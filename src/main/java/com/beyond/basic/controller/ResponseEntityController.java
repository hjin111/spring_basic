package com.beyond.basic.controller;

import com.beyond.basic.domain.CommonResDto;
import com.beyond.basic.domain.Member;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/response/entity")
public class ResponseEntityController {

    // case1. @ResponseStatus 어노테이션 방식
    @GetMapping("/annotation1")
    @ResponseStatus(HttpStatus.OK) // header에 들어감
    public String annotation1(){
        return "ok";
    }
    // responseBody는 header와 body로 이루어짐
    // ok 메세지는 body로 들어감
    // 200이라는 상태값은 header에 들어감

    @GetMapping("/annotation2")
    @ResponseStatus(HttpStatus.CREATED) // 상태 코드를 header에다가 놓고 201 리턴 하려고 함
    public Member annotation2(){
        // (가정)객체 생성 후 DB 저장 성공
        Member member = new Member("hong", "hong@naver.com","1234");
        return member;
    }

    // case2. 메서드 체이닝 방식 : ResponseEntity의 클래스 메서드 사용
    @GetMapping("/chaining1")
    public ResponseEntity<Member> chaining1(){
        Member member = new Member("hong", "hong@naver.com","1234");
        return ResponseEntity.ok(member);
    }

    @GetMapping("/chaining2")
    public ResponseEntity<Member> chaining2(){
        Member member = new Member("hong", "hong@naver.com","1234");
        return ResponseEntity.status(HttpStatus.CREATED).body(member);
    }

    @GetMapping("/chaining3")
    public ResponseEntity<Member> chaining3(){
        return ResponseEntity.notFound().build();
    }

    // case3. ResponseEntity 객체를 직접 custom하여 생성하는 방식
    @GetMapping("/custom1")
    public ResponseEntity<Member> custom1(){
        Member member = new Member("hong", "hong@naver.com","1234");
        return new ResponseEntity<>(member, HttpStatus.CREATED);
        // 위에 return 코드가 아래와 같은 코드이다.
        // ResponseEntity<Member> memberResponseEntity = new ResponseEntity<>(member, HttpStatus.CREATED); 상태코드가 header에 들어가고 member 객체만 body에 들어감
        // return memberResponseEntity;
    }

    // 우리가 실질적으로 쓸 것
    @GetMapping("/custom2")
    public ResponseEntity<CommonResDto> custom2(){
        Member member = new Member("hong", "hong@naver.com","1234");
        // 여기 HttpStatus.CREATED는 body에 들어감, commonResDto 객체 안에 result(member)라는 객체가 들어감
        CommonResDto commonResDto = new CommonResDto(HttpStatus.CREATED, "member is successfully created", member);
        return new ResponseEntity<>(commonResDto, HttpStatus.CREATED); // header에도 들어감
    }

}
