package ru.alarh.netris.controller;

import java.util.concurrent.ExecutionException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import ru.alarh.netris.service.AggregateService;

@RequiredArgsConstructor
@RestController
public class AggregateController {
	
	private final AggregateService aggregateService;
	
	@GetMapping(path = "/exchange")
	public ResponseEntity<?> exchange() {
		try {
			return ResponseEntity.ok(aggregateService.aggregate());
		} catch (InterruptedException | ExecutionException e) {
			return ResponseEntity.unprocessableEntity().build();
		}
	}

}
