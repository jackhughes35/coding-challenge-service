package com.coolplanet.challenge.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "taskRecords")
public class RecordedTask {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
//	@Column(name = "taskRecordIdentifier")
	private Long taskRecordIdentifier;	
	
	@Column(name = "taskIdentifier")
	private Long taskIdentifier;	
	
	@Column(name = "taskDuration")
	private Long taskDuration;
	
	public RecordedTaskDTO toRecordedTaskDTO() {
		return RecordedTaskDTO.builder()
				.taskDuration(this.taskDuration)
				.taskIdentifier(this.taskIdentifier)
				.build();
	}
}
