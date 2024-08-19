package com.beyond.basic.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MemberReqDto {
    private String name;
    private String email;
    private String password;

    // dto 에서 entity 로 변환
    // 추후에는 빌더패턴으로 변환
    public Member toEntity(){
        Member member = new Member(this.name, this.email, this.password);
        return member;
    }
}
