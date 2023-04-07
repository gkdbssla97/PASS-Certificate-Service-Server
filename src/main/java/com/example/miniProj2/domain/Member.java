package com.example.miniProj2.domain;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
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
    @Column(name = "reqTxId", length = 20)
    private String reqTxId; //반드시 20자리의 값(특수문자 사용불가)
    @Column(name = "telcoTxId",length = 20)
    private String telcoTxId;
    @Column(name = "certTxId", length = 20)
    private String certTxId;
    @Column(name = "reqEndDttm", length = 20)
    private String reqEndDttm; //인증요청의 유효 만료일시 "YYYY-MM-DD hh:mm:ss" format
}
