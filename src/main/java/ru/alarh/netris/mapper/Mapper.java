package ru.alarh.netris.mapper;

import java.util.Arrays;

import org.springframework.stereotype.Component;

import lombok.extern.log4j.Log4j2;
import ru.alarh.netris.domain.AggregateData;
import ru.alarh.netris.dto.DataDto;
import ru.alarh.netris.dto.SourceDataDto;
import ru.alarh.netris.dto.TokenDataDto;
import ru.alarh.netris.exception.WrongTypeException;

@Log4j2
@Component
public class Mapper {
	
	/**
	 * Метод преобразования данных
	 * 
	 * @param data единица исходных данных
	 * @param source единица данных из источника, соответствующая исходным данным
	 * @param token единица данных по токену, соответствующая исходным данным
	 * @return объект агрегированных данные
	 * @throws WrongTypeException
	 */
	public AggregateData toAggregateData(DataDto data, SourceDataDto source, TokenDataDto token) throws WrongTypeException {
		if (!Arrays.asList("LIVE", "ARCHIVE").contains(source.getUrlType())) {
			log.error("Неверный тип камеры id = {}, urlType = {}", data.getId(), source.getUrlType());
			throw new WrongTypeException();
		}
			
		AggregateData aggregateData = new AggregateData();
		aggregateData.setId(data.getId());
		aggregateData.setUrlType(source.getUrlType());
		aggregateData.setVideoUrl(source.getVideoUrl());
		aggregateData.setValue(token.getValue());
		aggregateData.setTtl(token.getTtl());
		return aggregateData;
	}

}
