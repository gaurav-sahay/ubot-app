package com.sap.ubot.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.sap.ubot.entity.TechnicianAssignmentPoolEntity;

@Transactional
public interface TechnicianAssignmentRepository extends CrudRepository<TechnicianAssignmentPoolEntity, Long> {
	
	
	public List<TechnicianAssignmentPoolEntity> findByNextAvailableTimeOrderByNextAvailableTimeDesc(@Param("scheduleTime") String scheduleTime);
	
	public List<TechnicianAssignmentPoolEntity> findByTechIdOrderByNextAvailableTimeDesc(@Param("techId") long techId);
	
	public List<TechnicianAssignmentPoolEntity> findByTechIdAndNextAvailableTimeOrderByNextAvailableTimeDesc(@Param("techId") long techId,@Param("scheduleTime") String scheduleTime );

}
