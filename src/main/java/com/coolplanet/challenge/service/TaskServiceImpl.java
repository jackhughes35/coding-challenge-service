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

@Service
public class TaskServiceImpl implements TaskService {

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

//		Long averageDuration = recordedTaskRepository.averageDurationByTaskIdentifier(task.getTaskIdentifier());
//		
//		Task taskToUpdate = Task.builder().taskIdentifier(task.getTaskIdentifier()).averageTaskDuration(averageDuration).build();
		
//		recordedTaskRepository.
		
	}
		
	

}
