package com.ssafy.recrip.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CommentDto {
	String commentno;
	String articleno;
	String userid;
	String content;
	String registdate;	
	boolean isdelete;
}
