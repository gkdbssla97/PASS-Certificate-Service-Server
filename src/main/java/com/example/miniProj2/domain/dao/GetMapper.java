package com.example.miniProj2.domain.dao;

import com.example.miniProj2.domain.dto.MemberDto;
import com.example.miniProj2.domain.vo.AccessTokenVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface GetMapper {
    AccessTokenVO getAccessToken();
    List<MemberDto> findAuthorizedMemberList();
    MemberDto findMemberByCertTxId(String certTxId);



}
