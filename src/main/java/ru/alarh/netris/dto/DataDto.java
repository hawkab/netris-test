package ru.alarh.netris.dto;

import java.io.Serializable;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DataDto implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private long id;	
	private String sourceDataUrl;	
	private String tokenDataUrl;

}
