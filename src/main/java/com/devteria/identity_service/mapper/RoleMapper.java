package com.devteria.identity_service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.devteria.identity_service.dto.request.RoleRequest;
import com.devteria.identity_service.dto.response.RoleResponse;
import com.devteria.identity_service.entity.Role;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    @Mapping(target = "permissions", ignore = true)
    // Request truyền vào permision chỉ là String, Nhưng role lại muôn nhận là Obj Permission. nên ignore ở đây.
    Role toRole(RoleRequest request);

    RoleResponse toRoleResponse(Role role);
}
