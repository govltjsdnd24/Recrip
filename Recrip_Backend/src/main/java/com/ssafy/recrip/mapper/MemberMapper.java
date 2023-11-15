package com.ssafy.recrip.mapper;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.recrip.model.MemberDto;

@Mapper
public interface MemberMapper {

	MemberDto login(MemberDto dto);
	int memberInsert(MemberDto dto);
	int memberUpdate(MemberDto dto);
	int memberDelete(String userid);

}
