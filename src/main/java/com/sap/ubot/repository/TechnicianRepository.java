package com.sap.ubot.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import com.sap.ubot.entity.TechnicianEntity;

@Transactional
public interface TechnicianRepository extends CrudRepository<TechnicianEntity, Long> {
	

}
