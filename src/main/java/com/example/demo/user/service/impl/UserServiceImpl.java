package com.example.demo.user.service.impl;

import com.example.demo.user.enity.User;
import com.example.demo.user.model.UserVo;
import com.example.demo.user.repository.UserRepository;
import com.example.demo.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public UserVo create(String name, String pass, List<String> roles) {
        if (StringUtils.equalsIgnoreCase(name, "anonymous")) {
            throw new IllegalArgumentException("can not create anonymous user");
        }

        User user = userRepository.save(User.of(name, pass));
        user.addAllRole(roles);
        log.info("create user : {}", user);
        return UserVo.fromEntity(user);
    }

    @Override
    @Transactional(readOnly = true)
    public UserVo findById(Long id) {
        User user = userRepository.findById(id).orElseThrow(IllegalArgumentException::new);
        return UserVo.fromEntity(user);
    }

    @Override
    @Transactional(readOnly = true)
    public UserVo getUser(Long id) {
        User user = userRepository.findById(id).orElseThrow(IllegalArgumentException::new);
        return UserVo.fromEntity(user);
    }
}
