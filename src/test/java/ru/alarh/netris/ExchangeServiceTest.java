package ru.alarh.netris;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import ru.alarh.netris.configuration.properties.ExchangeProperties;
import ru.alarh.netris.dto.DataDto;
import ru.alarh.netris.dto.SourceDataDto;
import ru.alarh.netris.dto.TokenDataDto;
import ru.alarh.netris.service.ExchangeService;

import static org.hamcrest.MatcherAssert.assertThat;

@SpringBootTest
public class ExchangeServiceTest {
	
	@Autowired
	private ExchangeService exchangeService;
	
	@MockBean
	private RestTemplate restTemplate;
	
	@MockBean
	private ExchangeProperties props;

	@MockBean
	private HttpHeaders headers;
	
	/** 
	 * Проверка метода получения начальных данных
	 */
	@Test
	public void testGetData() {
		final DataDto dataDto = new DataDto();
		dataDto.setId(1L);
		dataDto.setSourceDataUrl("http://test-source.io");
		dataDto.setTokenDataUrl("http://test-token.io");
		
		DataDto[] dataDtoArray = new DataDto[1];
		dataDtoArray[0] = dataDto;
		
		when(restTemplate.exchange(props.getSource(), 
				HttpMethod.GET,
				new HttpEntity<>(headers), 
				DataDto[].class)).thenReturn(ResponseEntity.ok(dataDtoArray));
	
		DataDto[] result = exchangeService.getData();

		assertThat(result[0].getId(), is(dataDto.getId()));
		assertThat(result[0].getSourceDataUrl(), is(dataDto.getSourceDataUrl()));
		assertThat(result[0].getTokenDataUrl(), is(dataDto.getTokenDataUrl()));
	}
	
	/**
	 * Проверка метода получения данных по источнику 
	 * 
	 * @throws InterruptedException
	 * @throws ExecutionException
	 */
	@Test
	public void testGetSourceData() throws InterruptedException, ExecutionException {
		final SourceDataDto sourceDataDto = new SourceDataDto();
		sourceDataDto.setUrlType("LIVE");
		sourceDataDto.setVideoUrl("rtsp://127.0.0.1/20");
		
		String sourceUrl = "http://test-source.io";
		
		when(restTemplate.exchange(sourceUrl, 
				HttpMethod.GET,
				new HttpEntity<>(headers), 
				SourceDataDto.class)).thenReturn(ResponseEntity.ok(sourceDataDto));
	
		CompletableFuture<SourceDataDto> result = exchangeService.getSourceData(sourceUrl);

		assertNotNull(result.get());
		assertThat(result.get().getUrlType(), is(sourceDataDto.getUrlType()));
		assertThat(result.get().getVideoUrl(), is(sourceDataDto.getVideoUrl()));
	}
	
	/**
	 * Проверка метода получения данных по токену
	 * 
	 * @throws InterruptedException
	 * @throws ExecutionException
	 */
	@Test
	public void testGetTokenData() throws InterruptedException, ExecutionException {
		final TokenDataDto tokenDataDto = new TokenDataDto();
		tokenDataDto.setValue("fa4b5f64-249b-11e9-ab14-d663bd873d93");
		tokenDataDto.setTtl(120);
		
		String tokenUrl = "http://test-token.io";
		
		when(restTemplate.exchange(tokenUrl, 
				HttpMethod.GET,
				new HttpEntity<>(headers), 
				TokenDataDto.class)).thenReturn(ResponseEntity.ok(tokenDataDto));
	
		CompletableFuture<TokenDataDto> result = exchangeService.getTokenData(tokenUrl);

		assertNotNull(result.get());
		assertThat(result.get().getValue(), is(tokenDataDto.getValue()));
		assertThat(result.get().getTtl(), is(tokenDataDto.getTtl()));
	}

}
