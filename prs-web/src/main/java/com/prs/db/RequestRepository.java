package com.prs.db;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.prs.business.*;
public interface RequestRepository extends CrudRepository<Request, Integer> {
	
	List<Request> findAllById(Request id);
	List<Request> findAllByStatusAndUserIdNot(String status, int id);

}
