package com.ssafy.recrip.service;

import java.util.Map;

import com.ssafy.recrip.model.MemberDto;

public interface MemberService {
	MemberDto login(MemberDto dto);
	int memberInsert(MemberDto dto);
	int memberUpdate(MemberDto dto);
	int memberDelete(String userid);
}
