package com.coolplanet.challenge.controller;

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

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/v1/coolplanet")
@Slf4j
public class TaskController {
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
		log.info("Added Task :: {}", serviceResponse.toString());
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
		log.info("Added Task :: {}", serviceResponse.toString());
		ResponseEntity<Object> response = new ResponseEntity<>(HttpStatus.CREATED);
		return response;
	}
	
}
