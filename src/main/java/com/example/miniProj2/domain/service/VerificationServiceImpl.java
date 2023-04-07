package com.example.miniProj2.domain.service;

import com.example.miniProj2.domain.dao.GetMapper;
import com.example.miniProj2.domain.dao.PostMapper;
import com.example.miniProj2.domain.dto.MemberDto;
import com.example.miniProj2.domain.dto.VerificationRequestDto;
import com.example.miniProj2.domain.dto.VerificationResponseDto;
import com.example.miniProj2.util.AESCipher;
import com.example.miniProj2.util.Constants;
import com.example.miniProj2.util.error.code.AuthenticationRegistrationError;
import com.example.miniProj2.util.error.code.AuthenticationSearchError;
import com.example.miniProj2.util.error.code.CommonError;
import com.example.miniProj2.util.error.customexception.CommonCustomException;
import com.example.miniProj2.util.error.customexception.RegisterCustomException;
import com.example.miniProj2.util.error.customexception.SearchCustomException;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
@Builder
@Slf4j
public class VerificationServiceImpl implements VerificationService {

    private final GetMapper getMapper;
    private final PostMapper postMapper;
    private final AESCipher aesCipher = new AESCipher(Constants.ENCRYPT_KEY);

    @Override
    public MemberDto findMemberByCertTxId(String certTxId) {
        return getMapper.findMemberByCertTxId(certTxId);
    }

    @Override
    public VerificationResponseDto setVerificationResponse(MemberDto memberDto, String signTarget) throws Exception {
        return VerificationResponseDto.builder()
                .reqTxId(memberDto.getReqTxId())
                .telcoTxId(memberDto.getTelcoTxId())
                .certTxId(memberDto.getCertTxId())
                .resultTycd("1")
                .resultDttm(initializeResultDttm())
                .digitalSign(initializeEncryptKey(signTarget, memberDto, "DigitalSign"))
                .CI(initializeEncryptKey("", memberDto, "CI"))
                .userNm(aesCipher.encrypt(memberDto.getUserNm()))
                .birthday(aesCipher.encrypt(memberDto.getBirthday()))
                .gender(aesCipher.encrypt(memberDto.getGender()))
                .phoneNo(aesCipher.encrypt(memberDto.getPhoneNo()))
                .telcoTycd(memberDto.getTelcoTycd())
                .build();
    }

    @Override
    public void saveVerificationResult(VerificationResponseDto verificationResponseDto) {
        postMapper.saveVerificationResult(verificationResponseDto);
    }

    /**
     * 인증 처리상태 조회 에러(4101)
     *
     * @param verificationRequestDto
     * @return
     */
    @Override
    public void validateRequiredKeys(VerificationRequestDto verificationRequestDto) {

        // 필수항목 검증(4101)
        boolean isNull = false;
        List.of("companyCd", "reqTxId", "certTxId", "phoneNo", "userNm");
        String requiredKey = "";
        if (verificationRequestDto.getCompanyCd() == null) {
            isNull = true;
            requiredKey = Constants.requiredKeysBySearch.get(0);
        }
        if (verificationRequestDto.getReqTxId() == null) {
            isNull = true;
            requiredKey = Constants.requiredKeysBySearch.get(1);
        }
        if (verificationRequestDto.getCertTxId() == null) {
            isNull = true;
            requiredKey = Constants.requiredKeysBySearch.get(2);
        }
        if (verificationRequestDto.getPhoneNo() == null) {
            isNull = true;
            requiredKey = Constants.requiredKeysBySearch.get(3);
        }
        if (verificationRequestDto.getUserNm() == null) {
            isNull = true;
            requiredKey = Constants.requiredKeysBySearch.get(4);
        }
        if (isNull) {
            throw new SearchCustomException(AuthenticationSearchError.MISSING_REQUIRED_ITEM, requiredKey,
                    verificationRequestDto.getCertTxId(),
                    verificationRequestDto.getReqTxId(),
                    verificationRequestDto.getCertTxId());
        }
    }

    @Override
    public void validateRequestBody(VerificationRequestDto verificationRequestDto) {
        // RequestBody null 여부(9001)
        if (verificationRequestDto == null) {
            throw new CommonCustomException(CommonError.NO_REQUESTED_BODY);
        }
    }

    /**
     * 인증 처리상태 조회 에러(4102)
     *
     * @param verificationRequestDto
     * @return
     */
    @Override
    public void validateRequestItems(VerificationRequestDto verificationRequestDto) throws Exception {
        // 요청항목 유효성검사(4102)
        if (verificationRequestDto.getCompanyCd().length() != 5) {
            throw new RegisterCustomException(AuthenticationRegistrationError.INVALID_VALUE, "companyCd");
        }
        if (verificationRequestDto.getReqTxId().length() != 20) {
            throw new RegisterCustomException(AuthenticationRegistrationError.INVALID_VALUE, "reqTxId");
        }
        if (verificationRequestDto.getCertTxId().length() != 20) {
            throw new RegisterCustomException(AuthenticationRegistrationError.INVALID_VALUE, "certTxId");
        }
        String decryptPhoneNo = aesCipher.decrypt(verificationRequestDto.getPhoneNo());
        if (!decryptPhoneNo.startsWith("010") || decryptPhoneNo.substring(3).length() != 8 ||
        verificationRequestDto.getPhoneNo().length() != 24) {
            throw new RegisterCustomException(AuthenticationRegistrationError.INVALID_VALUE, "phoneNo");
        }
        String decryptUserNm = aesCipher.decrypt(verificationRequestDto.getUserNm());
        if (decryptUserNm.length() > 300 || !decryptUserNm.matches("^[ㄱ-ㅎ가-힣|a-zA-Z]*$")) {
            throw new RegisterCustomException(AuthenticationRegistrationError.INVALID_VALUE, "userNm");
        }
    }

    /**
     * 인증 처리상태 조회 에러(4103)
     * @param memberDto
     * @return
     */
    @Override
    public void confirmExpirationPeriod(MemberDto memberDto) {
        LocalDateTime date = LocalDateTime.now();
        String format = date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        if(memberDto == null) {
            throw new SearchCustomException(AuthenticationSearchError.OUT_OF_DATE_OR_NO_MATCHING_DATA);
        }
        if (memberDto.getReqEndDttm().compareTo(format) < 0) {
            throw new SearchCustomException(AuthenticationSearchError.OUT_OF_DATE_OR_NO_MATCHING_DATA,
                    memberDto.getTelcoTxId(), memberDto.getReqTxId(), memberDto.getCertTxId());
        }
    }

    private String initializeResultDttm() {
        LocalDateTime localDateTime = LocalDateTime.now();
        return DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(localDateTime);
    }

    private String initializeEncryptKey(String signTarget, MemberDto memberDto, String key) throws Exception {
        if(key.equals("DigitalSign")) {
            return aesCipher.encrypt(signTarget + memberDto.getSignTarget() + key + memberDto.getGender() + memberDto.getBirthday());
        }
        return aesCipher.encrypt(key + memberDto.getGender() + memberDto.getBirthday());
    }
}
