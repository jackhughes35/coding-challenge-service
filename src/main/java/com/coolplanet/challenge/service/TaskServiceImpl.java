package com.coolplanet.challenge.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
public class TaskServiceImpl implements TaskService {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired	
	RecordedTaskRepository recordedTaskRepository;
	
	@Autowired
	TaskRepository taskRepository;
	
	@Override
	public RecordedTask addRecordedTask(RecordedTaskDTO task) {
		RecordedTask recordedTask = task.toRecordedTask();
		RecordedTask addedTask = recordedTaskRepository.save(recordedTask);
		calculateAverage(task);
		return addedTask;
	}

	@Override
	public TaskDTO getAverageTaskDuration(Long taskIdentifier) throws ResourceNotFoundException {
		Task retrievedTask = taskRepository.findById(taskIdentifier)
				.orElseThrow(() -> new ResourceNotFoundException("No Task with identifier "+ taskIdentifier));
		return retrievedTask.toTaskDTO();
	}
	
	
	@Override
	public Task addTask(TaskDTO taskDto) {
		Task toAdd = taskDto.toTask();
		Task addedTask = taskRepository.save(toAdd);
		return addedTask;
	}
	
	@Override
	@Async
	public void calculateAverage(RecordedTaskDTO task) {

		// Calculate the average for a given Task ID
		Long averageDuration = recordedTaskRepository.averageDurationByTaskIdentifier(task.getTaskIdentifier());
		logger.info("Average Duration for Task Identifier {} :: {}", task.getTaskIdentifier(), averageDuration);
		
		// Update the entity
		Task taskToUpdate = Task.builder().taskId(task.getTaskIdentifier()).averageTaskDuration(averageDuration).build();		
		Task updatedTask = taskRepository.save(taskToUpdate);
		if(null != updatedTask) {
			logger.info("Updated Task ID :: {} with Average Duration :: {}", task.getTaskIdentifier(), averageDuration);
		} else {
			logger.info("Rersource Not Found for Task ID :: {} when updating", task.getTaskIdentifier());
		}
				
	}
		
	

}
