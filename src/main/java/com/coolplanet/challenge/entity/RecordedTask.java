package com.coolplanet.challenge.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

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
@Entity
@Table(name = "taskRecords")
public class RecordedTask {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long taskRecordIdentifier;
	
	private Long taskIdentifier;	
	private Long taskDuration;
	
	public RecordedTaskDTO toRecordedTaskDTO() {
		return RecordedTaskDTO.builder()
				.taskDuration(this.taskDuration)
				.taskIdentifier(this.taskIdentifier)
				.build();
	}
}
