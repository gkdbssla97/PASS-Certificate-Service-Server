package com.example.miniProj2.domain.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class MemberDto {
    private String companyCd;
    private String telcoTycd;
    private String telcoTxId;
    private String phoneNo;
    private String userNm;
    private String birthday;
    private String gender;
    private String reqTxId;
    private String certTxId;
    private String reqEndDttm;
    private String signTarget;
}
