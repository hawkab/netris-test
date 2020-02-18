package ru.alarh.netris.service;

import java.util.Collection;
import java.util.concurrent.ExecutionException;

import ru.alarh.netris.domain.AggregateData;

public interface AggregateService {
	
	Collection<AggregateData> aggregate() throws InterruptedException, ExecutionException;

}
