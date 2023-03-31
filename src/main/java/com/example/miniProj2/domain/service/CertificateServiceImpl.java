package com.example.miniProj2.domain.service;

import com.example.miniProj2.domain.dao.GetMapper;
import com.example.miniProj2.domain.vo.AccessTokenVO;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Builder
public class CertificateServiceImpl implements CertificateService {

    private final GetMapper getMapper;

    @Override
    public AccessTokenVO getAccessToken() {
        return getMapper.getAccessToken();
    }
}
