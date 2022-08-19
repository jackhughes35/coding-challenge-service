package com.coolplanet.challenge.service;

import org.springframework.stereotype.Service;

import com.coolplanet.challenge.entity.RecordedTask;
import com.coolplanet.challenge.entity.RecordedTaskDTO;
import com.coolplanet.challenge.entity.Task;
import com.coolplanet.challenge.entity.TaskDTO;
import com.coolplanet.challenge.exception.ResourceNotFoundException;

@Service
public interface TaskService {

	RecordedTask addRecordedTask(RecordedTaskDTO task);
	
	Task addTask(TaskDTO taskDto);
	
	TaskDTO getAverageTaskDuration(Long taskIdentifier) throws ResourceNotFoundException;	
	
	void calculateAverage(RecordedTaskDTO task);

	

	
}
