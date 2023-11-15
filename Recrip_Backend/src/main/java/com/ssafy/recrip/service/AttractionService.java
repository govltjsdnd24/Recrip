package com.ssafy.recrip.service;

import java.util.List;

import com.ssafy.recrip.model.GugunDto;
import com.ssafy.recrip.model.SidoDto;

public interface AttractionService {
	List<SidoDto> listSido();
	List<GugunDto> listGugun(String sido);
}
