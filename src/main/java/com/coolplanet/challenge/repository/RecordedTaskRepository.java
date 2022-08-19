package com.coolplanet.challenge.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.coolplanet.challenge.entity.RecordedTask;

public interface TaskRepository extends JpaRepository<RecordedTask, Long>{

}
