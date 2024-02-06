package com.example.demo.user;

import com.example.demo.user.dto.request.CreateUserRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
//@EnabledIf(expression = "#{environment['ENABLE_TEST'] == 'TRUE'}", loadContext = false)
@ExtendWith(SpringExtension.class) //  Spring Test Context 프레임워크와 Junit5와 통합해 사용할 때는 SpringExtension.class를 사용
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@AutoConfigureMockMvc
@RequiredArgsConstructor
@SpringBootTest
@Testcontainers
@ContextConfiguration(initializers = {UserMvcTestContainerTests.Initializer.class})
class UserMvcTestContainerTests {
    private final MockMvc mockMvc;
    private final ObjectMapper objectMapper;

    private static Long testId;
    private static final String testName = "james";

    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Autowired
    JPAQueryFactory jpaQueryFactory;

    @Container
    private static final MySQLContainer mySQLContainer = new MySQLContainer<>("mysql:8.0.30")
            .withDatabaseName("testcontainer")
            .withUsername("user")
            .withPassword("pass")
            .withInitScript("db/init.sql")
            ;

    static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
        public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
            mySQLContainer.start();
            TestPropertyValues.of(
                    "spring.datasource.url=" + mySQLContainer.getJdbcUrl(),
                    "spring.datasource.username=" + mySQLContainer.getUsername(),
                    "spring.datasource.password=" + mySQLContainer.getPassword()
            ).applyTo(configurableApplicationContext.getEnvironment());
        }
    }

    @Test
    @Order(1)
    @DisplayName("createUserTest")
    public void createUserTest() throws Exception {
        CreateUserRequest request = CreateUserRequest.of(
                testName,
                "007",
                List.of("READ", "WRITE")
        );

        String json = objectMapper.writeValueAsString(request);
        MvcResult mvcResult = mockMvc.perform(post("/user")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(json)
                        .characterEncoding("UTF-8")
                        .header("X-TEST", "this is test header")
                )
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(testName))
                .andDo(print())
                .andReturn();

        log.info("{}", mvcResult.getResponse().getContentAsString());
        testId = JsonPath.parse(mvcResult.getResponse().getContentAsString()).read("$.id", Long.class);
    }

    @Test
    @Order(2)
    @DisplayName("getUserTest")
    public void getUserTest() throws Exception {
        String name = "james";
        MvcResult mvcResult = mockMvc.perform(get("/user/"+ testId)
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .characterEncoding("UTF-8")
                        .header("X-TEST", "this is test header")
                )
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(name))
                .andDo(print())
                .andReturn();

        log.info("{}", mvcResult.getResponse().getContentAsString());
        String responseName = JsonPath.parse(mvcResult.getResponse().getContentAsString()).read("$.name");
        Assertions.assertEquals(name, responseName);
    }

    @Test
    @Order(3)
    @DisplayName("getUnknownUserTest")
    public void getUnknownUserTest() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/user/160")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .characterEncoding("UTF-8")
                        .header("X-TEST", "this is test header")
                )
                .andExpect(status().isBadRequest())
                .andDo(print())
                .andReturn();
    }
}
