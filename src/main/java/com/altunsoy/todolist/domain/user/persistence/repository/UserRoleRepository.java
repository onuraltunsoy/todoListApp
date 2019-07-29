package com.altunsoy.todolist.domain.user.persistence.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.altunsoy.todolist.domain.user.persistence.entity.UserRole;
import com.altunsoy.todolist.domain.user.persistence.entity.UserRoleEnum;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, Long> {
	public Optional<UserRole> findByName(UserRoleEnum roleUser);
}