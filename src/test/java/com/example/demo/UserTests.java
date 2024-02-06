package com.example.demo;

import com.example.demo.user.enity.User;
import com.example.demo.user.model.UserVo;
import com.example.demo.user.repository.UserRepository;
import com.example.demo.user.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;

@Slf4j
//@EnabledIf(expression = "#{environment['ENABLE_TEST'] == 'TRUE'}", loadContext = false)
@ExtendWith(SpringExtension.class)
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@AutoConfigureMockMvc
@RequiredArgsConstructor
class UserTests {

    // Mock 객체를 생성하고, 스프링 컨텍스트에 등록을 한다.
    @MockBean
    private final UserRepository userRepository;

    private final UserService userService;
    private final MockMvc mockMvc;
    private final ObjectMapper objectMapper;

    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Autowired
    JPAQueryFactory jpaQueryFactory;

    @BeforeEach
    void onBeforeEach() {
    }

    @Test
    @Order(1)
    public void userTest() {
        User mockedUser = User.of("james", "007");
        Mockito.when(userRepository.save(any())).thenReturn(mockedUser);
        Mockito.when(userRepository.findById(any())).thenReturn(Optional.of(mockedUser));
        UserVo userVo = userService.findById(1L);
        Assertions.assertEquals("james", userVo.getName());
    }
}
