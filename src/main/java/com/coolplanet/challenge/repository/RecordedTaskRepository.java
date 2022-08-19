package com.coolplanet.challenge.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.coolplanet.challenge.entity.RecordedTask;

@Repository
public interface RecordedTaskRepository extends JpaRepository<RecordedTask, Long>{

//	@Query("SELECT avg(duration) FROM RecordedTasks WHERE taskID = ")
//	Long averageDurationByTaskIdentifier(Long id);
}
