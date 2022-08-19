package com.coolplanet.challenge.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.coolplanet.challenge.entity.Task;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long>{

	@Query(value = "SELECT avg(task_duration) FROM task_records where task_identifier = :id", nativeQuery = true)
	public Long averageDurationByTaskIdentifier(@Param("average") Long average, @Param("id") Long id);
}