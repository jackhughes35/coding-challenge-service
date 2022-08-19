package com.coolplanet.challenge.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.coolplanet.challenge.entity.RecordedTask;
import com.coolplanet.challenge.entity.RecordedTaskDTO;
import com.coolplanet.challenge.entity.Task;
import com.coolplanet.challenge.entity.TaskDTO;
import com.coolplanet.challenge.exception.ResourceNotFoundException;
import com.coolplanet.challenge.repository.RecordedTaskRepository;
import com.coolplanet.challenge.repository.TaskRepository;

@SpringBootTest
public class TaskServiceTest {

	@Autowired
	TaskServiceImpl service;
	
	@MockBean 
	RecordedTaskRepository recordedTaskRepo;
	
	@MockBean
	TaskRepository taskRepo;
	
	@Captor
	ArgumentCaptor<RecordedTask> recordedTaskCaptor;
	
	@Captor
	ArgumentCaptor<Long> taskCaptor;
		
	
	/**
	 * Success Scenario add Recorded Test
	 */
	@Test
	void addRecordedTask_success(){
		// Initialise vars
		Long taskDuration = 500L;
		Long taskIdentifier = 1L;
		RecordedTaskDTO input = RecordedTaskDTO.builder().taskDuration(taskDuration).taskIdentifier(taskIdentifier).build();
		RecordedTask output = RecordedTask.builder().taskDuration(taskDuration).taskIdentifier(taskIdentifier).build();
		
		// Mock
		Mockito.when(recordedTaskRepo.save(Mockito.any(RecordedTask.class))).thenReturn(output);
		
		// Carry out Request
		RecordedTask response = service.addRecordedTask(input);
				
		// Assertions
		verify(recordedTaskRepo).save(recordedTaskCaptor.capture());
		RecordedTask capturedTask = recordedTaskCaptor.getValue();
		
		Assertions.assertAll(
				() -> assertNotNull(response),
				() -> assertNotNull(capturedTask),
				() -> assertEquals(input.getTaskIdentifier(), capturedTask.getTaskIdentifier())
				);
	}
	
	/**
	 * Success Scenario Get Task
	 * @throws ResourceNotFoundException 
	 */
	@Test
	void getAverageTaskDuration_success() throws ResourceNotFoundException{
		// Initialise vars
		Long taskDuration = 500L;
		Long taskIdentifier = 1L;
		TaskDTO input = TaskDTO.builder().averageTaskDuration(taskDuration).taskIdentifier(taskIdentifier).build();
		Task output = Task.builder().averageTaskDuration(taskDuration).taskId(taskIdentifier).build();
		Optional<Task> outputTask = Optional.of(output);
		
		// Mock
		Mockito.when(taskRepo.findById(Mockito.anyLong())).thenReturn(outputTask);		
		
		// Carry out Request
		TaskDTO response = service.getAverageTaskDuration(taskIdentifier);
				
		// Assertions
		verify(taskRepo).findById(taskCaptor.capture());
		Long capturedTaskId = taskCaptor.getValue();
		
		Assertions.assertAll(
				() -> assertNotNull(response),
				() -> assertNotNull(capturedTaskId),
				() -> assertEquals(input.getTaskIdentifier(), capturedTaskId),
				() -> assertEquals(response.getTaskIdentifier(), capturedTaskId)
				);
	}
	
	
	/**
	 * Failure Scenario Get Task by Id
	 * @throws ResourceNotFoundException 
	 */
	@Test
	void getAverageTaskDuration_resourceNotFoundException() throws ResourceNotFoundException{
		// Initialise vars
		Long taskDuration = 500L;
		Long taskIdentifier = 1L;
		TaskDTO input = TaskDTO.builder().averageTaskDuration(taskDuration).taskIdentifier(taskIdentifier).build();								
		
		// Mock
		Mockito.when(taskRepo.findById(Mockito.anyLong())).thenReturn(Optional.empty());		
				
		// Carry out Request
		ResourceNotFoundException exception = Assertions.assertThrows(ResourceNotFoundException.class, () -> {
			service.getAverageTaskDuration(taskIdentifier);
		});
				
		// Assertions
		verify(taskRepo).findById(taskCaptor.capture());
		Long capturedTaskId = taskCaptor.getValue();
		
		Assertions.assertAll(
				() -> assertNotNull(exception),
				() -> assertNotNull(capturedTaskId),
				() -> assertEquals(input.getTaskIdentifier(), capturedTaskId),
				() -> assertTrue(exception.getMessage().contains(capturedTaskId.toString()))
				);
	}
}
