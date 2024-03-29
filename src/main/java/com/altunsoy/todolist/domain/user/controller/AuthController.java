package com.altunsoy.todolist.domain.user.controller;

import java.util.HashSet;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.altunsoy.todolist.common.payload.ApiResponse;
import com.altunsoy.todolist.common.payload.FieldError;
import com.altunsoy.todolist.domain.user.payload.LoginRequest;
import com.altunsoy.todolist.domain.user.payload.SignUpRequest;
import com.altunsoy.todolist.domain.user.service.UserService;
import com.altunsoy.todolist.exception.CustomResponseEntityExceptionHandler;

@RestController
@RequestMapping("/api/auth")
public class AuthController extends CustomResponseEntityExceptionHandler {

	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	PasswordEncoder passwordEncoder;

	@Autowired
	UserService userService;

	@PostMapping("/signin")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest request) {

		return ResponseEntity.ok(userService.authenticate(request.getUsernameOrEmail(), request.getPassword()));
	}

	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequest request) {
		boolean success = true;
		ResponseEntity<?> response = null;
		Set<FieldError> errors = new HashSet<FieldError>();
		if (userService.checkUserName(request.getUsername())) {
			success = false;
			errors.add(new FieldError("username", "Username is already taken!", request.getUsername()));
		}

		if (userService.checkUserMail(request.getEmail())) {
			success = false;
			errors.add(new FieldError("email", "Email Address already in use!", request.getEmail()));
		}

		if (success) {
			userService.save(request);
			response = ResponseEntity.ok(userService.authenticate(request.getUsername(), request.getPassword()));
		} else {
			ApiResponse<Set<FieldError>> apiResponse = new ApiResponse<Set<FieldError>>(success, "", errors);
			response = ResponseEntity.badRequest().body(apiResponse);
		}

		return response;
	}

}
