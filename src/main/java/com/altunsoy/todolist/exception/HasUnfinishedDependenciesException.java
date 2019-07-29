package com.altunsoy.todolist.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.altunsoy.todolist.common.payload.ApiResponse;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class HasUnfinishedDependenciesException extends AppException {

	private static final long serialVersionUID = 1L;

	public HasUnfinishedDependenciesException(ApiResponse response) {
		super(response);
	}
}
