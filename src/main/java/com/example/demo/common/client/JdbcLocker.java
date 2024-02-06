package com.example.demo.common.client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.SQLException;

@Slf4j
public class JdbcLocker implements AutoCloseable {
    private final JdbcTemplate jdbcTemplate;
    private final String name;
    private String lockResult;
    private String unlockResult;

    public JdbcLocker(JdbcTemplate jdbcTemplate, String name, int timeoutSecs) throws SQLException {
        this.jdbcTemplate = jdbcTemplate;
        this.name = name;

        // 1 : Lock 획득 성공, 0 : timeout 동안 Lock 획득 못한 경우, null : 에러 발생
        lockResult = jdbcTemplate.queryForObject(String.format("SELECT GET_LOCK('%s', %d)", name, timeoutSecs), String.class);

        if (!"1".equalsIgnoreCase(lockResult)) {
            // Lock 획득에 실패한 경우
            String message = String.format("fail to lock : %s, timeoutSecs : %d, lockResult : %s", name, timeoutSecs, lockResult);
            throw new SQLException(message);
        }
    }

    @Override
    public void close() {
        if ("1".equalsIgnoreCase(lockResult)) {
            String unlockSql = String.format("SELECT RELEASE_LOCK('%s') -- #########", name);
            unlockResult = jdbcTemplate.queryForObject(unlockSql, String.class);

            if (!"1".equalsIgnoreCase(unlockResult)) {
                // unlock 에 실패한 경우
                log.warn("fail to unlock : {}, unlock result : {}", name, unlockResult);
            }
        }
    }
}
