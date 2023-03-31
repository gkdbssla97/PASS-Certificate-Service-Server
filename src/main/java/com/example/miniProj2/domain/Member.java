package com.example.miniProj2.domain;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Member {
    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "companyCd", length = 5)
    private String companyCd;
    @Column(name = "telcoTycd", length = 1)
    private String telcoTycd;
    @Column(name = "phoneNo", length = 40)
    private String phoneNo;
    @Column(name = "userNm", length = 300)
    private String UserNm;
    @Column(length = 40)
    private String birthday; //생년월일 "YYMMDD" format
    @Column(length = 40)
    private String gender; //성별로 0~9값 가짐 AES128 or AES256
}
