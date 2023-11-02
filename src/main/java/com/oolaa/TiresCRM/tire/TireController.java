package com.oolaa.TiresCRM.tire;

import java.net.URI;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.oolaa.TiresCRM.tire.task.Task;
import com.oolaa.TiresCRM.tire.task.TaskType;

@CrossOrigin(origins="http://localhost:4200")
@RestController
public class TireController {

	@Autowired
	private TireRepository tireRepository;
	@Autowired
	private TireService tireService;
	
	@GetMapping("/tires")
	public List<Tire> getAllTires() {
		return tireRepository.findAll();
	}
	
	@GetMapping("/tires/new-tire")
	public Tire createTire() {
		return tireService.createTire("", false, false, false, false);
	}
	
    @GetMapping("/tires/{date}")
    public List<Tire> createTire(@PathVariable String date) {
        // Parse the date string to LocalDate
        LocalDate localDate = LocalDate.parse(date);

        // Convert LocalDate to OffsetDateTime at start and end of day
        OffsetDateTime startDateTime = localDate.atStartOfDay().toInstant(ZoneOffset.UTC).atOffset(ZoneOffset.UTC);
        OffsetDateTime endDateTime = localDate.plusDays(1).atStartOfDay().toInstant(ZoneOffset.UTC).atOffset(ZoneOffset.UTC);

        // Fetch tires between startDateTime and endDateTime
        System.out.print(tireRepository.findByTireAddedBetween(startDateTime, endDateTime).size());
        return tireRepository.findByTireAddedBetween(startDateTime, endDateTime);
    }
	
	@PostMapping("/tires/{id}")
	public ResponseEntity<Tire> updateTire (@PathVariable Long id, @RequestBody Tire newTireData) {
		Tire existingTire = tireRepository.findById(id).orElse(null);
		
		if (existingTire == null) {
            return ResponseEntity.notFound().build();
        }
		
	    List<Task> newTasks = newTireData.getTasks();
	    List<Task> existingTasks = existingTire.getTasks();
		
	    boolean allTasksDone = true;

	    for (TaskType type : TaskType.values()) {
	        Optional<Task> existingTaskOpt = existingTasks.stream().filter(t -> t.getTaskType() == type).findFirst();
	        Optional<Task> newTaskOpt = newTasks.stream().filter(t -> t.getTaskType() == type).findFirst();
	        
	        if (existingTaskOpt.isPresent() && newTaskOpt.isPresent()) {
	            Task existingTask = existingTaskOpt.get();
	            Task newTask = newTaskOpt.get();

	            existingTask.setIsDone(newTask.getIsDone());
	            if (existingTask.getIsDone()) {
	            existingTask.setWhoDidIt(newTask.getWhoDidIt());
	            OffsetDateTime truncatedDateTime = newTask.getWhenWasItDone().truncatedTo(ChronoUnit.SECONDS);
	            existingTask.setWhenWasItDone(truncatedDateTime);
	            } else {
		            existingTask.setWhoDidIt("no one");
		            existingTask.setWhenWasItDone(OffsetDateTime.of(2000, 1, 1, 0, 0, 0, 0, ZoneOffset.UTC));
	            }
	            if (!newTask.getIsDone()) {
	                allTasksDone = false;
	            }
	        } else {
	            System.out.println("fatal error, task is not found");
	        }
	    }

	    existingTire.setIsTireReady(allTasksDone);
	    existingTire.setLotNumber(newTireData.getLotNumber());
	    existingTire.setIsTireScrapped(newTireData.getIsTireScrapped());
		
        Tire updatedTire = tireRepository.save(existingTire);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(updatedTire.getId()).toUri();

        return ResponseEntity.created(uri).body(updatedTire);
	}
}
