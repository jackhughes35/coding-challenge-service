package com.coolplanet.challenge.service;

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

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class TaskServiceImpl implements TaskService {
	@Autowired	
	RecordedTaskRepository recordedTaskRepository;
	
	@Autowired
	TaskRepository taskRepository;
	
	@Override
	public RecordedTask addRecordedTask(RecordedTaskDTO task) {
		// Insert individual record
		RecordedTask recordedTask = task.toRecordedTask();
		RecordedTask addedTask = recordedTaskRepository.save(recordedTask);
		log.info("Added Recorded Task Type :: {} with Duration of :: {}", recordedTask.getTaskIdentifier(), recordedTask.getTaskDuration());
		// Async call to update average in table averages table
		calculateAverage(task);
		return addedTask;

	}

	@Override
	public TaskDTO getAverageTaskDuration(Long taskIdentifier) throws ResourceNotFoundException {
		Task retrievedTask = taskRepository.findById(taskIdentifier)
				.orElseThrow(() -> new ResourceNotFoundException("No Task with identifier "+ taskIdentifier));
		return retrievedTask.toTaskDTO();
	}
	
	/**
	 * Security with Role based auth. 
	 */
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
		log.info("Average Duration for Task Identifier {} :: {}", task.getTaskIdentifier(), averageDuration);
		
		// Update the entity
		Task taskToUpdate = Task.builder().taskId(task.getTaskIdentifier()).averageTaskDuration(averageDuration).build();		
		Task updatedTask = taskRepository.save(taskToUpdate);
		if(null != updatedTask) {
			log.info("Updated Task ID :: {} with Average Duration :: {}", task.getTaskIdentifier(), averageDuration);
		} else {
			log.info("Resource Not Found for Task ID :: {} when updating", task.getTaskIdentifier());
		}
				
	}
		
	

}
