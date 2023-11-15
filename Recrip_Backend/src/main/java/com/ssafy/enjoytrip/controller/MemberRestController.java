package com.ssafy.enjoytrip.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ssafy.enjoytrip.model.BoardDto;
import com.ssafy.enjoytrip.model.MemberDto;
import com.ssafy.enjoytrip.service.MemberService;
import com.ssafy.enjoytrip.service.S3UploadService;
import com.ssafy.enjoytrip.util.SizeConstant;

import io.swagger.annotations.Api;

@CrossOrigin("*")
@RestController
@Api(tags = {"EnjoyTrip Member API V1"})
public class MemberRestController {
	
	private MemberService service;
	private S3UploadService s3service;
	
	@Autowired
	public MemberRestController(MemberService service, S3UploadService s3service) {
		this.service = service;
		this.s3service=s3service;
	}
	
	@PostMapping("/upload")
	public ResponseEntity<Map<String, Object>> upload(@RequestBody MultipartFile multipartFile) throws IllegalStateException, IOException, SQLException {
		Map<String, Object> map = new HashMap<>();
		try {
			String upload = s3service.saveFile(multipartFile);
			map.put("url", upload);
		} catch (Exception e) {
			e.printStackTrace();
			map.put("resmsg", e.toString());
		}
		
		ResponseEntity<Map<String, Object>> res = new ResponseEntity<Map<String,Object>>(map,HttpStatus.OK);
		
		return res;
	}
	
	@PostMapping("/restmemlogin")
	public ResponseEntity<Map<String, Object>> restmemlogin(@RequestBody MemberDto dto) throws IllegalStateException, IOException, SQLException {
		Map<String, Object> map = new HashMap<>();
		try {
			MemberDto login = service.login(dto);
			if(login.getUserid().equals(dto.getUserid())) {
				map.put("resmsg", login);
				map.put("resdata", "1");
			} else {
				map.put("resmsg", "로그인 실패");
				map.put("resdata", "0");
			}
		} catch (Exception e) {
			e.printStackTrace();
			map.put("resmsg", "로그인 오류 발생");
		}
		
		ResponseEntity<Map<String, Object>> res = new ResponseEntity<Map<String,Object>>(map,HttpStatus.OK);
		
		return res;
	}
	
	@PostMapping("/restmeminsert")
	public ResponseEntity<Map<String, Object>> restmeminsert(@RequestBody MemberDto dto) throws Exception {
		Map<String, Object> map = new HashMap<>();
		try {
			int result = service.memberInsert(dto);
			if(result != 0) {
				map.put("resdata", "1");
				map.put("resmsg", "멤버 등록 성공");
			} else {
				map.put("resdata", "0");
				map.put("resmsg", "멤버 등록 실패");
			}
		} catch (Exception e) {
			e.printStackTrace();
			map.put("resmsg", "멤버 등록 오류 발생");
		}
		
		ResponseEntity<Map<String, Object>> res = new ResponseEntity<Map<String,Object>>(map,HttpStatus.OK);
		
		return res;
	}
	
	@PutMapping("/restmemupdate")
	public ResponseEntity<Map<String, Object>> restmemupdate(@RequestBody MemberDto dto) throws SQLException {
		Map<String, Object> map = new HashMap<>();
		try {
			int result = service.memberUpdate(dto);
			if(result != 0) {
				map.put("resdata", "1");
				map.put("resmsg", "멤버 수정 성공");
			} else {
				map.put("resdata", "0");
				map.put("resmsg", "멤버 수정 실패");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			map.put("resmsg", "멤버 수정 오류 발생");
		}
		
		ResponseEntity<Map<String, Object>> res = new ResponseEntity<Map<String,Object>>(map,HttpStatus.OK);
		
		return res;
	}
	
	@DeleteMapping("/restmemdelete")
	public ResponseEntity<Map<String, Object>> restmemdelete(String userid) throws SQLException {
		Map<String, Object> map = new HashMap<>();
		try {
			int result = service.memberDelete(userid);
			if(result != 0) {
				map.put("resdata", "1");
				map.put("resmsg", "멤버 삭제 성공");
			} else {
				map.put("resdata", "0");
				map.put("resmsg", "멤버 삭제 실패");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			map.put("resmsg", "멤버 삭제 오류 발생");
		}
		
		ResponseEntity<Map<String, Object>> res = new ResponseEntity<Map<String,Object>>(map,HttpStatus.OK);
		
		return res;
	}
}
