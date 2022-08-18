package com.coolplanet.challenge.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.coolplanet.challenge.entity.RecordedTaskDTO;
import com.coolplanet.challenge.entity.TaskDTO;

@Service
public class TaskServiceImpl implements TaskService{

	@Override
	public ResponseEntity addTask(RecordedTaskDTO task) {
		return null;
	}

	@Override
	public TaskDTO getAverageTaskDuration(Long taskIdentifier) {
		return null;
	}

}
