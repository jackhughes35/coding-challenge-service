package com.coolplanet.challenge.entity;

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

	private Long taskDuration;
	private Long taskIdentifier;
	
	public RecordedTask toRecordedTask() {
		return RecordedTask.builder()
				.taskDuration(this.taskDuration)
				.task(Task.builder().taskId(this.taskIdentifier).build())
				.build();
	}
}
