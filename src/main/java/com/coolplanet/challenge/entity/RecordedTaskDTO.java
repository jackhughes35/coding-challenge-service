package com.coolplanet.challenge.entity;


import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RecordedTaskDTO {

	@NotNull(message = "Must provide a task duration")
	private Long taskDuration;
	@NotNull(message = "Must provide a parent Task ID")
	private Long taskIdentifier;
	
	public RecordedTask toRecordedTask() {
		return RecordedTask.builder()
				.taskDuration(this.taskDuration)
				.taskIdentifier(this.taskIdentifier)
				.build();
	}

}
