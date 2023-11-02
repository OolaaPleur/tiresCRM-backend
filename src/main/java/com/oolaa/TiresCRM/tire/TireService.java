package com.oolaa.TiresCRM.tire;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TireService {
	@Autowired
    private TireRepository tireRepository;
	
	public Tire createTire(String lotNumber, Boolean isTireBlown, Boolean isTireChecked, Boolean isTireReady, Boolean isTireScrapped) {
		String uniqueTireId = generateUniqueTireId();
		Tire newTire = new Tire(uniqueTireId, lotNumber, isTireReady, isTireScrapped);
		return tireRepository.save(newTire);
	}
	
    public String generateUniqueTireId() {
        Random random = new Random();
        String id;
        do {
            // ... (existing random ID generation logic)
            StringBuilder letterPart = new StringBuilder(4);
            for (int i = 0; i < 4; i++) {
                letterPart.append((char) ('A' + random.nextInt(26)));
            }
            int digitPart = 1000 + random.nextInt(9000);
            id = letterPart + "-" + digitPart;
        } while (tireRepository.findByTireId(id).isPresent());
        return id;
    }
}
