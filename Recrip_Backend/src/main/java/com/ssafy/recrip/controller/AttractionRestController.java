package com.ssafy.recrip.controller;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.recrip.model.GugunDto;
import com.ssafy.recrip.model.RankDto;
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
	
	@PostMapping("/restrankinsert")
	public ResponseEntity<Map<String, Object>> restrankinsert(@RequestBody RankDto rankDto) throws Exception {
		Map<String, Object> map = new HashMap<>();
		try {
			int result = service.insertRank(rankDto);
			if(result != 0) {
				map.put("resdata", "1");
				map.put("resmsg", "랭크 등록 성공");
			} else {
				map.put("resdata", "0");
				map.put("resmsg", "랭크 등록 실패");
			}
		} catch (Exception e) {
			e.printStackTrace();
			map.put("resmsg", "랭크 등록 오류 발생");
		}
		
		ResponseEntity<Map<String, Object>> res = new ResponseEntity<Map<String,Object>>(map,HttpStatus.OK);
		
		return res;
	}
	
	@GetMapping("/restranklist")
	public ResponseEntity<Map<String,Object>> restranklist () {
		Map<String,Object>map =new HashMap<>();
		try {
			List<RankDto>ranks= service.selectRank();
			if(ranks.size()>0) {
				map.put("resmsg", ranks);
				map.put("resdata", "1");
			} else {
				map.put("resmsg", "랭크 리스트가 없음");
				map.put("resdata", "0");
			}
		} catch (Exception e) {
			e.printStackTrace();
			map.put("resmsg", "랭크 리스트 불러오는 중 오류 발생");
		}
		
		ResponseEntity<Map<String,Object>> res= new ResponseEntity<Map<String,Object>>(map,HttpStatus.OK);
		return res;
	}
	
	@PutMapping("/restrankupdate")
	public ResponseEntity<Map<String, Object>> restrankupdate(@RequestBody RankDto rankDto) throws SQLException {
		Map<String, Object> map = new HashMap<>();
		try {
			int result = service.updateRank(rankDto);
			if(result != 0) {
				map.put("resdata", "1");
				map.put("resmsg", "랭크 수정 성공");
			} else {
				map.put("resdata", "0");
				map.put("resmsg", "랭크 수정 실패");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			map.put("resmsg", "랭크 수정 오류 발생");
		}
		
		ResponseEntity<Map<String, Object>> res = new ResponseEntity<Map<String,Object>>(map,HttpStatus.OK);
		
		return res;
	}
}
