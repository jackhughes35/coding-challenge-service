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
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

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
@Table(name = "tasks")
public class Task {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)	
	@Column(name = "task_identifier")
	@NotNull
	private Long taskId;
	
	@Column(name = "average_task_duration")
	private Long averageTaskDuration;
	
	@JsonIgnore
	@OneToMany
	@JoinColumn(name = "task_identifier", updatable = false)
	private Set<RecordedTask> recordedTasks;
	
	public TaskDTO toTaskDTO() {
		return TaskDTO.builder()
				.taskIdentifier(this.taskId)
				.averageTaskDuration(this.averageTaskDuration)
				.build();
	}
}
