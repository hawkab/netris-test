package ru.alarh.netris.domain;

import java.io.Serializable;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AggregateData implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private long id;
	private String urlType;
	private String videoUrl;
	private String value;
	private int ttl;
	
}
