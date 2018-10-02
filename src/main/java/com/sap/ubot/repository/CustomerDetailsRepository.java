package com.sap.ubot.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import com.sap.ubot.entity.CustomerDetails;

@Transactional
public interface CustomerDetailsRepository extends CrudRepository<CustomerDetails, Long> {
	
	

}
