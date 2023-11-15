package com.ssafy.recrip.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class BoardDto {
	String articleno;
	String userid;
	String subject;
	String content;
	String date;
	String hit;
	boolean isdelete;
}
