package com.example.miniProj2.domain.dao;

import com.example.miniProj2.domain.vo.AccessTokenVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface GetMapper {
    AccessTokenVO getAccessToken();
}
