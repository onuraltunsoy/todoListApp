package com.altunsoy.todolist.domain.user.persistence.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.altunsoy.todolist.domain.user.persistence.entity.User;
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	public Optional<User> findByEmail(String email);

	public Optional<User> findByUsernameOrEmail(String username, String email);

	public List<User> findByIdIn(List<Long> userIds);

	public Optional<User> findByUsername(String username);

	public Boolean existsByUsername(String username);

	public Boolean existsByEmail(String email);

	@Query("SELECT u FROM User u LEFT JOIN FETCH u.todoLists lists LEFT JOIN FETCH lists.items itms LEFT JOIN FETCH itms.status stt where u.username = ?1")
	public Optional<User> findAll(String username);
}
