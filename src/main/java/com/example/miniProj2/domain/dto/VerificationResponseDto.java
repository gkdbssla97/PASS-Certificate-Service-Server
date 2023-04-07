package com.example.miniProj2.domain.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class VerificationResponseDto {
    private String reqTxId;
    private String telcoTxId;
    private String certTxId;
    private String resultTycd; // 1 인증완료
    private String resultDttm;
    private String digitalSign;
    @JsonProperty("CI")
    private String CI;
    private String userNm;
    private String birthday;
    private String gender;
    private String phoneNo;
    private String telcoTycd;
}
