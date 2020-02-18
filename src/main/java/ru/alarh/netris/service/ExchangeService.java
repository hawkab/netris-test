package ru.alarh.netris.service;

import org.springframework.http.ResponseEntity;

import ru.alarh.netris.dto.DataDto;
import ru.alarh.netris.dto.SourceDataDto;
import ru.alarh.netris.dto.TokenDataDto;

public interface ExchangeService {
	
	ResponseEntity<DataDto[]> getData();
	
	ResponseEntity<SourceDataDto> getSourceData(String sourceUrl);
	
	ResponseEntity<TokenDataDto> getTokenData(String tokenUrl);

	
	
}
