package com.oolaa.TiresCRM.tire.task;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.oolaa.TiresCRM.tire.Tire;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="TASK")
public class Task {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="ID")
	private Long id;

	@Column(name="IS_DONE")
	private Boolean isDone;

	@Column(name="WHO_DID_IT")
	private String whoDidIt;

	@Column(name="WHEN_WAS_IT_DONE")
	private OffsetDateTime whenWasItDone;

	@Enumerated(EnumType.STRING)
	@Column(name="TASK_TYPE")
	private TaskType taskType;

	@ManyToOne
	@JoinColumn(name = "TIRE_ID")
	@JsonBackReference
	private Tire tire;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Boolean getIsDone() {
		return isDone;
	}

	public void setIsDone(Boolean isDone) {
		this.isDone = isDone;
	}

	public String getWhoDidIt() {
		return whoDidIt;
	}

	public void setWhoDidIt(String whoDidIt) {
		this.whoDidIt = whoDidIt;
	}

	public OffsetDateTime getWhenWasItDone() {
		return whenWasItDone;
	}

	public void setWhenWasItDone(OffsetDateTime whenWasItDone) {
		this.whenWasItDone = whenWasItDone;
	}

	public TaskType getTaskType() {
		return taskType;
	}

	public void setTaskType(TaskType taskType) {
		this.taskType = taskType;
	}

	public Tire getTire() {
		return tire;
	}

	public void setTire(Tire tire) {
		this.tire = tire;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Task other = (Task) obj;
		return Objects.equals(id, other.id);
	}

	
}
