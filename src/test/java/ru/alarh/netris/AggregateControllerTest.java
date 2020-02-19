package ru.alarh.netris;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.HashSet;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.*;

import ru.alarh.netris.domain.AggregateData;
import ru.alarh.netris.service.AggregateService;

@SpringBootTest
@AutoConfigureMockMvc
public class AggregateControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private AggregateService aggregateService;
	
	/**
	 * Проверка доступности метода получения агрегированных данных
	 * 
	 * @throws Exception
	 */
	@Test
	public void testAggregateControllerCode2xx() throws Exception {
		when(aggregateService.aggregate()).thenReturn(new HashSet<AggregateData>());		
		mockMvc.perform(get("/aggregate"))
			.andDo(print())
			.andExpect(status().is2xxSuccessful());
	}
	
	/**
	 * Проверка валидности данных, которые вернул метод получения агрегированных данных
	 * 
	 * @throws Exception
	 */
	@Test
	public void testAggregateControllerValidContent() throws Exception {
		AggregateData aggregateData = new AggregateData();
		aggregateData.setId(1L);
		aggregateData.setUrlType("LIVE");
		aggregateData.setValue("fa4b5f64-249b-11e9-ab14-d663bd873d93");
		aggregateData.setTtl(120);
		aggregateData.setVideoUrl("rtsp://127.0.0.1/20");
		
		HashSet<AggregateData> result = new HashSet<AggregateData>();
		result.add(aggregateData);
		
		when(aggregateService.aggregate()).thenReturn(result);
		mockMvc.perform(get("/aggregate"))
			.andDo(print())
			.andExpect(status().is2xxSuccessful())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$", hasSize(1)))
			.andExpect(jsonPath("$[0].id", is(1)))
			.andExpect(jsonPath("$[0].urlType", is("LIVE")))
			.andExpect(jsonPath("$[0].value", is("fa4b5f64-249b-11e9-ab14-d663bd873d93")))
			.andExpect(jsonPath("$[0].ttl", is(120)))
			.andExpect(jsonPath("$[0].videoUrl", is("rtsp://127.0.0.1/20")));
	}

}
