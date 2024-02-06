package com.example.demo.common.entity;

import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.ZonedDateTime;


/**
 * @brief       모든 엔티티에 공통으로 사용될 등록일시, 수정일시를 기록하기 위한 엔티티
 */

// JPA entity 에 대해 CRUD 이벤트의 전과 후를
// 콜백 받을 수 있는 애노테이션입니다.
@EntityListeners(AuditingEntityListener.class)
// 공통 되는 컬럼을 만들기 위해 사용하는 애노테이션입니다.
// 매핑 정보만 제공할 뿐 따로 구현하는 내용이 없기 때문에 보통 abstract class로 제공을 합니다.
@MappedSuperclass
@Getter
public abstract class BaseEntity {
    // entity 가 생성될 때, 자동으로 시간이 저장
    @CreationTimestamp
    @Column(updatable = false)
    protected ZonedDateTime createAt;

    // entity 가 수정될 때, 자동으로 시간이 저장
    @UpdateTimestamp
    protected ZonedDateTime updateAt;
}
