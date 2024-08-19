package com.beyond.basic.domain;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
public class Post extends BaseEntity{

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    private Long id;
    private String title;

    // 1:1의 경우 OneToOne을 설정하고, unique = true로 설정.
    // ManyToOne, OneToOne의 경우 default 설정이 eager이므로, lazy로 변경 ( eager이 즉시 로딩이고, lazy가 지연 로딩 )
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    // JPA의 연속성(Persistence) 컨텍스트에 의해 Member 객체가 관리된다.
    private Member member;


    public PostResDto fromEntity(){
        return new PostResDto(this.id, this.title);
    }

}
