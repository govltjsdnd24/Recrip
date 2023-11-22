package com.ssafy.recrip.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.recrip.model.AttractionDto;
import com.ssafy.recrip.model.CourseDto;
import com.ssafy.recrip.model.MemberDto;
import com.ssafy.recrip.model.MessageDto;
import com.ssafy.recrip.model.WishHisDto;

@Mapper
public interface MemberMapper {

	MemberDto login(MemberDto dto);
	List<MemberDto> selectDeleted();
	int memberInsert(MemberDto dto);
	int memberUpdate(MemberDto dto);
	int memberDelete(String userid);
	int memberPermaDelete(String userid);
	List<WishHisDto> historyList(Map<String, Object> param);
	List<WishHisDto> wishList(Map<String, Object> param);
	List<CourseDto> courseList(Map<String, Object> param);
	int histroyInsert(WishHisDto dto);
	int wishInsert(WishHisDto dto);
	int courseInsert(CourseDto dto);
	int historydelete(String contentid);
	int wishdelete(String contentid);
	int coursedelete(String groupno);
	String findMaxGroupno();
	List<MessageDto> messageList(String userid);
	int messageinsert(MessageDto dto);
	int messagedelete(String messageno);
	int getTotalCount(Map<String,Object> param);
	AttractionDto getAttrInfo(String contentid);
	int getCourseCount(String userid);
	List<Integer> courseReview(Map<String,Object> param);
	List<Integer> getcourse(String groupno);
}