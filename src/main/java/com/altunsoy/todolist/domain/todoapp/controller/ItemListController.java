package com.altunsoy.todolist.domain.todoapp.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.altunsoy.todolist.common.BaseController;
import com.altunsoy.todolist.common.payload.ApiResponse;
import com.altunsoy.todolist.domain.todoapp.payload.ListRequest;
import com.altunsoy.todolist.domain.todoapp.persistence.entity.ItemList;
import com.altunsoy.todolist.domain.todoapp.service.ItemListService;
import com.altunsoy.todolist.exception.AuthenticationException;
import com.altunsoy.todolist.exception.BadRequestException;
import com.altunsoy.todolist.security.CurrentUser;
import com.altunsoy.todolist.security.UserPrincipal;

@RestController
@RequestMapping("/api/todo/itemList")
public class ItemListController extends BaseController<ItemListService> {

	@GetMapping("/getByUserName/{username}")
	public ResponseEntity<?> getOne(@PathVariable("username") String username) {
		return ResponseEntity.ok(service.findByUsername(username));
	}

	@PostMapping("/")
	public ResponseEntity<?> create(@CurrentUser UserPrincipal currentUser, @Valid @RequestBody ListRequest itemlist) {
		return ResponseEntity.ok(service.save(currentUser, itemlist));
	}

	@Override
	public ResponseEntity<?> getAll(@CurrentUser UserPrincipal currentUser) {
		List<ItemList> lists = service.getUsersList(currentUser);
		ApiResponse<List<ItemList>> response = new ApiResponse<List<ItemList>>(true, "ok", lists);
		return ResponseEntity.ok(response);
	}
}
