package com.sap.ubot.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.sap.ubot.entity.MeterReadingDetails;

@Transactional
public interface BillingDetailsRepository extends CrudRepository<MeterReadingDetails, Long> {
	
	@Query(value = "SELECT * FROM METER_READING_DETAILS WHERE MR_DATE LIKE %:mrDate% AND DEVICE = :device", nativeQuery=true)
	public MeterReadingDetails findByMrDate(@Param("mrDate") String mrDate, @Param("device") String device);
	
	@Query(value = "SELECT * FROM METER_READING_DETAILS WHERE MR_DATE BETWEEN :startDate AND :endDate AND DEVICE = :device", nativeQuery=true)
	public List<MeterReadingDetails> findByMrDateRange(@Param("startDate") String startDate,@Param("endDate") String endDate, @Param("device") String device);
	
	
}
