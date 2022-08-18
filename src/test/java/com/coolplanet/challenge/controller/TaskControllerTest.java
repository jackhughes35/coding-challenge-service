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
	
	/*
	 * POST tests
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
}
