package com.coolplanet.challenge.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.coolplanet.challenge.entity.RecordedTaskDTO;
import com.coolplanet.challenge.entity.TaskDTO;
import com.coolplanet.challenge.exception.ErrorResponse;
import com.coolplanet.challenge.exception.ResourceNotFoundException;
import com.coolplanet.challenge.service.TaskService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith(SpringExtension.class)
@WebMvcTest(value = TaskController.class)
public class TaskControllerTest {

	@Autowired 
	MockMvc mockMvc;
	
	@MockBean
	TaskService service;
	
	private ObjectMapper mapper = new ObjectMapper();
	
	private final String postUri = "/v1/coolplanet/task";
	private final String getUri = "/v1/coolplanet/task";
	
	/*
	 * POST tests
	 */
	/**
	 * Success Scenario Testing
	 * @throws Exception
	 */
	@Test
	void addTask_Success() throws Exception {
		// Initialise Vars
		ResponseEntity<Object> response = new ResponseEntity<>(HttpStatus.CREATED);
		
		// Set up Mocks
		Mockito.when(service.addTask(Mockito.any(RecordedTaskDTO.class))).thenReturn(response);
		
		// Build Request
		RecordedTaskDTO request = RecordedTaskDTO.builder().taskIdentifier(1L).taskDuration(500L).build();
		RequestBuilder requestBuilder = buildPostRequest(request, postUri);
		
		// Carry out request
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		
		// Assertions
		verify(service).addTask(Mockito.any(RecordedTaskDTO.class));
		Assertions.assertAll(
				() -> assertNotNull(result),
				() -> assertEquals(HttpStatus.CREATED.value(), result.getResponse().getStatus())
				);
		
	}

	/*
	 * GET tests
	 */
	
	/**
	 * Successful Scenario testing
	 * @throws Exception
	 */
	@Test
	void getAverageDuration_Success() throws Exception {
		// Initialise Vars
		Long duration = 500L;
		Long id = 1L;
		TaskDTO response = TaskDTO.builder().averageTaskDuration(duration).taskIdentifier(id).build();
		
		// set up mocks
		Mockito.when(service.getAverageTaskDuration(Mockito.anyLong())).thenReturn(response);
		
		// build Request 
		RequestBuilder request = buildGetRequest(id, getUri);
		
		// carry out request
		MvcResult result = mockMvc.perform(request).andReturn();
		
		// Assertions
		verify(service).getAverageTaskDuration(Mockito.anyLong());
		
		TaskDTO taskDto = mapper.readValue(result.getResponse().getContentAsString(), TaskDTO.class);
		Assertions.assertAll(
				() -> assertNotNull(result),
				() -> assertNotNull(taskDto),
				() -> assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus()),
				() -> assertEquals(id, taskDto.getTaskIdentifier()),
				() -> assertEquals(duration, taskDto.getAverageTaskDuration())
				);
	}

	/**
	 * Failure scenario testing for expected Exception which is handled by ExceptionHandler
	 * @throws Exception
	 */
	@Test
	void getAverageDuration_Exception() throws Exception {
		// Initialise Vars
		String errorMessage = "errorMessage";
		Long id = 1L;
		ResourceNotFoundException exception = new ResourceNotFoundException(errorMessage);
		
		// set up mocks
		Mockito.when(service.getAverageTaskDuration(Mockito.anyLong())).thenThrow(exception);
		
		// build Request 
		RequestBuilder request = buildGetRequest(id, getUri);
		
		// carry out request
		MvcResult result = mockMvc.perform(request).andReturn();
		
		// Assertions
		verify(service).getAverageTaskDuration(Mockito.anyLong());
		
		ErrorResponse errorResponse = mapper.readValue(result.getResponse().getContentAsString(), ErrorResponse.class);		
		Assertions.assertAll(
				() -> assertNotNull(result),				
				() -> assertEquals(HttpStatus.NOT_FOUND.value(), result.getResponse().getStatus()),
				() -> assertNotNull(errorResponse.getTimeStamp()),
				() -> assertEquals(errorMessage, errorResponse.getMessage())				
				);		
	}
	
	/**
	 * Failure scenario testing for unexpected Exception which is handled by ExceptionHandler
	 * @throws Exception
	 */
	@Test
	void getAverageDuration_unexpectedException() throws Exception {
		// Initialise Vars
		String errorMessage = "errorMessage";
		Long id = 1L;
		NullPointerException exception = new NullPointerException(errorMessage);
		
		// set up mocks
		Mockito.when(service.getAverageTaskDuration(Mockito.anyLong())).thenThrow(exception);
		
		// build Request 
		RequestBuilder request = buildGetRequest(id, getUri);
		
		// carry out request
		MvcResult result = mockMvc.perform(request).andReturn();
		
		// Assertions
		verify(service).getAverageTaskDuration(Mockito.anyLong());
		
		ErrorResponse errorResponse = mapper.readValue(result.getResponse().getContentAsString(), ErrorResponse.class);		
		Assertions.assertAll(
				() -> assertNotNull(result),				
				() -> assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), result.getResponse().getStatus()),
				() -> assertNotNull(errorResponse.getTimeStamp()),
				() -> assertEquals(errorMessage, errorResponse.getMessage())				
				);		
	}
	
	/*
	 * Helper Methods
	 */
	public <T> String createJson(T request) {		
		String requestJson = "";
		
			try {
				requestJson = mapper.writeValueAsString(request);
			} catch (JsonProcessingException e) {
				e.printStackTrace();
				requestJson = null;
			}
			return requestJson;
	}
	
	public <T> RequestBuilder buildPostRequest(T request, String uri) {							
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post(uri)
				.accept(MediaType.APPLICATION_JSON)	            
	            .contentType(MediaType.APPLICATION_JSON)
	            .content(createJson(request))
	            .characterEncoding("utf-8");
		return requestBuilder;
	}
	
	public RequestBuilder buildGetRequest(Long id, String uri) {
		StringBuilder sb = new StringBuilder(uri);
		sb.append("/");
		sb.append(id);
		String updatedUri = sb.toString();
		
		RequestBuilder rb = MockMvcRequestBuilders.get(updatedUri)
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.characterEncoding("utf-8");
		return rb;
	}
}
