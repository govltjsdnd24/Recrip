package com.ssafy.recrip.service;

import java.util.List;
import java.util.Map;

import com.ssafy.recrip.model.AttractionDto;
import com.ssafy.recrip.model.GugunDto;
import com.ssafy.recrip.model.RankDto;
import com.ssafy.recrip.model.SidoDto;

public interface AttractionService {
	List<AttractionDto> attrInfoList(Map<String,Object> map);
	List<SidoDto> listSido();
	List<GugunDto> listGugun(String sido);
	int insertRank(RankDto rankDto);
	List<RankDto> selectRank();
	int updateRank(RankDto rankDto);
	int addscore(String content_id);
	List<AttractionDto> attrRankList();
	List<AttractionDto> ratingCall(Map<String,String> param);
}
