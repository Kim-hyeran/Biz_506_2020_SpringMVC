package com.biz.data.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class BisStation {
	
	private String STATION_NUM;
	private String BUSSTOP_NAME;
	private String ARS_ID;
	private String NEXT_BUSSTOP;
	private String BUSSTOP_ID;

}
