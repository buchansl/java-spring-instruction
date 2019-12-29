package com.stuffy.db;

import org.springframework.data.repository.CrudRepository;

import com.stuffy.bussiness.Stuffy;

public interface StuffyRepository extends CrudRepository<Stuffy, Integer> {

}
