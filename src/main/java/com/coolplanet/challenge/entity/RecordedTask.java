package com.coolplanet.challenge.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "task_records")
public class RecordedTask {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "task_record_identifier")
	private Long taskRecordIdentifier;	
	
	@Column(name = "task_identifier")
	private long taskIdentifier;
	
	@ManyToOne
	@JoinColumn(name = "task_identifier", insertable = false, updatable = false)
	private Task task;	
		
	@Column(name = "task_duration")
	private Long taskDuration;
	
	public RecordedTaskDTO toRecordedTaskDTO() {
		return RecordedTaskDTO.builder()
				.taskDuration(this.taskDuration)
				.taskIdentifier(this.task.getTaskId())
				.build();
	}
	
}
