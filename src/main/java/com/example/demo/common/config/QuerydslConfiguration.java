package com.example.demo.common.config;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.querydsl.sql.MySQLTemplates;
import com.querydsl.sql.SQLQueryFactory;
import com.querydsl.sql.SQLTemplates;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;

@Configuration
public class QuerydslConfiguration {

    @PersistenceContext
    private EntityManager entityManager;

    @Bean
    public JPAQueryFactory jpaQueryFactory() {
        // JPAQueryFactory는 insert()를 가지고 있지 않다.
        return new JPAQueryFactory(entityManager);
    }

    @Bean
    public SQLQueryFactory sqlQueryFactory(DataSource dataSource) {
        SQLTemplates templates = new MySQLTemplates();
        com.querydsl.sql.Configuration configuration = new com.querydsl.sql.Configuration(templates);
        SQLQueryFactory queryFactory = new SQLQueryFactory(configuration, dataSource);
        return queryFactory;
    }
}