package com.example.miniProj2.domain.service;

import com.example.miniProj2.domain.dao.GetMapper;
import com.example.miniProj2.domain.dao.PostMapper;
import com.example.miniProj2.domain.dto.CertificationResponseDto;
import com.example.miniProj2.domain.dto.MemberDto;
import com.example.miniProj2.domain.vo.AccessTokenVO;
import com.example.miniProj2.domain.vo.MemberVO;
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
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

import static com.example.miniProj2.util.Constants.*;

@Service
@RequiredArgsConstructor
@Builder
public class CertificateServiceImpl implements CertificateService {

    private final GetMapper getMapper;
    private final PostMapper postMapper;
    private final AESCipher aesCipher = new AESCipher(ENCRYPT_KEY);

    @Override
    public AccessTokenVO getAccessToken() {
        return getMapper.getAccessToken();
    }

    @Override
    public List<MemberDto> findAuthorizedMemberList() {
        return getMapper.findAuthorizedMemberList();
    }

    @Override
    public CertificationResponseDto setCertificationResponse(Map<String, String> requestData) {
        return CertificationResponseDto.builder()
                .reqTxId(requestData.get("reqTxId"))
                .certTxId(initializeCertTxId())
                .telcoTxId(initializeTelcoTxId())
                .build();
    }
    @Override
    public void saveTxId(MemberVO memberVO) {
        postMapper.saveTxId(memberVO);
    }

    private String initializeCertTxId() {
        return extractRandomId();
    }

    private String initializeTelcoTxId() {
        return extractRandomId();
    }

    private String extractRandomId() {
        Random rnd = new Random();
        StringBuilder randomCertTxId = new StringBuilder();
        for (int i = 0; i < 20; i++) {
            if (rnd.nextBoolean()) {
                randomCertTxId.append((char) ((int) (rnd.nextInt(26)) + 97));
            } else {
                randomCertTxId.append((rnd.nextInt(10)));
            }
        }
        return randomCertTxId.toString();
    }

    private String initializeReqTxId() { //타임스태프로 20자리 값 요청(특수문자 사용 X)
        LocalDateTime localDateTime = LocalDateTime.now();
        String timeStamp = DateTimeFormatter.ofPattern("yyyyMMddHHmmss").format(localDateTime);

        return timeStamp + String.valueOf(generateAuthNo());
    }

    private String initializeReqEndDttm() {
        //인증요청의 유효 만료일시 "YYYY-MM-DD hh:mm:ss" format
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = Calendar.getInstance().getTime();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MINUTE, 5);

