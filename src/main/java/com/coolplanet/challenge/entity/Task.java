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
@Table(name = "tasks")
public class Task {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)	
	private Long taskIdentifier;
	private Long averageTaskDuration;
	
	public TaskDTO toTaskDTO() {
		return TaskDTO.builder()
				.taskIdentifier(this.taskIdentifier)
				.averageTaskDuration(this.averageTaskDuration)
				.build();
	}
}
