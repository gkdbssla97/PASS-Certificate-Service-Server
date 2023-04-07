package com.example.miniProj2.domain;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Verification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "reqTxId", length = 20)
    private String reqTxId;
    @Column(name = "telcoTxId", length = 20)
    private String telcoTxId;
    @Column(name = "certTxId", length = 20)
    private String certTxId;
    @Column(name = "resultTycd", length = 1)
    private String resultTycd; // 1 인증완료
    @Column(name = "resultDttm", length = 20)
    private String resultDttm;
    @Lob
    private String digitalSign;
    @Column(name = "CI", length = 200)
    private String CI;
    @Column(name = "userNm", length = 300)
    private String userNm;
    @Column(name = "birthday", length = 40)
    private String birthday;
    @Column(name = "gender", length = 40)
    private String gender;
    @Column(name = "phoneNo", length = 40)
    private String phoneNo;
    @Column(name = "telcoTycd", length = 1)
    private String telcoTycd;
}
