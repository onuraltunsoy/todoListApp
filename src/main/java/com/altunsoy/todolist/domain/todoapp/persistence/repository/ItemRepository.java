package com.altunsoy.todolist.domain.todoapp.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.altunsoy.todolist.domain.todoapp.persistence.entity.Item;
import com.altunsoy.todolist.domain.todoapp.persistence.entity.ItemList;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {

	@Query("SELECT i FROM Item i LEFT JOIN FETCH i.dependentItems where i.id = ?1")
	public Item getWithDependentItemsById(Long id);

	@Query("SELECT i FROM Item i LEFT JOIN FETCH i.dependentItems where i.id = ?1")
	public List<Item> findByIdIn(Iterable<Long> id);
	
	List<Item> findByList(ItemList list);

}
