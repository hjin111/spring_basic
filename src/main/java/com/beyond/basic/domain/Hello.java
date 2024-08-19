package com.beyond.basic.domain;

import lombok.*;

// 롬복 라이브러리를 통해 getter, setter 어노테이션을 사용
// @Getter
// @Setter
@Data // getter, setter, toString 등을 포함
// @NoArgsConstructor : 기본생성자 만드는 어노테이션
// @AllArgsConstructor : 모든 매개변수를 사용한 생성자를 만드는 어노테이션
@AllArgsConstructor // AllArgsConstructor 넣고 NoArgsConstructor 안 넣으면 기본 생성자가 없어짐
@NoArgsConstructor
public class Hello {

    private String name;
    private String email;
    private String password;

//    @Override
//    public String toString(){
//        return "이름은 : " + this.name + " email은 : " + this.email;
//    }

    // Builder 패턴 직접 구현
    // 빌더 적용 대상 생성자가 필요
    public Hello(HelloBuilder helloBuilder){
        this.name = helloBuilder.name;
        this.email = helloBuilder.email;
        this.password = helloBuilder.password;
    }

    public static HelloBuilder builder(){
        return new HelloBuilder();
    }

    // static 내부 클래스
    public static class HelloBuilder {
        private String name;
        private String email;
        private String password;

        public HelloBuilder name(String name){
            this.name = name;
            return this; // 여기서 this는 HelloBuilder
        }

        public HelloBuilder email(String email){
            this.email = email;
            return this; // 여기서 this는 HelloBuilder 의미
        }

        public HelloBuilder password(String password){
            this.password = password;
            return this; // 여기서 this는 HelloBuilder 의미
        }

        public Hello build(){
            return new Hello(this);
        }
    }

}
