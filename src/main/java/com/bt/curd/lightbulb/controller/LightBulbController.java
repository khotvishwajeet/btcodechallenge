package com.bt.curd.lightbulb.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import com.bt.curd.lightbulb.model.LightBulb;
import com.bt.curd.lightbulb.repository.LightBulbRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bt.curd.lightbulb.exception.ResourceNotFoundException;
import com.bt.curd.lightbulb.service.SequenceGeneratorService;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/v1")
public class LightBulbController {
	@Autowired
	private LightBulbRepository lightBulbRepository;
	
	@Autowired
	private SequenceGeneratorService sequenceGeneratorService;

	@GetMapping("/lightBulbs")
	public List<LightBulb> getAllLightBulbs() {
		return lightBulbRepository.findAll();
	}

	@GetMapping("/lightBulbs/{id}")
	public ResponseEntity<LightBulb> getLightBulbById(@PathVariable(value = "id") Long lightBulbId)
			throws ResourceNotFoundException {
		LightBulb lightBulb = lightBulbRepository.findById(lightBulbId)
				.orElseThrow(() -> new ResourceNotFoundException("LightBulb not found for this id :: " + lightBulbId));
		return ResponseEntity.ok().body(lightBulb);
	}

	@PostMapping("/lightBulbs")
	public LightBulb createLightBulb(@Valid @RequestBody LightBulb lightBulb) {
		lightBulb.setId(sequenceGeneratorService.generateSequence(LightBulb.SEQUENCE_NAME));
		return lightBulbRepository.save(lightBulb);
	}

	@PutMapping("/lightBulbs/{id}")
	public ResponseEntity<LightBulb> updateLightBulb(@PathVariable(value = "id") Long lightBulbId,
			@Valid @RequestBody LightBulb lightBulbDetails) throws ResourceNotFoundException {
		LightBulb lightBulb = lightBulbRepository.findById(lightBulbId)
				.orElseThrow(() -> new ResourceNotFoundException("LightBulb not found for this id :: " + lightBulbId));

		lightBulb.setId(lightBulbDetails.getId());
		lightBulb.setState(lightBulbDetails.getState());
		lightBulb.setColor(lightBulbDetails.getColor());
		final LightBulb updatedLightBulb = lightBulbRepository.save(lightBulb);
		return ResponseEntity.ok(updatedLightBulb);
	}

	@DeleteMapping("/lightBulbs/{id}")
	public Map<String, Boolean> deleteLightBulb(@PathVariable(value = "id") Long lightBulbId)
			throws ResourceNotFoundException {
		LightBulb lightBulb = lightBulbRepository.findById(lightBulbId)
				.orElseThrow(() -> new ResourceNotFoundException("LightBulb not found for this id :: " + lightBulbId));

		lightBulbRepository.delete(lightBulb);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return response;
	}
}
