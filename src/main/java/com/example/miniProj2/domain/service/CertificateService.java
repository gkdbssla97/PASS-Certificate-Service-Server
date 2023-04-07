package com.example.miniProj2.domain.service;

import com.example.miniProj2.domain.dto.CertificationResponseDto;
import com.example.miniProj2.domain.dto.MemberDto;
import com.example.miniProj2.domain.vo.AccessTokenVO;
import com.example.miniProj2.domain.vo.MemberVO;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface CertificateService {

    AccessTokenVO getAccessToken();
    List<MemberDto> findAuthorizedMemberList();
    CertificationResponseDto setCertificationResponse(Map<String, String> requestData);
    void saveTxId(MemberVO memberVO);

    /**
     * 인증 알림내용 등록 에러
     */
    void validateRequiredKeys(Map<String, String> requestData); // 3101
    void verifyAccessToken(String authorization);
    void validateRequestBody(Map<String, String> requestData);
    void validateRequestItems(Map<String, String> requestData) throws Exception;
    ResponseEntity<CertificationResponseDto> getCertificationResponseEntity(
            Map<String, String> requestData, List<MemberDto> authorizedMemberList) throws Exception;
}
