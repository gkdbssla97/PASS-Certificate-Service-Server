package com.example.miniProj2.domain.service;

import com.example.miniProj2.domain.dto.MemberDto;
import com.example.miniProj2.domain.dto.VerificationRequestDto;
import com.example.miniProj2.domain.dto.VerificationResponseDto;
import org.apache.ibatis.annotations.Param;

public interface VerificationService {
    MemberDto findMemberByCertTxId(String certTxId);
    VerificationResponseDto setVerificationResponse(MemberDto memberDto, String signTarget) throws Exception;
    void saveVerificationResult(@Param("VerificationResponseDto") VerificationResponseDto verificationResponseDto);

    /**
     * 인증 처리상태 조회 에러
     */
    void validateRequiredKeys(VerificationRequestDto verificationRequestDto); // 4101
    void validateRequestBody(VerificationRequestDto verificationRequestDto);
    void validateRequestItems(VerificationRequestDto verificationRequestDto) throws Exception;
    void confirmExpirationPeriod(MemberDto memberDto);
}
