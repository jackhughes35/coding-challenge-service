package com.coolplanet.challenge.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.coolplanet.challenge.entity.Task;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long>{

}