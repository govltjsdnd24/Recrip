package com.ssafy.recrip.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.recrip.model.AttractionDto;
import com.ssafy.recrip.model.GugunDto;
import com.ssafy.recrip.model.RankDto;
import com.ssafy.recrip.model.SidoDto;

@Mapper
public interface AttractionMapper {
	List<AttractionDto> attrInfoList(Map<String,Object> map);
	List<SidoDto> listSido();
	List<GugunDto> listGugun(String sido);
	int insertRank(RankDto rankDto);
	List<RankDto> selectRank();
	int updateRank(RankDto rankDto);
	int addrating(Map<String,String> param);
	List<AttractionDto> attrRankList();
	List<AttractionDto> ratingCall(Map<String,String> param);
}
