package com.beyond.basic.domain;

import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@Getter
// 기본적으로 Entity는 상속관계가 불가능하여, 해당 어노테이션(@MappedSuperclass)을 붙여야 상속관계 성립 가능
@MappedSuperclass
public abstract class BaseEntity { // 객체를 만들 수 없는 추상클래스

    // 캐멀케이스 사용시 DB에는 _(언더바)로 생성
    @CreationTimestamp // DB에는 current_timestamp 가 생성되지 않음 / 최초의 시간
    private LocalDateTime createdTime;
    @UpdateTimestamp // 수정할 때 마다 값이 갱신
    private LocalDateTime updateTime;

}
