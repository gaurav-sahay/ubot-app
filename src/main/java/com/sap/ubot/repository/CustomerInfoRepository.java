package com.sap.ubot.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.sap.ubot.entity.CustomerInfo;
import com.sap.ubot.entity.CustomerInfoKey;

public interface CustomerInfoRepository extends CrudRepository<CustomerInfo, CustomerInfoKey> {
	
	
	public CustomerInfo findByCustomerInfoKeyMobileNo(@Param("mobileNo") String mobileNo);
	
	public CustomerInfo findByCustomerInfoKeyContractAccountOrCustomerInfoKeyMobileNo(@Param("contractAccount") long contractAccount, @Param("mobileNo") String mobileNo);

}
