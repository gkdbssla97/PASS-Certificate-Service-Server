package com.example.miniProj2.domain.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class CertificationResponseDto {
    private String reqTxId;
    private String certTxId;
    private String telcoTxId;
}
