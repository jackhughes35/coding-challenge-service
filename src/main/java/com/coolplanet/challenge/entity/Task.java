package com.coolplanet.challenge.entity;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tasks")
public class Task {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)	
	@Column(name = "task_identifier")
	private Long taskId;
	
	@Column(name = "average_task_duration")
	private Long averageTaskDuration;
	
	@JsonIgnore
	@OneToMany
	@JoinColumn(name = "task_identifier")
	private Set<RecordedTask> recordedTasks;
	
	public TaskDTO toTaskDTO() {
		return TaskDTO.builder()
				.taskIdentifier(this.taskId)
				.averageTaskDuration(this.averageTaskDuration)
				.build();
	}
}
