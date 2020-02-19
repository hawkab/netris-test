package ru.alarh.netris;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.when;

import java.util.Collection;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.hamcrest.collection.IsEmptyCollection;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import ru.alarh.netris.domain.AggregateData;
import ru.alarh.netris.dto.DataDto;
import ru.alarh.netris.dto.SourceDataDto;
import ru.alarh.netris.dto.TokenDataDto;
import ru.alarh.netris.service.AggregateService;
import ru.alarh.netris.service.ExchangeService;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.collection.IsIterableContainingInOrder.contains;
import static org.hamcrest.MatcherAssert.assertThat;

@SpringBootTest
public class AggregateServiceTest {
	
	@Autowired
	private AggregateService aggregateService;

	@MockBean
	private ExchangeService exchangeService;
	
	/**
	 * Проверка правильности агрегации данных
	 * 
	 * @throws InterruptedException
	 * @throws ExecutionException
	 */
	@Test
	public void testAggregate() throws InterruptedException, ExecutionException {
		final DataDto dataDto = new DataDto();
		dataDto.setId(1L);
		dataDto.setSourceDataUrl("http://test-source.io");
		dataDto.setTokenDataUrl("http://test-token.io");
		
		DataDto[] dataDtoArray = new DataDto[1];
		dataDtoArray[0] = dataDto;
		
		final SourceDataDto sourceDataDto = new SourceDataDto();
		sourceDataDto.setUrlType("LIVE");
		sourceDataDto.setVideoUrl("rtsp://127.0.0.1/20");
		
		final TokenDataDto tokenDataDto = new TokenDataDto();
		tokenDataDto.setValue("fa4b5f64-249b-11e9-ab14-d663bd873d93");
		tokenDataDto.setTtl(120);
		
		when(exchangeService.getData()).thenReturn(dataDtoArray);
		when(exchangeService.getSourceData(dataDto.getSourceDataUrl())).thenReturn(CompletableFuture.completedFuture(sourceDataDto));
		when(exchangeService.getTokenData(dataDto.getTokenDataUrl())).thenReturn(CompletableFuture.completedFuture(tokenDataDto));
		
		Collection<AggregateData> result = aggregateService.aggregate();	
		
		assertThat(result, not(IsEmptyCollection.empty()));
		assertThat(result, hasSize(1));
		assertThat(result, contains(hasProperty("id", is(dataDto.getId()))));
		assertThat(result, contains(hasProperty("urlType", is(sourceDataDto.getUrlType()))));
		assertThat(result, contains(hasProperty("value", is(tokenDataDto.getValue()))));
		assertThat(result, contains(hasProperty("ttl", is(tokenDataDto.getTtl()))));
		assertThat(result, contains(hasProperty("videoUrl", is(sourceDataDto.getVideoUrl()))));		
	}	

}
