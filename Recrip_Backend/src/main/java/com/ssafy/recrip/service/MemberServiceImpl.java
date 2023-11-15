package com.ssafy.recrip.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.recrip.mapper.MemberMapper;
import com.ssafy.recrip.model.MemberDto;

@Service
public class MemberServiceImpl implements MemberService{
	
	private SqlSession session;
	
	@Autowired
	public MemberServiceImpl(SqlSession session) {
		super();
		this.session = session;
	}

	@Override
	public MemberDto login(MemberDto dto) {
		// TODO Auto-generated method stub
		return session.getMapper(MemberMapper.class).login(dto);
	}
	
	@Override
	public List<MemberDto> selectDeleted() {
		// TODO Auto-generated method stub
		return session.getMapper(MemberMapper.class).selectDeleted();
	}

	@Override
	public int memberInsert(MemberDto dto) {
		// TODO Auto-generated method stub
		return session.getMapper(MemberMapper.class).memberInsert(dto);
	}

	@Override
	public int memberUpdate(MemberDto dto) {
		// TODO Auto-generated method stub
		return session.getMapper(MemberMapper.class).memberUpdate(dto);
	}

	@Override
	public int memberDelete(String userid) {
		// TODO Auto-generated method stub
		return session.getMapper(MemberMapper.class).memberDelete(userid);
	}
	
	@Override
	public int memberPermaDelete(String userid) {
		// TODO Auto-generated method stub
		return session.getMapper(MemberMapper.class).memberPermaDelete(userid);
	}

}