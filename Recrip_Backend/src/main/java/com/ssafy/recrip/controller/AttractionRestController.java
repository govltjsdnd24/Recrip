package com.ssafy.recrip.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.recrip.model.GugunDto;
import com.ssafy.recrip.model.SidoDto;
import com.ssafy.recrip.service.AttractionService;

import io.swagger.annotations.Api;

@CrossOrigin("*")
@RestController
@Api(tags = {"Recrip Attraction API"})
public class AttractionRestController {
	
	private AttractionService service;

	@Autowired
	public AttractionRestController(AttractionService service) {
		this.service=service;
	}
	
	@GetMapping("/restattrsido")
	public ResponseEntity<Map<String,Object>> restattrsido () {
		Map<String,Object>map =new HashMap<>();
		try {
			List<SidoDto>sido= service.listSido();
			if(sido.size()>0) {
				map.put("resmsg", sido);
				map.put("resdata", "1");
			} else {
				map.put("resmsg", "시도 리스트가 없음");
				map.put("resdata", "0");
			}
		} catch (Exception e) {
			e.printStackTrace();
			map.put("resmsg", "시도 리스트 불러오는 중 오류 발생");
		}
		
		ResponseEntity<Map<String,Object>> res= new ResponseEntity<Map<String,Object>>(map,HttpStatus.OK);
		return res;
	}
	
	@GetMapping("/restattrgugun")
	public ResponseEntity<Map<String,Object>> restattrgugun (String sido_code) {
		Map<String,Object>map =new HashMap<>();
		try {
			List<GugunDto>gugun= service.listGugun(sido_code);
			if(gugun.size()>0) {
				map.put("resmsg", gugun);
				map.put("resdata", "1");
			} else {
				map.put("resmsg", "구군 리스트가 없음");
				map.put("resdata", "0");
			}
		} catch (Exception e) {
			e.printStackTrace();
			map.put("resmsg", "구군 리스트 불러오는 중 오류 발생");
		}
		
		ResponseEntity<Map<String,Object>> res= new ResponseEntity<Map<String,Object>>(map,HttpStatus.OK);
		return res;
	}
}
