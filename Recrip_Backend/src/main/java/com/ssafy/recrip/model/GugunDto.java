package com.ssafy.recrip.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class GugunDto {
	String gugun_code;
	String gugun_name;
	String sido_code;
}
