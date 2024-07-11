package com.beyond.basic.repository;

import com.beyond.basic.domain.Member;

import java.util.List;

public interface MemberRepository {

    Member save(Member member); // Member 로 리턴을 하게 되면 Member 객체를 잘 저장했다고 알려줘야 해서 그 저장된 Member 객체를 리턴해줘야 함

    List<Member> findAll();

    Member findById(Long id);

}
