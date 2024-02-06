package com.example.demo.user.dto.request;

import lombok.*;
import java.util.*;

@Getter
@Builder(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CreateUserRequest {
    private String name;
    private String pass;
    private List<String> roles;

    public static CreateUserRequest of(String name,
                                       String pass,
                                       List<String> roles) {
        return CreateUserRequest.builder()
                .name(name)
                .pass(pass)
                .roles(roles)
                .build();
    }
}
