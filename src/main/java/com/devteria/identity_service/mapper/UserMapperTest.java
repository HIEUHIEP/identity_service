package com.devteria.identity_service.mapper;

import com.devteria.identity_service.entity.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapperTest {
    User selectUser(String id);
}
