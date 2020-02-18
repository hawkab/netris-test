package ru.alarh.netris.mapper;

import org.springframework.stereotype.Component;

import ru.alarh.netris.domain.AggregateData;
import ru.alarh.netris.dto.DataDto;
import ru.alarh.netris.dto.SourceDataDto;
import ru.alarh.netris.dto.TokenDataDto;

@Component
public class Mapper {
	
	public AggregateData toAggregateData(DataDto data, SourceDataDto source, TokenDataDto token) {
		AggregateData aggregateData = new AggregateData();
		aggregateData.setId(data.getId());
		aggregateData.setUrlType(source.getUrlType());
		aggregateData.setVideoUrl(source.getVideoUrl());
		aggregateData.setValue(token.getValue());
		aggregateData.setTtl(token.getTtl());
		return aggregateData;
	}

}
