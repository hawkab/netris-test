package ru.alarh.netris.dto;

import java.io.Serializable;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SourceDataDto implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String urlType;
	private String videoUrl;

}
