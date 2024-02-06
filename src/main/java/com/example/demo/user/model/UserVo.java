package com.example.demo.user.model;

import com.example.demo.role.model.RoleVo;
import com.example.demo.user.enity.User;
import lombok.*;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class UserVo {
    private Long id;
    private String name;
    private List<RoleVo> roles;

    public static UserVo fromEntity(User user) {
        return UserVo.builder()
                .id(user.getId())
                .name(user.getName())
                .roles(user.getRoles().stream().map(RoleVo::fromEntity).collect(Collectors.toList()))
                .build();
    }
}
