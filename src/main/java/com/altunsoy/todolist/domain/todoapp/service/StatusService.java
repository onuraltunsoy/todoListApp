package com.altunsoy.todolist.domain.todoapp.service;

import org.springframework.stereotype.Service;

import com.altunsoy.todolist.common.BaseService;
import com.altunsoy.todolist.domain.todoapp.persistence.entity.Status;
import com.altunsoy.todolist.domain.todoapp.persistence.entity.StatusName;
import com.altunsoy.todolist.domain.todoapp.persistence.repository.StatusRepository;

@Service
public class StatusService extends BaseService<StatusRepository, Status> {

	public Status findByName(StatusName name) {
		return repository.findByName(name);
	};

}
