package com.coolplanet.challenge.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.coolplanet.challenge.entity.RecordedTaskDTO;
import com.coolplanet.challenge.entity.TaskDTO;
import com.coolplanet.challenge.exception.ResourceNotFoundException;

@Service
public interface TaskService {

	ResponseEntity addTask(RecordedTaskDTO task);
	
	TaskDTO getAverageTaskDuration(Long taskIdentifier) throws ResourceNotFoundException;
	
	void calculateAverage(RecordedTaskDTO task);
}
