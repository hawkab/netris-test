package ru.alarh.netris.service;

import java.util.concurrent.CompletableFuture;

import ru.alarh.netris.dto.DataDto;
import ru.alarh.netris.dto.SourceDataDto;
import ru.alarh.netris.dto.TokenDataDto;

public interface ExchangeService {
	
	DataDto[] getData();
	
	CompletableFuture<SourceDataDto> getSourceData(String sourceUrl);
	
	CompletableFuture<TokenDataDto> getTokenData(String tokenUrl);
	
}
