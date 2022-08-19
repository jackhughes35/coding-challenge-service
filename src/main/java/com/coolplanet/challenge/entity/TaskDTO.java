package com.coolplanet.challenge.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

//@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TaskDTO {

	private Long averageTaskDuration;
	private Long taskIdentifier;
	
	public Task toTask() {
		return Task.builder()
				.averageTaskDuration(this.averageTaskDuration)
				.taskId(this.taskIdentifier)
				.build();
	}
}
