package com.ssafy.recrip.model;

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
public class MessageDto {
	private String fromid;
	private String toid;
	private String subject;
	private String content;
	private String date;
	private String isdelete;
}
