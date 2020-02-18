package ru.alarh.netris.service.impl;

import java.util.concurrent.CompletableFuture;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import ru.alarh.netris.configuration.properties.ExchangeProperties;
import ru.alarh.netris.dto.DataDto;
import ru.alarh.netris.dto.SourceDataDto;
import ru.alarh.netris.dto.TokenDataDto;
import ru.alarh.netris.service.ExchangeService;

import org.springframework.http.HttpHeaders;

@Log4j2
@Service
@RequiredArgsConstructor
public class ExchangeServiceImpl implements ExchangeService {
	
	private final ExchangeProperties props;
	
	private final RestTemplate restClient;
	private final HttpHeaders headers;

	@Override	
	public DataDto[] getData() {
		log.info("Получение исходных данных");
		ResponseEntity<DataDto[]> response = restClient.exchange(props.getSource(), 
				 HttpMethod.GET, 
				 new HttpEntity<>(headers), 
				 DataDto[].class);
		return response.getBody();
	}

	@Async
	@Override
	public CompletableFuture<SourceDataDto> getSourceData(String sourceUrl) {
		log.info("Получение данных по источнику = {}", sourceUrl);
		log.debug("Поток {}", Thread.currentThread().getName());
		ResponseEntity<SourceDataDto> response = restClient.exchange(sourceUrl, 
				 HttpMethod.GET, 
				 new HttpEntity<>(headers), 
				 SourceDataDto.class);
		return CompletableFuture.completedFuture(response.getBody());
	}

	@Async
	@Override
	public CompletableFuture<TokenDataDto> getTokenData(String tokenUrl) {
		log.info("Получение данных по токену = {}", tokenUrl);
		log.debug("Поток {}", Thread.currentThread().getName());
		ResponseEntity<TokenDataDto> response = restClient.exchange(tokenUrl, 
				 HttpMethod.GET, 
				 new HttpEntity<>(headers), 
				 TokenDataDto.class);
		return CompletableFuture.completedFuture(response.getBody());
	}	

}
