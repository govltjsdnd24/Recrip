package com.ssafy.recrip.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.recrip.mapper.AttractionMapper;
import com.ssafy.recrip.mapper.BoardMapper;
import com.ssafy.recrip.model.AttractionDto;
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

	@Override
	public List<AttractionDto> attrInfoList(Map<String,Object> map) {
		// TODO Auto-generated method stub
		System.out.println(map);
		return session.getMapper(AttractionMapper.class).attrInfoList(map);
	}

	@Override
	public int addscore(String content_id) {
		// TODO Auto-generated method stub
		Map<String, String> map = new HashMap<>();
		
		Map<String, Object> result = session.getMapper(BoardMapper.class).findscore(content_id);
		
		map.put("rating", String.valueOf(Integer.parseInt(String.valueOf(result.get("rating"))) + 4));
		map.put("content_id", content_id);
		
		return session.getMapper(AttractionMapper.class).addrating(map);
	}

	@Override
	public List<AttractionDto> attrRankList() {
		return session.getMapper(AttractionMapper.class).attrRankList();
	}
}
