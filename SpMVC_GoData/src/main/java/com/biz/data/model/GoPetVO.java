package com.biz.data.model;

import javax.xml.bind.annotation.XmlRootElement;

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
@XmlRootElement(name="list")
public class GoPetVO {
	
	private String apiSid;
	private String apiDongName;
	private String apiNewAddress;
	private String apiOldAddress;
	private String apiTel;
	private String apiLat;
	private String apiLng;
	private String apiRegDate;

}
