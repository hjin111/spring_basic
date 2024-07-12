package com.beyond.basic.service;

import com.beyond.basic.domain.Member;
import com.beyond.basic.domain.MemberDetResDto;
import com.beyond.basic.domain.MemberReqDto;
import com.beyond.basic.domain.MemberResDto;
import com.beyond.basic.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

// input 값의 검증 및 실질적인 비지니스 로직은 서비스 계층에서 수행
@Service // 서비스 계층임을 표현함과 동시에 싱글톤 객체로 생성한다.
@Transactional // Transactional 어노테이션을 통해 모든 메서드에 트랜잭션을 적용하고,( 각 메서드마다 하나의 트랜잭션으로 묶는다는 뜻 ) 만약 예외가 발생시 롤백처리 자동화
public class MemberService {

    // 다형성 설계
    // MemberRepository 인터페이스
//    private final MemberRepository memberRepository; // 중복되어 사용되니깐 객체 변수로 선언 , 다른 클래스에서 접근 못하도록 private 붙여줌, 재할당이 안되도록 final 붙여줌
//
//    @Autowired // 싱글톤 객체를 주입(DI) 받는다라는 것을 의미 한다.
//    public  MemberService(MemberSpringDataJpaRepository memoryRepository){
//        // memberRepository = new MemberMemoryRepository(); // 생성자가 호출될 때 만들어짐
//        this.memberRepository = memoryRepository;
//    }

    // 비다형성 설계
    private final MyMemberRepository memberRepository; // 중복되어 사용되니깐 객체 변수로 선언 , 다른 클래스에서 접근 못하도록 private 붙여줌, 재할당이 안되도록 final 붙여줌

    @Autowired // 싱글톤 객체를 주입(DI) 받는다라는 것을 의미 한다.
    public  MemberService(MyMemberRepository memoryRepository){
        // memberRepository = new MemberMemoryRepository(); // 생성자가 호출될 때 만들어짐
        this.memberRepository = memoryRepository;
    }

//     @Autowired
//     private MemberController memberController;

    public void memberCreate(MemberReqDto dto) {

        if(dto.getPassword().length() < 8){
            throw new IllegalArgumentException("비밀번호가 너무 짧습니다.");
        }

        // member 객체 만들기
        Member member = new Member();
        member.setName(dto.getName());
        member.setEmail(dto.getEmail());
        member.setPassword(dto.getPassword());

        // repository로 넘겨줄때는 member 객체로 넘겨줌( DB에 Member 객체 저장해 )
        memberRepository.save(member);

    }

    public MemberDetResDto memberDetail(Long id){

        MemberDetResDto memberDetail = new MemberDetResDto();
        Optional<Member> optMember = memberRepository.findById(id);
        // 클라이언트에게 적절한 예외 메세지와 상태코드를 주는 것이 주요 목적
        // 또한, 예외를 강제 발생시킴으로써 적절한 롤백처리 하는것도 주요목적
        Member member = optMember.orElseThrow(()->new EntityNotFoundException("없는 회원입니다."));
        memberDetail.setId(member.getId());
        memberDetail.setName(member.getName());
        memberDetail.setEmail(member.getEmail());
        memberDetail.setPassword(member.getPassword());

        return memberDetail;
    }

    public List<MemberResDto> memberList(){

        List <MemberResDto> memberResDtos = new ArrayList<>();
        List<Member> memberList = memberRepository.findAll();
        for( Member member : memberList){
            MemberResDto dto = new MemberResDto();
            dto.setId(member.getId());
            dto.setName(member.getName());
            dto.setEmail(member.getEmail());
            memberResDtos.add(dto);
        }

        return memberResDtos;

    }
}
