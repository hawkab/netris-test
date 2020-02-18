package ru.alarh.netris.service.impl;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import ru.alarh.netris.domain.AggregateData;
import ru.alarh.netris.dto.DataDto;
import ru.alarh.netris.dto.SourceDataDto;
import ru.alarh.netris.dto.TokenDataDto;
import ru.alarh.netris.mapper.Mapper;
import ru.alarh.netris.service.AggregateService;
import ru.alarh.netris.service.ExchangeService;

@Log4j2
@Service
@RequiredArgsConstructor
public class AggregateServiceImpl implements AggregateService {

	private final ExchangeService exchangeService;
	private final Mapper mapper;

	@Override
	public Collection<AggregateData> aggregate() {
		log.info("Начато получение и обработка данных");
		final long start = System.currentTimeMillis();

		Collection<AggregateData> collection = Collections.synchronizedCollection(new HashSet<AggregateData>());
		Arrays.asList(exchangeService.getData()).parallelStream().forEach(x -> collection.add(mapping(x)));

		log.info("На получение и агрегацию данных затрачено: {} мс", System.currentTimeMillis() - start);
		return collection;
	}

	private AggregateData mapping(DataDto data) {
		if (Objects.isNull(data))
			throw new IllegalArgumentException();
		
		log.info("Получение данных по камере id = {}", data.getId());		
		CompletableFuture<SourceDataDto> source = exchangeService.getSourceData(data.getSourceDataUrl());
		CompletableFuture<TokenDataDto> token = exchangeService.getTokenData(data.getTokenDataUrl());
		CompletableFuture.allOf(source, token).join();
		
		try {
			log.info("Агрегация данных по камере id = {}", data.getId());
			return mapper.toAggregateData(data, source.get(), token.get());
		} catch (InterruptedException | ExecutionException e) {
			log.error("Ошибка при обработке камеры id = {}: {}", data.getId(), e.getMessage());
			return null;
		}
	}
	
}
