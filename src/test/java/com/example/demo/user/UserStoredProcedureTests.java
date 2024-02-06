package com.example.demo.user;

import com.example.demo.user.enity.User;
import com.example.demo.user.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.PersistenceContext;
import javax.persistence.StoredProcedureQuery;
import java.util.List;

@Slf4j
//@EnabledIf(expression = "#{environment['ENABLE_TEST'] == 'TRUE'}", loadContext = false)
@ExtendWith(SpringExtension.class)
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@AutoConfigureMockMvc
@RequiredArgsConstructor
class UserStoredProcedureTests {
    private final UserRepository userRepository;
    private final MockMvc mockMvc;
    private final ObjectMapper objectMapper;

    @Autowired
    JPAQueryFactory jpaQueryFactory;

    @PersistenceContext
    private EntityManager em;

    @BeforeEach
    void onBeforeEach() {
    }

     @Test
     @Order(1)
     @DisplayName("getUserTest")
     public void getUserTest() throws Exception {
         List<User> userList = userRepository.findUserById(16L);
         log.info("{}", userList);
     }

    @Test
    @Order(2)
    @DisplayName("getUserCountTest")
    public void getUserCountTest() throws Exception {
        Long userCount = userRepository.getTotalUserCountById(16L);
        log.info("{}", userCount);
    }

    @Test
    @Order(3)
    @DisplayName("getUserCountTest2")
    public void getUserCountTest2() throws Exception {
        StoredProcedureQuery query = em.createStoredProcedureQuery("GET_TOTAL_USERS_BY_ID");

        // 파라미터 이름 지정
        query.registerStoredProcedureParameter("inId", Long.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("outCount", Long.class, ParameterMode.OUT);

        // 필요한 파라미터 적용
        query.setParameter("inId", 16L);

        // 쿼리 실행.
        query.execute();

        // 결과 확인
        Long count = (Long)query.getOutputParameterValue("outCount");
        log.info("{}", count);
    }
}
