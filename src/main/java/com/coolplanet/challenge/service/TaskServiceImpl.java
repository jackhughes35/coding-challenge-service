package com.coolplanet.challenge.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.coolplanet.challenge.entity.RecordedTask;
import com.coolplanet.challenge.entity.RecordedTaskDTO;
import com.coolplanet.challenge.entity.TaskDTO;
import com.coolplanet.challenge.repository.TaskRepository;

@Service
public class TaskServiceImpl implements TaskService{

	@Autowired
	@Qualifier("repositoryImpl")
	TaskRepository repository;
	
	@Override
	public ResponseEntity addTask(RecordedTaskDTO task) {
		RecordedTask addedTask = repository.save(task.toRecordedTask());
		return null;
	}

	@Override
	public TaskDTO getAverageTaskDuration(Long taskIdentifier) {
		return null;
	}

}
