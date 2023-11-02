package com.oolaa.TiresCRM.tire;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.oolaa.TiresCRM.tire.task.Task;
import com.oolaa.TiresCRM.tire.task.TaskType;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name="TIRE")
public class Tire {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="ID")
	private Long id;
	@Column(name="TIRE_ID")
    private String tireId;
	@Column(name="TIRE_ADDED")
	private OffsetDateTime tireAdded;
	@Column(name="LOT_NUMBER")
	private String lotNumber;
    @OneToMany(mappedBy = "tire", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Task> tasks = new ArrayList<>();
	@Column(name="IS_TIRE_SCRAPPED")
	private Boolean isTireScrapped;
	////..../////
	@Column(name="IS_TIRE_READY")
	private Boolean isTireReady;
	
	protected Tire() {}

	public Tire(String tireId, String lotNumber, Boolean isTireReady, Boolean isTireScrapped) {
	    this.tireId = tireId;
	    this.lotNumber = lotNumber;
	    this.isTireReady = isTireReady;
	    this.isTireScrapped = isTireScrapped;
	    this.tasks = new ArrayList<>();
	    OffsetDateTime truncatedDateTime = OffsetDateTime.now().truncatedTo(ChronoUnit.SECONDS);
	    this.tireAdded = truncatedDateTime;
	    
	    // Initialize tasks based on taskType enum
	    for (TaskType type : TaskType.values()) {
	        Task newTask = new Task();
	        
	        newTask.setTaskType(type);
	        newTask.setIsDone(false);
	        newTask.setWhoDidIt("unknown");
	        newTask.setWhenWasItDone(OffsetDateTime.now());
	        
	        newTask.setTire(this);
	        this.tasks.add(newTask);
	    }
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLotNumber() {
		return lotNumber;
	}

	public void setLotNumber(String lotNumber) {
		this.lotNumber = lotNumber;
	}

	public List<Task> getTasks() {
		return tasks;
	}

	public void setTasks(List<Task> tasks) {
		this.tasks = tasks;
	}

	public String getTireId() {
		return tireId;
	}

	public void setTireId(String tireId) {
		this.tireId = tireId;
	}

	public Boolean getIsTireScrapped() {
		return isTireScrapped;
	}

	public void setIsTireScrapped(Boolean isTireScrapped) {
		this.isTireScrapped = isTireScrapped;
	}

	public Boolean getIsTireReady() {
		return isTireReady;
	}

	public void setIsTireReady(Boolean isTireReady) {
		this.isTireReady = isTireReady;
	}
	
	public void addTask(Task task) {
	    tasks.add(task);
	    task.setTire(this);
	}

	public void removeTask(Task task) {
	    tasks.remove(task);
	    task.setTire(null);
	}

	public OffsetDateTime getTireAdded() {
		return tireAdded;
	}

	public void setTireAdded(OffsetDateTime tireAdded) {
		this.tireAdded = tireAdded;
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
		Tire other = (Tire) obj;
		return Objects.equals(id, other.id);
	}
	
	
}
