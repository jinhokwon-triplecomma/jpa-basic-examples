package com.example.demo.user.dto.response;

import com.example.demo.role.model.RoleVo;
import com.example.demo.user.model.UserVo;
import lombok.*;

import java.util.List;

@Getter
@Builder(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class GetUserResponse {
    private Long id;
    private String name;
    private List<RoleVo> roles;

    public static GetUserResponse of(UserVo userVo) {
        return GetUserResponse.builder()
                .id(userVo.getId())
                .name(userVo.getName())
                .roles(userVo.getRoles())
                .build();
    }
}
