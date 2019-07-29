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
import com.altunsoy.todolist.domain.todoapp.payload.ItemRequest;
import com.altunsoy.todolist.domain.todoapp.persistence.entity.Item;
import com.altunsoy.todolist.domain.todoapp.persistence.entity.ItemList;
import com.altunsoy.todolist.domain.todoapp.service.ItemService;
import com.altunsoy.todolist.security.CurrentUser;
import com.altunsoy.todolist.security.UserPrincipal;

@RestController
@RequestMapping("/api/todo/item")
public class ItemController extends BaseController<ItemService> {

	@GetMapping("/{id}")
	public ResponseEntity<?> getOne(@CurrentUser UserPrincipal currentUser, @PathVariable("id") long id) {
		return ResponseEntity.ok(service.get(id));
	}

	@PostMapping("/{id}/done")
	public ResponseEntity<?> doneTodo(@CurrentUser UserPrincipal currentUser, @PathVariable("id") long id) {
		return ResponseEntity.ok(service.doneItem(id));
	}

	@PostMapping("/{id}/start")
	public ResponseEntity<?> startTodo(@CurrentUser UserPrincipal currentUser, @PathVariable("id") long id) {
		return ResponseEntity.ok(service.startItem(id));
	}

	@PostMapping()
	public ResponseEntity<?> create(@Valid @RequestBody Item item) {
		item = service.save(item);
		return ResponseEntity.ok(item);
	}

	@PostMapping("/{listId}")
	public ResponseEntity<?> create(@CurrentUser UserPrincipal currentUser, @PathVariable("listId") long listId,
			@Valid @RequestBody ItemRequest itemRequest) {
		return ResponseEntity.ok(service.saveToList(listId, itemRequest));
	}
	@GetMapping("/list/{listId}")
	public ResponseEntity<?> getListItems(@CurrentUser UserPrincipal currentUser, @PathVariable("listId") long listId) {
		
		List<Item> items = service.getListItems(currentUser,listId);
		
		ApiResponse<List<Item>> response = new ApiResponse<List<Item>>(true, "ok", items);
		
		return ResponseEntity.ok(response);
	}

}
