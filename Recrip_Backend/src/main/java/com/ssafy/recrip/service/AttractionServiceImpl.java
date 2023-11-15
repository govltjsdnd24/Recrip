package com.ssafy.recrip.service;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.recrip.mapper.AttractionMapper;
import com.ssafy.recrip.model.GugunDto;
import com.ssafy.recrip.model.RankDto;
import com.ssafy.recrip.model.SidoDto;

@Service
public class AttractionServiceImpl implements AttractionService {
	
	private SqlSession session;
	
	@Autowired
	public AttractionServiceImpl(SqlSession session) {
		super();
		this.session = session;
	}

	@Override
	public List<SidoDto> listSido() {
		return session.getMapper(AttractionMapper.class).listSido();
	}

	@Override
	public List<GugunDto> listGugun(String sido) {
		return session.getMapper(AttractionMapper.class).listGugun(sido);
	}

	@Override
	public int insertRank(RankDto rankDto) {
		return session.getMapper(AttractionMapper.class).insertRank(rankDto);
	}

	@Override
	public List<RankDto> selectRank() {
		return session.getMapper(AttractionMapper.class).selectRank();
	}

	@Override
	public int updateRank(RankDto rankDto) {
		return session.getMapper(AttractionMapper.class).updateRank(rankDto);
	}


}
