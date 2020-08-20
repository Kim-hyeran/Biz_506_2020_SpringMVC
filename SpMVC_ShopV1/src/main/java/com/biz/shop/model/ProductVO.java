package com.biz.shop.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ProductVO {
	
	private String p_code;
	private String p_name;
	private String p_dcode;
	private String p_std;
	private int p_iprice;
	private int p_oprice;
	private String p_image;

}
