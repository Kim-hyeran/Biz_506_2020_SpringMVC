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
	
	//DB와 연동할 때 CHAR(1) TinyInt형으로 선언된 칼럼과 연동하기 위해 사용하는 type
	//사용하는 type이 type 0~255, -128~127까지의 값만 저장하는 데이터
	//메모리 공간 크기가 1byte(8bit)
	//int형은 java에서 4byte 크기를 사용
	//String형은 wrapper 클래스이다보니 실제로 상당히 큰 메모리 공간 차지
	//단순히 flag와 같은 데이터를 취급할 때는 char, byte형으로 사용하면 메모리 절약 가능
	private Byte p_not_use;
	/*
	 * DB p_not_use 칼럼의 값이 NULL(=IS NULL)이면 정상적인 데이터이고
	 * NULL이 아니면(IS NOT NULL이면) 삭제된 데이터로 취급
	 * update를 수행할 때 VO의 칼럼에 값이 null이면 jdbcType, VARCHAR, config 수행 설정 때문에 DB update시 NULL이 아닌 다른 값이 저장됨
	 * Dao.update()를 수행하기 전에 VO에 해당 칼럼의 값을 강제로 NULL을 입력해줄 필요가 있음
	 * 	->기본형인 변수는 NULL을 저장하여 DB로 보낼 수 없기 때문에 byte형이 아닌 Byte wrapper class형으로 설정
	 */

}
