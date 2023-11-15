package com.ssafy.enjoytrip.service;

import java.util.Map;

import com.ssafy.enjoytrip.model.MemberDto;

public interface MemberService {
	MemberDto login(MemberDto dto);
	int memberInsert(MemberDto dto);
	int memberUpdate(MemberDto dto);
	int memberDelete(String userid);
}
