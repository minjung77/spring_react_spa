package com.example.zzyzzy.semiprojectv2.domain;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Member {
    private int mno;
    private String userid;
    private String passwd;
    private String name;
    private String email;
    private LocalDateTime regdate;
}
