package com.coolplanet.challenge.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.coolplanet.challenge.entity.RecordedTask;
import com.coolplanet.challenge.entity.RecordedTaskDTO;
import com.coolplanet.challenge.entity.Task;
import com.coolplanet.challenge.entity.TaskDTO;
import com.coolplanet.challenge.exception.ResourceNotFoundException;
import com.coolplanet.challenge.service.TaskService;

@RestController
@RequestMapping("/v1/coolplanet")
public class TaskController {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	TaskService service;

	/**
	 * Method to get the average duration for a given task
	 * 
	 * @return
	 * @throws ResourceNotFoundException 
	 */
	@GetMapping("/task/{taskIdentifier}")
	private ResponseEntity<TaskDTO> getAverageDuration(@PathVariable Long taskIdentifier) throws ResourceNotFoundException {
		TaskDTO task = service.getAverageTaskDuration(taskIdentifier);
		ResponseEntity<TaskDTO> response = new ResponseEntity<>(task, HttpStatus.OK);

		return response;
	}

	/**
	 * Method to add a new task with a duration
	 * 
	 * @return Acknowledgement or failure
	 */
	@PostMapping("/recordedTask")
	private ResponseEntity<Object> addRecordedTask(@RequestBody RecordedTaskDTO task) {

		RecordedTask serviceResponse = service.addRecordedTask(task);
		logger.info("Added Task :: {}", serviceResponse.toString());
		ResponseEntity<Object> response = new ResponseEntity<>(HttpStatus.CREATED);
		return response;
	}
	
	/**
	 * Method to add a new parent task Type 
	 * TODO: Add Role-Based Authentication
	 * @return Acknowledgement or failure
	 */
	@PostMapping("/task")
	private ResponseEntity<Object> addTask(@RequestBody TaskDTO task) {

		Task serviceResponse = service.addTask(task);
		logger.info("Added Task :: {}", serviceResponse.toString());
		ResponseEntity<Object> response = new ResponseEntity<>(HttpStatus.CREATED);
		return response;
	}
	
}
