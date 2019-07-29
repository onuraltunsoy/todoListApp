package com.altunsoy.todolist.domain.user.service;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.altunsoy.todolist.common.BaseService;
import com.altunsoy.todolist.domain.user.payload.AuthenticationResponse;
import com.altunsoy.todolist.domain.user.payload.SignUpRequest;
import com.altunsoy.todolist.domain.user.persistence.entity.User;
import com.altunsoy.todolist.domain.user.persistence.entity.UserRole;
import com.altunsoy.todolist.domain.user.persistence.entity.UserRoleEnum;
import com.altunsoy.todolist.domain.user.persistence.repository.UserRepository;
import com.altunsoy.todolist.exception.NotFoundException;
import com.altunsoy.todolist.security.JwtTokenProvider;

@Service
public class UserService extends BaseService<UserRepository, User> {

	@Autowired
	UserRoleService roleService;

	@Autowired
	PasswordEncoder passwordEncoder;

	@Autowired
	JwtTokenProvider tokenProvider;

	@Autowired
	AuthenticationManager authenticationManager;

	public User save(SignUpRequest signUpRequest) {
		User user = new User(signUpRequest.getName(), signUpRequest.getUsername(), signUpRequest.getEmail(),
				signUpRequest.getPassword());

		user.setPassword(passwordEncoder.encode(user.getPassword()));

		UserRole userRole = roleService.findByName(UserRoleEnum.ROLE_USER);

		user.setRoles(Collections.singleton(userRole));

		User result = super.save(user);
		return result;
	}

	@Override
	@PreAuthorize("#id == principal.getId() or hasRole('ROLE_ADMIN')")
	public User get(Long id) {
		return super.get(id);
	}

	@PreAuthorize("#userName == principal.getUsername() or hasRole('ROLE_ADMIN')")
	public User get(String userName) {
		return repository.findAll(userName).orElseThrow(() -> new NotFoundException());
	}

	public boolean checkUserName(String name) {
		return repository.existsByUsername(name);
	}

	public boolean checkUserMail(String mail) {
		return repository.existsByEmail(mail);
	}

	public AuthenticationResponse authenticate(String mailOrUsername, String pass) {
		Authentication authentication = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(mailOrUsername, pass));
		SecurityContextHolder.getContext().setAuthentication(authentication);

		String jwt = tokenProvider.generateToken(authentication);
		AuthenticationResponse auth = new AuthenticationResponse(jwt);
		boolean admin = authentication.getAuthorities().stream()
				.anyMatch(a -> UserRoleEnum.ROLE_ADMIN.name().equals(a.getAuthority()));
		auth.setAdmin(admin);
		auth.setUser(get(mailOrUsername));

		return auth;

	}

}
