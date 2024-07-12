package com.beyond.basic.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Generated;
import lombok.NoArgsConstructor;

import javax.persistence.*;

// entity 어노테이션을 통해 엔티티매니저에게 객체 관리를 위임
// 해당 클래스명으로 테이블 및 컬럼을 자동 생성하고 각종 설정 정보 위임
@Entity
@Data
public class Member {
    @Id // pk 설정
    // identity : auto_increment 설정
    // auto : jpa 자동으로 적절한 전략을 선택하도록 맡기는것.
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // long은 bigint로 변환
    //    String은 varchar(255)로 기본으로 변환. name 변수명이 name 컬럼명으로 변환
    private String name;
    // nullable=false : not null 제약조건
    @Column(nullable = false, length = 50, unique = true)
    private String email;
    // @Column(name = "pw") 이렇게 할 수 있으나, 컬럼명과 변수명을 일치시키는 것이 혼선을 줄일 수 있음
    private String password;

}
