package com.coolplanet.challenge.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.coolplanet.challenge.entity.Task;

public interface TaskRepository extends JpaRepository<Task, Long>{

}