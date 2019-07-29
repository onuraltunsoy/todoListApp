package com.altunsoy.todolist.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.altunsoy.todolist.common.payload.ApiResponse;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BadRequestException extends AppException {

	private static final long serialVersionUID = 1L;

	public BadRequestException(ApiResponse response) {
		super(response);
	}

	public BadRequestException(String message) {
		super(message);
	}

	public BadRequestException(String message, Throwable cause) {
		super(message, cause);
	}
}
