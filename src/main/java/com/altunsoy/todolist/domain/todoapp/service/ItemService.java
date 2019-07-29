package com.altunsoy.todolist.domain.todoapp.service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import com.altunsoy.todolist.common.BaseService;
import com.altunsoy.todolist.common.entity.DateAudit;
import com.altunsoy.todolist.common.payload.ApiResponse;
import com.altunsoy.todolist.domain.todoapp.payload.ItemRequest;
import com.altunsoy.todolist.domain.todoapp.persistence.entity.Item;
import com.altunsoy.todolist.domain.todoapp.persistence.entity.ItemList;
import com.altunsoy.todolist.domain.todoapp.persistence.entity.StatusName;
import com.altunsoy.todolist.domain.todoapp.persistence.repository.ItemRepository;
import com.altunsoy.todolist.exception.BadRequestException;
import com.altunsoy.todolist.exception.HasUnfinishedDependenciesException;
import com.altunsoy.todolist.exception.NotFoundException;
import com.altunsoy.todolist.security.UserPrincipal;

@Service
public class ItemService extends BaseService<ItemRepository, Item> {

	@Autowired
	private StatusService statusService;

	@Autowired
	private ItemListService itemListService;

	public Item doneItem(long id) {
		Item item = repository.getWithDependentItemsById(id);
		List<Item> unFinishedItems = item.getDependentItems().stream()
				.filter((dependentItem) -> !dependentItem.getStatus().getIsDone()).collect(Collectors.toList());

		if (unFinishedItems != null && !unFinishedItems.isEmpty()) {
			ApiResponse<List<Item>> apiResponse = new ApiResponse<>(false, "item has Unfinished dependencies",
					unFinishedItems);
			throw new HasUnfinishedDependenciesException(apiResponse);
		}

		item.setStatus(statusService.findByName(StatusName.DONE));
		return repository.save(item);
	}

	@Override
	public Item save(Item item) {
		item.setStatus(statusService.findByName(StatusName.NEW));
		item.setDateAudit(new DateAudit());
		if (item.getList() != null) {
			if (item.getList().getCreatedBy().getId() == null) {
				throw new BadRequestException("createdBy id is required");
			}
		}
		return repository.save(item);
	}

	public Item saveToList(Long listId, Item item) {
		ItemList itemList = itemListService.get(listId);
		Set<Long> dependentIds = item.getDependentItems().stream().map((i) -> i.getId()).collect(Collectors.toSet());
		List<Item> dependentItems = repository.findByIdIn(dependentIds);
		item.setDependentItems(dependentItems);
		item.setList(itemList);
		return save(item);
	}

	public Item startItem(long id) {
		Item item = repository.findById(id).orElseThrow(() -> new NotFoundException("No data found with value:" + id));
		item.setStatus(statusService.findByName(StatusName.INPROGRESS));
		return repository.save(item);
	}

	public Item saveToList(long listId, @Valid ItemRequest itemRequest) {
		ItemList itemList = itemListService.get(listId);
		List<Item> dependentItems = repository.findByIdIn(itemRequest.getDependentItems());

		Item item = new Item();
		item.setStatus(statusService.findByName(StatusName.NEW));
		item.setList(itemList);
		item.setDependentItems(dependentItems);
		item.setName(itemRequest.getName());
		item.setDeadline(itemRequest.getDeadline());
		item.setdescription(itemRequest.getDescription());

		return save(item);
	}
	
	public List<Item> getListItems(UserPrincipal currentUser ,long listId) {
		ItemList itemList = itemListService.get(listId);

		if(itemList.getCreatedBy().getId() != currentUser.getId()) {
				throw new BadRequestException("createdBy isnot match");
				
		}
		List<Item> listItems = repository.findByList(itemList);
		
		return listItems;
	}
}
