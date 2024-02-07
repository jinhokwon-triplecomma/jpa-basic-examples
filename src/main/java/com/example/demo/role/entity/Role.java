package com.example.demo.role.entity;

import com.example.demo.common.entity.BaseEntity;
import com.example.demo.user.enity.User;
import lombok.*;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Entity
@Table(name = "\"role\"", indexes = {
    @Index(columnList = "name")
})
@Getter
@DynamicUpdate
@DynamicInsert
@BatchSize(size = 30)
@Builder(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(of = {"id", "name"})
public class Role extends BaseEntity {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, columnDefinition = "varchar(256) null comment '이름'")
    String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", insertable = false, updatable = false)   // user 테이블의 id 컬럼
    User user;

    @Column(name = "user_id")
    private Long userId;

    public void setUser(User user) {
        this.user = user;
        this.userId = user.getId();
    }

    public static Role of(String name) {
        return Role.builder()
                .name(name)
                .build();
    }

    public static Role of(String name, User user) {
        return Role.builder()
                .userId(user.getId())
                .name(name)
                .user(user)
                .build();
    }
}
