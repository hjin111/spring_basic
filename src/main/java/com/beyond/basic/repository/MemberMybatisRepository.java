package com.beyond.basic.repository;

import com.beyond.basic.domain.Member;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
// 해당 레파지토리를 mybatis 기술을 쓰겠다 라는 표현
@Mapper
public interface MemberMybatisRepository extends MemberRepository{ // 인터페이스 하고 인터페이스가 상속 관계가 선언 되면 메서드들을 그대로 가져온다

}
