package com.ssafy.enjoytrip.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class EnjoyBoardDto {
	String contentid;
	String contenttypeid;
	String title;
	String addr1;
	String addr2;
	String zipcode;
	String tel;
	String first_image;
	String first_image2;
	String readcount;
	String sido_code;
	String gugun_code;
	String latitude;
	String longitude;
	String mlevel;
}
