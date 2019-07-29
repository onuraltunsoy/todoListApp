package com.altunsoy.todolist.domain.todoapp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import com.altunsoy.todolist.common.BaseService;
import com.altunsoy.todolist.domain.todoapp.payload.ListRequest;
import com.altunsoy.todolist.domain.todoapp.persistence.entity.ItemList;
import com.altunsoy.todolist.domain.todoapp.persistence.repository.ItemListRepository;
import com.altunsoy.todolist.domain.user.service.UserService;
import com.altunsoy.todolist.security.UserPrincipal;

@Service
public class ItemListService extends BaseService<ItemListRepository, ItemList> {

	@Autowired
	private UserService userService;

	@PreAuthorize("#userName == principal.getUsername() or hasRole('ROLE_ADMIN')")
	public List<ItemList> findByUsername(String username) {
		List<ItemList> list = repository.findByUsername(username);
		return list;
	}

	@Override
	public ItemList save(ItemList list) {
		return repository.save(list);
	}

	public ItemList save(UserPrincipal currentUser, ListRequest request) {
		ItemList list = new ItemList();
		list.setName(request.getName());
		list.setCreatedBy(userService.get(currentUser.getId()));
		return repository.save(list);
	}

	public List<ItemList> getUsersList(UserPrincipal user) {
		return repository.findByUsername(user.getUsername());
	}

}
