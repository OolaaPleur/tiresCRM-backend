package com.oolaa.TiresCRM.tire;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TireRepository extends JpaRepository<Tire, Long>{
	//List<Todo> findByUsername(Boolean isTireReady);
	Optional<Tire> findByTireId(String tireId);
    List<Tire> findByTireAddedBetween(OffsetDateTime start, OffsetDateTime end);
}