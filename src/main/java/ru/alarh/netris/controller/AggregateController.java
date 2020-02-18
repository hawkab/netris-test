package ru.alarh.netris.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import ru.alarh.netris.service.ExchangeService;

@RequiredArgsConstructor
@RestController
public class AggregateController {
	
	private final ExchangeService ex;
	
	@GetMapping(path = "/exchange")
	public ResponseEntity<?> exchange() {
		return ex.getData();
	}

}
