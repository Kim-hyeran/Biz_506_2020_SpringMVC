package com.biz.valid.model;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/*
 * validation-api, hibernate-validate를 이용한 server단 유효성 검사
 * @NotBlank : 공백으로 둘 수 없음
 * @NotNull : Null일 수 없음
 * @Null : 입력할 수 없음(Null이어야 함)
 * @Size : 배열, 문자열의 개수 범위
 * @Email : 이메일 형식 준수
 * @Min(x) : 입력한 숫자가 x 이하일 수 없음
 * @Max(x) : 입력한 숫자가 x 이상일 수 없음
 * @DecimalMin(x) : 숫자가 10진수이고, x 이하일 수 없음
 * @DecimalMax(x) : 숫자가 10진수이고, x 이상일 수 없음
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserVO {
	
	@NotBlank(message = "* 이름은 반드시 입력하여야 합니다")
	private String name;
	
	@NotBlank(message = "* Email은 반드시 입력하여야 합니다")
	@Email(message = "* Email 형식이 잘못되었습니다")
	private String email;
	
	@DecimalMax(value = "30", message = "* 나이는 30세 이하로만 입력 가능합니다")
	@DecimalMin(value = "10", message = "* 나이는 10세 이상만 입력 가능합니다")
//	@Size(min = 10, max = 30, message = "* 나이는 10부터 30세까지만 입력할 수 있습니다")
	private String age;

}
