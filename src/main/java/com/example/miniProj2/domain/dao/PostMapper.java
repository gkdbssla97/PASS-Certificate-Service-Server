package com.example.miniProj2.domain.dao;

import com.example.miniProj2.domain.dto.VerificationResponseDto;
import com.example.miniProj2.domain.vo.MemberVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface PostMapper {
    void saveTxId(@Param("memberVO")MemberVO memberVO);
    void saveVerificationResult(@Param("VerificationResponseDto") VerificationResponseDto verificationResponseDto);
}
