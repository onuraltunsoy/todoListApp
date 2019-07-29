package com.altunsoy.todolist.domain.user.service;

import org.springframework.stereotype.Service;

import com.altunsoy.todolist.common.BaseService;
import com.altunsoy.todolist.domain.user.persistence.entity.UserRole;
import com.altunsoy.todolist.domain.user.persistence.entity.UserRoleEnum;
import com.altunsoy.todolist.domain.user.persistence.repository.UserRoleRepository;
import com.altunsoy.todolist.exception.AppException;

@Service
public class UserRoleService extends BaseService<UserRoleRepository, UserRole> {

	public UserRole findByName(UserRoleEnum roleUser) {
		return repository.findByName(roleUser).orElseThrow(() -> new AppException("User Role not set."));
	}

}
