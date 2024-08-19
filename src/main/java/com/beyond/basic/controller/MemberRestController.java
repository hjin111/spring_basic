package com.beyond.basic.controller;

import com.beyond.basic.domain.*;
import com.beyond.basic.repository.MyMemberRepository;
import com.beyond.basic.service.MemberService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.sql.SQLException;
import java.util.List;

@RestController
// RestController의 경우 모든 메서드 상단에 @ResponseBody가 붙는 효과 발생
@RequestMapping("/rest")
@Api(tags = "회원관리서비스")
public class MemberRestController {

    private final MemberService memberService;
    private final MyMemberRepository myMemberRepository;

    @Autowired
    public MemberRestController(MemberService memberService, MyMemberRepository myMemberRepository){ // 객체 주입
        this.memberService = memberService;
        this.myMemberRepository = myMemberRepository;
    }

    @GetMapping("/member/text")
    public String memberText(){
        return "ok";
    }

    // 회원 목록 조회
//    @GetMapping("/member/list")
//    public List<MemberResDto> memberList(){ // List<객체> => 배열 형식의 json으로 만들어줌
//        List<MemberResDto> memberList = memberService.memberList();
//        return memberList;
//    }

    @GetMapping("/member/list")
    public ResponseEntity<Object> memberList(){
        List<MemberResDto> memberList = memberService.memberList();
        CommonResDto commonResDto = new CommonResDto(HttpStatus.OK, "members find successful", memberList);
        return new ResponseEntity<>(commonResDto, HttpStatus.OK);
    }

    // 회원 상세 조회
    @GetMapping("/member/detail/{id}")
    public ResponseEntity<Object> memberDetail(@PathVariable Long id){

//        try {

            MemberDetResDto memberResDto = memberService.memberDetail(id);
            CommonResDto commonResDto = new CommonResDto(HttpStatus.OK, "member is found", memberResDto);
            return new ResponseEntity<>(commonResDto, HttpStatus.OK);

//        }catch (EntityNotFoundException e){
//
//            CommonErrorDto commonErrorDto = new CommonErrorDto(HttpStatus.NOT_FOUND.value(), e.getMessage());
//            return new ResponseEntity<>( commonErrorDto, HttpStatus.NOT_FOUND);
//        }

    }

    @PostMapping("/member/create")
    public ResponseEntity<Object> memberCreatePost(@RequestBody MemberReqDto dto){// @ModelAttribute 생략 가능하다

//        try {

            // memberService.memberCreate(dto); // member 객체를 memberService memberCreate 메서드 한테 넘겨줌
            // 화면 리턴이 아닌 url 재호출
            // return "ok"; // member/member-list 이렇게 화면을 리턴하면 데이터가 비어있는 화면을 호출해줘서 안됨
            memberService.memberCreate(dto);
            // 여기 안에 들어 있는 HttpStatus.CREATED는 body에 들어가는 부분
            CommonResDto commonResDto = new CommonResDto(HttpStatus.CREATED, "member is successfully created", null); // null 한 이유는 객체를 넣어줄게 없어서
            return new ResponseEntity<>(commonResDto, HttpStatus.CREATED); // header에 들어가 있는 상태
//        }catch (IllegalArgumentException e){
//            e.printStackTrace();
//            CommonErrorDto commonErrorDto = new CommonErrorDto(HttpStatus.BAD_REQUEST.value(), e.getMessage());
//            return new ResponseEntity<>(commonErrorDto, HttpStatus.BAD_REQUEST);
//            // return "error!!";
//        }
    }

    // 수정은 2가지 요청방식 : PUT, PATCH 요청
    // patch 요청은 부분수정, put 요청은 덮어쓰기
    @PatchMapping("/member/pw/update")
    public String memberList(@RequestBody MemberUpdateDto dto){
        memberService.pwUpdate(dto);
        return "ok";
    }

    @DeleteMapping("/member/delete/{id}")
    public String memberDelete(@PathVariable Long id){
        memberService.delete(id);
        return "ok";
    }

    // lazy(지연로딩), eager(즉시로딩) 테스트
    @GetMapping("member/post/all")
    @ResponseBody
    public void memberPostAll(){
        List<Member> memberList = myMemberRepository.findAll();
        for( Member m : memberList ){
            System.out.println(m.getPosts().size());
        }

    }
}
