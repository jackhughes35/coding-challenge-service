package com.coolplanet.challenge.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.coolplanet.challenge.entity.RecordedTask;
import com.coolplanet.challenge.entity.RecordedTaskDTO;
import com.coolplanet.challenge.entity.Task;
import com.coolplanet.challenge.entity.TaskDTO;
import com.coolplanet.challenge.exception.ResourceNotFoundException;
import com.coolplanet.challenge.repository.RecordedTaskRepository;
import com.coolplanet.challenge.repository.TaskRepository;

@Service
public class TaskServiceImpl implements TaskService{

	@Autowired
//	@Qualifier("recordedTaskRepositoryImpl")
	RecordedTaskRepository recordedTaskRepository;
	
	@Autowired
	@Qualifier("taskRepositoryImpl")
	TaskRepository taskRepository;
	
	@Override
	public ResponseEntity addTask(RecordedTaskDTO task) {
		RecordedTask recordedTask = task.toRecordedTask();
		RecordedTask addedTask = recordedTaskRepository.save(recordedTask);
		return new ResponseEntity(addedTask, HttpStatus.OK);
	}

	@Override
	public TaskDTO getAverageTaskDuration(Long taskIdentifier) throws ResourceNotFoundException {
		Task retrievedTask = taskRepository.findById(taskIdentifier)
				.orElseThrow(() -> new ResourceNotFoundException("No Task with identifier "+ taskIdentifier));
		return retrievedTask.toTaskDTO();

	}
	
	@Override
	@Async
	public void calculateAverage(RecordedTaskDTO task) {
		RecordedTask recordedTask = task.toRecordedTask();
		
	}
		

}