        return simpleDateFormat.format(cal.getTime());
    }

    private int generateAuthNo() {
        return ThreadLocalRandom.current().nextInt(100000, 1000000);
    }

    /**
     * 공통 에러(9000)
     *
     * @return
     */
    @Override
    public void verifyAccessToken(String authorization) {
        // AccessToken value 검증(9000)
        if (authorization == null || !authorization.equals(BEARER_TOKEN + ACCESS_TOKEN)) {
            throw new CommonCustomException(CommonError.NO_PERMISSION);
        }
    }

    /**
     * 공통 에러(9001, 9002)
     *
     * @return
     */
    @Override
    public void validateRequestBody(Map<String, String> requestData) {
        // RequestBody null 여부(9001)
        if (requestData == null || requestData.size() == 0) {
            throw new CommonCustomException(CommonError.NO_REQUESTED_BODY);
        }

        // RequestBody format 검증(9002)
        if (!new HashSet<>(allKeysByRegister()).containsAll(requestData.keySet())) {
            throw new CommonCustomException(CommonError.REQUESTED_BODY_FORMAT_ERROR);
        }
    }
    /**
     * 공통 에러(9099)
     * @return
     */

    /**
     * 인증 알림내용 등록 에러(3101)
     *
     * @param requestData
     * @return
     */
    @Override
    public void validateRequiredKeys(Map<String, String> requestData) {
        // 필수항목 검증(3101)
        for (String key : Constants.requiredKeysByRegister) {
            if (requestData.get(key) == null) {
                throw new RegisterCustomException(AuthenticationRegistrationError.MISSING_REQUIRED_ITEM, key);
            }
        }
    }

    /**
     * 인증 알림내용 등록 에러(3102)
     * @param requestData
     * @return
     */
    @Override
    public void validateRequestItems(Map<String, String> requestData) throws Exception {
        // 요청항목 유효성검사(3102)
        if (requestData.get("companyCd").length() != 5) {
            throw new RegisterCustomException(AuthenticationRegistrationError.INVALID_VALUE, "companyCd");
        }
        if (requestData.get("reqTxId").length() != 20) {
            throw new RegisterCustomException(AuthenticationRegistrationError.INVALID_VALUE, "reqTxId");
        }
        if (!serviceTycd.contains(requestData.get("serviceTycd"))) {
            throw new RegisterCustomException(AuthenticationRegistrationError.INVALID_VALUE, "serviceTycd");
        }
        if (requestData.get("telcoTycd") == null || !telcoTycd.contains(requestData.get("telcoTycd"))) {
            throw new RegisterCustomException(AuthenticationRegistrationError.INVALID_VALUE, "telcoTycd");
        }
        String decryptPhoneNo = aesCipher.decrypt(requestData.get("phoneNo"));
        if (!decryptPhoneNo.startsWith("010") || decryptPhoneNo.substring(3).length() != 8) {
            throw new RegisterCustomException(AuthenticationRegistrationError.INVALID_VALUE, "phoneNo");
        }
        String decryptUserNm = aesCipher.decrypt(requestData.get("userNm"));
        if (decryptUserNm.length() > 300 || !decryptUserNm.matches("^[ㄱ-ㅎ가-힣|a-zA-Z]*$")) {
            throw new RegisterCustomException(AuthenticationRegistrationError.INVALID_VALUE, "userNm");
        }
        String decryptBirthday = aesCipher.decrypt(requestData.get("birthday"));
        if (decryptBirthday.length() != 6) { //YYMMDD 형식?
            throw new RegisterCustomException(AuthenticationRegistrationError.INVALID_VALUE, "birthday");
        }
        String decryptGender = aesCipher.decrypt(requestData.get("gender"));
        if (Integer.parseInt(decryptGender) > 9 || Integer.parseInt(decryptGender) < 0) {
            throw new RegisterCustomException(AuthenticationRegistrationError.INVALID_VALUE, "gender");
        }
        if (requestData.get("reqTitle").length() > 50) {
            throw new RegisterCustomException(AuthenticationRegistrationError.INVALID_VALUE, "reqTitle");
        }
        if (requestData.get("reqCSPhoneNo").length() > 12) {
            throw new RegisterCustomException(AuthenticationRegistrationError.INVALID_VALUE, "reqCSPhoneNo");
        }
        if (requestData.get("reqEndDttm").length() != 19) {
            throw new RegisterCustomException(AuthenticationRegistrationError.INVALID_VALUE, "reqEndDttm");
        }
        int[] day = Arrays.stream(requestData.get("reqEndDttm")
                        .substring(0, 10).split("-"))
                .mapToInt(Integer::parseInt)
                .toArray();
        int[] time = Arrays.stream(requestData.get("reqEndDttm")
                        .substring(11).split(":"))
                .mapToInt(Integer::parseInt)
                .toArray();
        LocalDateTime reqEndDttm = LocalDateTime.of(day[0], day[1], day[2], time[0], time[1], time[2]);
        LocalDateTime now = LocalDateTime.now();
        if (reqEndDttm.isBefore(now) || reqEndDttm.isEqual(now)) {
            System.out.println("Certification reqEndDttm");
            throw new SearchCustomException(AuthenticationSearchError.OUT_OF_DATE_OR_NO_MATCHING_DATA);
        }
        if (Integer.parseInt(requestData.get("signTargetTycd")) > 4 || Integer.parseInt(requestData.get("signTargetTycd")) < 0) {
            throw new RegisterCustomException(AuthenticationRegistrationError.INVALID_VALUE, "signTargetTycd");
        }
        if (requestData.get("isUserAgreement") != null && !List.of("Y", "N").contains(requestData.get("isUserAgreement"))) {
            throw new RegisterCustomException(AuthenticationRegistrationError.INVALID_VALUE, "isUserAgreement");
        }
        if (requestData.get("isDigitalSign") != null && !List.of("Y", "N").contains(requestData.get("isDigitalSign"))) {
            throw new RegisterCustomException(AuthenticationRegistrationError.INVALID_VALUE, "isDigitalSign");
        }
        if (requestData.get("isPASSVerify") != null && !List.of("Y", "N").contains(requestData.get("isPASSVerify"))) {
            throw new RegisterCustomException(AuthenticationRegistrationError.INVALID_VALUE, "isPASSVerify");
        }
        if (requestData.get("isNotification") != null && !List.of("Y", "N").contains(requestData.get("isNotification"))) {
            throw new RegisterCustomException(AuthenticationRegistrationError.INVALID_VALUE, "isNotification");
        }
    }

    /**
     * 인증 알림내용 등록 에러(3103)
     * @return
     */
    @Override
    public ResponseEntity<CertificationResponseDto> getCertificationResponseEntity(Map<String, String> requestData, List<MemberDto> authorizedMemberList) throws Exception {
        for (MemberDto member : authorizedMemberList) {
            if (member.getUserNm().equals(aesCipher.decrypt(requestData.get("userNm")))) {
                ResponseEntity<CertificationResponseDto> certificationResponseDto = isValidMemberData(requestData, member);
                if (certificationResponseDto != null) return certificationResponseDto;
            }
        }
        // 사용자 정보 확인(3103)
        throw new RegisterCustomException(AuthenticationRegistrationError.USER_NOT_FOUND);
    }

    private ResponseEntity<CertificationResponseDto> isValidMemberData(Map<String, String> requestData, MemberDto member) throws Exception {
        System.out.println("***** 사용자 검증 *****");
        System.out.println("인증된 사용자 이름: [" + member.getUserNm() + "] 요청된 사용자 이름: [" + aesCipher.decrypt(requestData.get("userNm")) + "]");
        System.out.println("인증된 사용자 전화번호: [" + member.getPhoneNo() + "] 요청된 사용자 전화번호: [" + aesCipher.decrypt(requestData.get("phoneNo")) + "]");
        System.out.println("인증된 사용자 성별: [" + member.getGender() + "] 요청된 사용자 성별: [" + aesCipher.decrypt(requestData.get("gender")) + "]");
        System.out.println("인증된 사용자 통신사: [" + member.getTelcoTycd() + "] 요청된 사용자 통신사: [" + requestData.get("telcoTycd") + "]");
        System.out.println("인증된 사용자 생년월일: [" + member.getBirthday() + "] 요청된 사용자 생년월일: [" + "19" +aesCipher.decrypt(requestData.get("birthday")) + "]");

            if (member.getPhoneNo().equals(aesCipher.decrypt(requestData.get("phoneNo")))
                    && member.getGender().equals(aesCipher.decrypt(requestData.get("gender")))
                    && member.getTelcoTycd().equals(requestData.get("telcoTycd"))
                    && member.getBirthday().equals("19" + aesCipher.decrypt(requestData.get("birthday")))) {
                CertificationResponseDto certificationResponseDto = setCertificationResponse(requestData);
                System.out.println("reqDttm: " + requestData.get("reqEndDttm"));
                saveTxId(new MemberVO(member.getUserNm(), certificationResponseDto.getTelcoTxId(),
                        certificationResponseDto.getReqTxId(), certificationResponseDto.getCertTxId(), requestData.get("reqEndDttm")));
                return ResponseEntity.ok()
                        .body(certificationResponseDto);
            }
            // 사용자 정보 검증(4103)
            throw new SearchCustomException(AuthenticationSearchError.OUT_OF_DATE_OR_NO_MATCHING_DATA);
    }
}
