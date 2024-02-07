package com.example.demo.user.enity;

import com.example.demo.common.entity.BaseEntity;
import com.example.demo.role.entity.Role;
import com.vladmihalcea.hibernate.type.json.JsonType;
import lombok.*;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.annotations.*;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;
import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(
    name="\"user\"",
    indexes = {
        @Index(columnList = "name")
    }
)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@DynamicInsert
@DynamicUpdate
@Builder(access = AccessLevel.PROTECTED)
@BatchSize(size = 30)
@Getter
@ToString(of = {"id", "name"})
@TypeDefs(@TypeDef(name = "json", typeClass = JsonType.class))
public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, columnDefinition = "varchar(256) null comment '이름'")
    private String name;

    @Column(name = "pass", nullable = false, columnDefinition = "varchar(256) null comment '비밀번호'")
    private String pass;

    @Builder.Default
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Set<Role> roles = new LinkedHashSet<>();

    @Override
    public int hashCode() {
        HashCodeBuilder hcb = new HashCodeBuilder();
        hcb.append(name);
        return hcb.toHashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof User)) {
            return false;
        }
        User that = (User) obj;
        EqualsBuilder eb = new EqualsBuilder();
        eb.append(name, that.name);
        return eb.isEquals();
    }

    public void removeAllRole() {
        this.roles.clear();
    }

    public void addRole(Role role) {
        roles.add(role);
        role.setUser(this);
    }

    public void addAllRole(List<String> roles) {
        for (String role : roles) {
            addRole(Role.of(role, this));
        }
    }

    public static User of(String name, String pass) {
        return User.builder()
                .name(name)
                .pass(pass)
                .build();
    }
}
