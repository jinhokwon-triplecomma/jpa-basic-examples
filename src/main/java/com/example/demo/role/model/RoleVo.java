package com.example.demo.role.model;

import com.example.demo.role.entity.Role;
import lombok.*;

@Getter
@Builder(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class RoleVo {
    private String name;

    public static RoleVo fromEntity(Role role) {
        return RoleVo.builder()
                .name(role.getName())
                .build();
    }
}
