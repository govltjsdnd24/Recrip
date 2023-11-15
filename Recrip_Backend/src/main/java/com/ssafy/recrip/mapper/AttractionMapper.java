package com.ssafy.recrip.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.recrip.model.GugunDto;
import com.ssafy.recrip.model.RankDto;
import com.ssafy.recrip.model.SidoDto;

@Mapper
public interface AttractionMapper {
	List<SidoDto> listSido();
	List<GugunDto> listGugun(String sido);
	int insertRank(RankDto rankDto);
	List<RankDto> selectRank();
	int updateRank(RankDto rankDto);
}
