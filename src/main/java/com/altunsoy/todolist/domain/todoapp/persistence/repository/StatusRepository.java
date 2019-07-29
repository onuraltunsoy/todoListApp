package com.altunsoy.todolist.domain.todoapp.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.altunsoy.todolist.domain.todoapp.persistence.entity.Status;
import com.altunsoy.todolist.domain.todoapp.persistence.entity.StatusName;

@Repository
public interface StatusRepository extends JpaRepository<Status, Long> {
	public Status findByName(StatusName name);
}
