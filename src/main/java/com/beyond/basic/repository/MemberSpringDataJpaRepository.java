package com.beyond.basic.repository;

import com.beyond.basic.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
// MemberSpringDataJpaRepository(MemberRepository)가 되기 위해서는 JpaRepository를 상속해야 하고, 상속시에 Entity명과 entity의 PK 타입을 제네릭에다가 명시
// MemberSpringDataJpaRepository(MemberRepository)는  JpaRepository를 상속함으로써 JpaRepository의 주요 기능을 상속
// JpaRepository 가 인터페이스임에도 해당 기능을 상속해서 사용할 수 있는 이유는 hibernate 구현체가 미리 구현되어 있기 때문이다.
// 런타임시점에 사용자가 인터페이스에 정의한 메서드를 프록시(대리인)객체가 리플렉션 기술을 통해 메서드를 구현
public interface MemberSpringDataJpaRepository extends MemberRepository, JpaRepository<Member, Long> {

    Optional<Member> findByEmail(String email); // 이렇게만 선언하면 런타임 시점에 알아서 기능 다 만들어줌

}
