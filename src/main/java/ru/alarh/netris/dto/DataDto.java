package ru.alarh.netris.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class DataDto implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private long id;	
	private String sourceDataUrl;	
	private String tokenDataUrl;

}
