package com.example.demo.user.service;

import com.example.demo.user.model.UserVo;

import java.util.List;

public interface UserService {

    UserVo create(String name, String pass, List<String> roles);
    UserVo findById(Long id);
    UserVo getUser(Long id);
}
