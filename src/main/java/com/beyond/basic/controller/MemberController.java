package com.beyond.basic.controller;

import com.beyond.basic.domain.Member;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class MemberController {

    // 회원 목록 조회
    @GetMapping("/member/list")
    public String memberList(){
        return "member/member-list";
    }

    // 회원 상세 조회 : memberDetail
    // url(uri) : member/1, member/2
    // 화면명 : member-detail
    @GetMapping("/member/{id}")
    // int 또는 long 받을 경우 스프링에서 형변환(String->Long)
    public String memberDetail(@PathVariable Long id){
        return "/member/member-detail";
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
    @ResponseBody
    public String memberCreatePost(Member member){ // @ModelAttribute 생략 가능하다
        System.out.println(member);
        return null;
    }

}
