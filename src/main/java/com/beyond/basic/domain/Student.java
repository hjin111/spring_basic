package com.beyond.basic.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Student {
    private String name;
    private String email;
    private List<Student.Grade> grades;
    // private List<Map<String, String>> grades;
    @Data
    static class Grade{
        private String className;
        private String point;
    }
}

