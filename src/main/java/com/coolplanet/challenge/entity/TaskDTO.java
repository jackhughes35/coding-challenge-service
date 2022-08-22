package com.coolplanet.challenge.entity;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TaskDTO {

	@NotBlank(message = "Task Duration Required")
	private Long averageTaskDuration;
	@NotBlank(message = "Task Identifier Required")
	private Long taskIdentifier;
	
	public Task toTask() {
		return Task.builder()
				.averageTaskDuration(this.averageTaskDuration)
				.taskId(this.taskIdentifier)
				.build();
	}
}
