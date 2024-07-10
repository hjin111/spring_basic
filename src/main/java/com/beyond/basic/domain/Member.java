package com.beyond.basic.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Member {
    private String name;
    private String email;
    private String password;
}
