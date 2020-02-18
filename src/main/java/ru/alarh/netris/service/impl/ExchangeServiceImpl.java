package ru.alarh.netris.service.impl;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import lombok.RequiredArgsConstructor;
import ru.alarh.netris.configuration.properties.ExchangeProperties;
import ru.alarh.netris.dto.DataDto;
import ru.alarh.netris.dto.SourceDataDto;
import ru.alarh.netris.dto.TokenDataDto;
import ru.alarh.netris.service.ExchangeService;

import org.springframework.http.HttpHeaders;

@Service
@RequiredArgsConstructor
public class ExchangeServiceImpl implements ExchangeService {
	
	private final ExchangeProperties props;
	
	private final RestTemplate restClient;
	private final HttpHeaders headers;

	@Override
	public ResponseEntity<DataDto[]> getData() {
		ResponseEntity<DataDto[]> response = restClient.exchange(props.getSource(), 
				 HttpMethod.GET, 
				 new HttpEntity<>(headers), 
				 DataDto[].class);
		return response;
	}

	@Override
	public ResponseEntity<SourceDataDto> getSourceData(String sourceUrl) {
		ResponseEntity<SourceDataDto> response = restClient.exchange(sourceUrl, 
				 HttpMethod.GET, 
				 new HttpEntity<>(headers), 
				 SourceDataDto.class);
		return response;
	}

	@Override
	public ResponseEntity<TokenDataDto> getTokenData(String tokenUrl) {
		ResponseEntity<TokenDataDto> response = restClient.exchange(tokenUrl, 
				 HttpMethod.GET, 
				 new HttpEntity<>(headers), 
				 TokenDataDto.class);
		return response;
	}	

}
