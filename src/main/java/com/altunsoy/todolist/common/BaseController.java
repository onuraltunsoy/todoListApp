package com.altunsoy.todolist.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.altunsoy.todolist.exception.CustomResponseEntityExceptionHandler;
import com.altunsoy.todolist.security.CurrentUser;
import com.altunsoy.todolist.security.UserPrincipal;

public abstract class BaseController<S extends BaseService> extends CustomResponseEntityExceptionHandler {

	@Autowired
	protected S service;

	@GetMapping("/")
	public ResponseEntity<?> getAll(@CurrentUser UserPrincipal currentUser) {

		return ResponseEntity.ok(service.getAll());
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> getOne(@CurrentUser UserPrincipal currentUser, @PathVariable("id") long id) {
		return ResponseEntity.ok(service.get(id));
	}

	@GetMapping("/{id}/exist")
	public ResponseEntity<?> exist(@CurrentUser UserPrincipal currentUser, @PathVariable("id") long id) {
		boolean exist = service.Exist(id);
		return exist ? ResponseEntity.ok(exist) : ResponseEntity.notFound().build();
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@CurrentUser UserPrincipal currentUser, @PathVariable("id") long id) {
		service.delete(id);
		return ResponseEntity.accepted().build();
	}
}
