package com.example.demo.common.client;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.math.BigInteger;
import java.util.function.Supplier;

@Slf4j
@Component
@RequiredArgsConstructor
public class DbLockTemplate {
    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public void execute(String key, int timeoutSeconds, Runnable runnable) {
        execute(key, timeoutSeconds, () -> {
            runnable.run();
        });
    }

    @Transactional
    public <T> T execute(String key, int timeoutSeconds, Supplier<T> supplier) {
        BigInteger result = getLock(key, timeoutSeconds);

        try {
            return supplier.get();
        } finally {
            if (result.intValue() == 1) {
                releaseLock(key);
            } else {
                log.error("result : {}", result);
            }
        }
    }

    private BigInteger getLock(String key, int timeoutSeconds) {
        // 1    Lock 획득 성공
        // 0    timeout 동안 Lock 획득 못한 경우
        // null 에러 발생
        return (BigInteger) entityManager
                .createNativeQuery("SELECT GET_LOCK(:key, :timeout)")
                .setParameter("key", key)
                .setParameter("timeout", timeoutSeconds)
                .getSingleResult();
    }

    private BigInteger releaseLock(String key) {
        return (BigInteger) entityManager
                .createNativeQuery("SELECT RELEASE_LOCK(:key)")
                .setParameter("key", key)
                .getSingleResult();
    }
}
