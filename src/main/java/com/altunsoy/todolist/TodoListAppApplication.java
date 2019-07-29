package com.altunsoy.todolist;

import java.util.Collections;
import java.util.TimeZone;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.altunsoy.todolist.domain.todoapp.persistence.entity.Status;
import com.altunsoy.todolist.domain.todoapp.persistence.entity.StatusName;
import com.altunsoy.todolist.domain.todoapp.service.StatusService;
import com.altunsoy.todolist.domain.user.persistence.*;
import com.altunsoy.todolist.domain.user.persistence.entity.User;
import com.altunsoy.todolist.domain.user.persistence.entity.UserRole;
import com.altunsoy.todolist.domain.user.persistence.entity.UserRoleEnum;
import com.altunsoy.todolist.domain.user.service.UserRoleService;
import com.altunsoy.todolist.domain.user.service.UserService;


@SpringBootApplication
@EntityScan(basePackages = {"com.altunsoy.todolist.domain.todoapp.persistence","com.altunsoy.todolist.domain.user.persistence"})
public class TodoListAppApplication {

	@Autowired
	private StatusService itemStatusService;

	@Autowired
	private UserRoleService userRoleService;

	@Autowired
	private UserService userSerice;

	@Autowired
	PasswordEncoder passwordEncoder;

	@PostConstruct
	void init() {
		TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
		/** if first run persist initial data to db. */
		if (!userSerice.Exist(1L)) {
			createUserRole();
			createItemStatus();
			createAdmin();
		}
	}

	public static void main(String[] args) {
		SpringApplication.run(TodoListAppApplication.class, args);
	}

	private void createUserRole() {
		UserRole userAdmin = new UserRole();
		UserRole userRole = new UserRole();
		userAdmin.setName(UserRoleEnum.ROLE_ADMIN);
		userRole.setName(UserRoleEnum.ROLE_USER);
		userRoleService.save(userAdmin, userRole);

	}

	private void createItemStatus() {
		Status newItem = new Status(StatusName.NEW, "Item Created and waiting for action", false);
		Status progress = new Status(StatusName.INPROGRESS, "Item is in Progress", false);
		Status done = new Status(StatusName.DONE, "Item is done", true);
		itemStatusService.save(newItem, progress, done);
	}

	private void createAdmin() {
		User user = new User();
		user.setEmail("admin@admin.com");
		user.setName("admin");
		user.setUsername("admin");
		user.setPassword("123456");
		UserRole userRole = userRoleService.findByName(UserRoleEnum.ROLE_ADMIN);
		user.setRoles(Collections.singleton(userRole));
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		userSerice.save(user);
	}
}
