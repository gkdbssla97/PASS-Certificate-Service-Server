package com.example.miniProj2.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class MemberVO {
    private String userNm;
    private String telcoTxId;
    private String reqTxId;
    private String certTxId;
    private String reqEndDttm;
}
