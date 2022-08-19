package com.coolplanet.challenge.entity;

import javax.persistence.CascadeType;
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
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "task_records")
public class RecordedTask {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "task_record_identifier")
	private Long taskRecordIdentifier;	
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "task_identifier", insertable = false, updatable = false)
	private Task task;	
	
	@Column(name = "task_identifier")
	private Long taskIdentifier;
	
	@Column(name = "task_duration")
	private Long taskDuration;
	
	public RecordedTaskDTO toRecordedTaskDTO() {
		return RecordedTaskDTO.builder()
				.taskDuration(this.taskDuration)
				.taskIdentifier(this.task.getTaskId())
				.build();
	}
	
}
