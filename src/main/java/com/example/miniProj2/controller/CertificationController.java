package com.example.miniProj2.controller;

import com.example.miniProj2.domain.dto.CertificationResponseDto;
import com.example.miniProj2.domain.dto.MemberDto;
import com.example.miniProj2.domain.dto.VerificationRequestDto;
import com.example.miniProj2.domain.dto.VerificationResponseDto;
import com.example.miniProj2.domain.service.CertificateService;
import com.example.miniProj2.domain.service.VerificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

import static java.util.concurrent.TimeUnit.SECONDS;

@RestController
@RequiredArgsConstructor
public class CertificationController {

    private final CertificateService certificateService;
    private final VerificationService verificationService;

    @PostMapping("/v1/certification/notice")
    public ResponseEntity<CertificationResponseDto> requestCertification(@RequestHeader(value = "Authorization", required = false) String authorization,
                                                                         @RequestBody(required = false) Map<String, String> requestData) throws Exception {

        certificateService.verifyAccessToken(authorization);

        certificateService.validateRequestBody(requestData);

        certificateService.validateRequiredKeys(requestData);

        certificateService.validateRequestItems(requestData);

        List<MemberDto> authorizedMemberList = certificateService.findAuthorizedMemberList();

        return certificateService.getCertificationResponseEntity(requestData, authorizedMemberList);
    }

    @PostMapping("/v1/certification/result")
    public ResponseEntity<VerificationResponseDto> requestVerification(@RequestBody(required = false) @Valid VerificationRequestDto verificationRequestDto)
            throws Exception {
        System.out.println(verificationRequestDto);
        verificationService.validateRequestBody(verificationRequestDto);

        verificationService.validateRequiredKeys(verificationRequestDto);

        verificationService.validateRequestItems(verificationRequestDto);
        System.out.println("certTxId: " + verificationRequestDto.getCertTxId());
        MemberDto findMemberDto = verificationService.findMemberByCertTxId(verificationRequestDto.getCertTxId());
        System.out.println(findMemberDto);
        verificationService.confirmExpirationPeriod(findMemberDto);
        VerificationResponseDto verificationResponseDto = verificationService.setVerificationResponse(findMemberDto, verificationRequestDto.getSignTarget());
        verificationService.saveVerificationResult(verificationResponseDto);
        System.out.println(verificationResponseDto);
        return ResponseEntity.ok()
                .body(verificationResponseDto);
    }

    public Future<String> getName(String name) {
        final ExecutorService executorService = Executors.newSingleThreadExecutor();
        FutureTask<String> futureTask = new FutureTask<>(() -> {
            SECONDS.sleep(7);
            return "[" + "hi" + "]";
        });
        executorService.execute(futureTask);
        System.out.println(futureTask.toString());
        return futureTask;
    }
}
