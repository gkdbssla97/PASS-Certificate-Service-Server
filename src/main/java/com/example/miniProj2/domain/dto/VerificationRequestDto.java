package com.example.miniProj2.domain.dto;

import lombok.*;

import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class VerificationRequestDto {
    @Size(max = 6)
    private String companyCd;
    @Size(max = 21)
    private String reqTxId;
    @Size(max = 21)
    private String certTxId;
    @Size(max = 40)
    private String phoneNo;
    @Size(max= 300)
    private String userNm;
    private String signTarget;
}
