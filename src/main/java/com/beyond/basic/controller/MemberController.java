package com.beyond.basic.controller;

import com.beyond.basic.domain.Member;
import com.beyond.basic.domain.MemberReqDto;
import com.beyond.basic.domain.MemberResDto;
import com.beyond.basic.service.MemberService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller // Controller도 싱글톤이다.
// @RequiredArgsConstructor - 3번
public class MemberController {

    // 의존성주입(DI)방법1. 생성자주입방식(가장 많이 사용하는 방식)
    // 장점 : 1)final을 통해 상수로 사용 가능 2)다형성 구현 가능 3)순환참조방지
    private final MemberService memberService;

    @Autowired
    public MemberController(MemberService memberService){ // 객체 주입
        this.memberService = memberService;
    }

    // 의존성주입방법2. 필드주입방식(Authorwired만 사용) - 다형성 어려움
    // @Autowired
    // private MemberService memberService;

    // 의존성주입방법3. 어노테이션(RequiredArgs)을 이용하는 방식
    // @RequiredArgsConstructor : @NonNull 어노테이션, final 키워드가 붙어있는 필드를 대상으로 생성자 생성
    // private final MemberService memberService; // 다형성은 한계가 있음

    // home 화면
    @GetMapping("/")
    public String home(){
        return"member/home";
    }

    // 회원 목록 조회
    @GetMapping("/member/list")
    public String memberList(Model model){
        List<MemberResDto> memberList = memberService.memberList();
        model.addAttribute("memberList", memberList);
        return "member/member-list";
    }

    // 회원 상세 조회 : memberDetail
    // url(uri) : member/1, member/2
    // 화면명 : member-detail
    @GetMapping("/member/{id}")
    // int 또는 long 받을 경우 스프링에서 형변환(String->Long)
    public String memberDetail(@PathVariable Long id){
        return "member/member-detail";
    }

    // 회원가입화면 주고
    // url : member/create
    // 화면명 : member-create
    @GetMapping("/member/create")
    public String memberCreate(){
        return "member/member-create";
    }

    // 회원가입데이터를 받는다
    // url : member/create
    // name, email, password
    @PostMapping("/member/create")
    public String memberCreatePost(MemberReqDto dto, Model model){// @ModelAttribute 생략 가능하다

        try {
            memberService.memberCreate(dto); // member 객체를 memberService memberCreate 메서드 한테 넘겨줌
            // 화면 리턴이 아닌 url 재호출
            return "redirect:/member/list"; // member/member-list 이렇게 화면을 리턴하면 데이터가 비어있는 화면을 호출해줘서 안됨
        }catch (IllegalArgumentException e){
            model.addAttribute("errorMessage", e.getMessage());
            return "member/member-error";
        }

    }

}
