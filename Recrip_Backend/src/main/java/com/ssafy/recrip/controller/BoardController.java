package com.ssafy.recrip.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.stream.events.Comment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.recrip.model.BoardDto;
import com.ssafy.recrip.model.CommentDto;
import com.ssafy.recrip.model.EnjoyBoardDto;
import com.ssafy.recrip.service.BoardService;
import com.ssafy.recrip.util.SizeConstant;

import io.swagger.annotations.Api;

@CrossOrigin("*")
@RestController
@Api(tags = {"EnjoyTrip Board API V1"})
public class BoardController {
	
	BoardService service;

	@Autowired
	public BoardController(BoardService service) {
		super();
		this.service = service;
	}
	
	@PostMapping("/boardwrite")
	public ResponseEntity<Map<String, Object>> insert(@RequestBody BoardDto dto) throws IllegalStateException, IOException, SQLException {
		Map<String, Object> map = new HashMap<>();
		System.out.println("123");
		try {
			service.boardwrite(dto);
			map.put("resmsg", "입력성공");
			map.put("resdata", "1");
		} catch (Exception e) {
			e.printStackTrace();
			map.put("resmsg", "입력실패");
		}
		
		ResponseEntity<Map<String, Object>> res = new ResponseEntity<Map<String,Object>>(map,HttpStatus.OK);
		
		return res;
	}
	
	@GetMapping("/boardlist")
	public ResponseEntity<Map<String, Object>> list(@RequestParam Map<String, String> map) throws Exception {
		System.out.println(map);
		Map<String, Object> resultmap = new HashMap<>();
		try {
			Map<String, Object> result = service.boardlist(map);
			resultmap.put("resdata", result);
			resultmap.put("resmsg", "조회성공");
		} catch (Exception e) { 
			e.printStackTrace();
			resultmap.put("resmsg", "조회실패");
		}
		
		ResponseEntity<Map<String, Object>> res = new ResponseEntity<Map<String,Object>>(resultmap,HttpStatus.OK);
		
		return res;
	}
	
	@GetMapping("/boardview")
	public ResponseEntity<Map<String, Object>> view(String articleno) throws SQLException {
		Map<String, Object> map = new HashMap<>();
		try {
			BoardDto dto = service.boardview(articleno);
			map.put("resdata", dto);
			map.put("resmsg", "조회성공");
		} catch (Exception e) {
			e.printStackTrace();
			map.put("resmsg", "조회실패");
		}
		
		ResponseEntity<Map<String, Object>> res = new ResponseEntity<Map<String,Object>>(map,HttpStatus.OK);
		
		return res;
		
	}
	
	@PutMapping("/boardupdate")
	public ResponseEntity<Map<String, Object>> update(@RequestBody BoardDto dto) throws SQLException {
		Map<String, Object> map = new HashMap<>();
		try {
			service.boardupdate(dto);
			map.put("resdata", dto);
			map.put("resmsg", "수정성공");
		} catch (Exception e) {
			e.printStackTrace();
			map.put("resmsg", "수정실패");
		}
		
		ResponseEntity<Map<String, Object>> res = new ResponseEntity<Map<String,Object>>(map,HttpStatus.OK);
		
		return res;
	}
	
	@DeleteMapping("/boarddelete")
	public ResponseEntity<Map<String, Object>> update(String articleno) throws SQLException {
		Map<String, Object> map = new HashMap<>();
		try {
			service.boarddelete(articleno);
			map.put("resdata", "1");
			map.put("resmsg", "삭제성공");
		} catch (Exception e) {
			e.printStackTrace();
			map.put("resmsg", "삭제실패");
		}
		
		ResponseEntity<Map<String, Object>> res = new ResponseEntity<Map<String,Object>>(map,HttpStatus.OK);
		
		return res;
	}
	
	@GetMapping("/enjoyboardlist")
	public ResponseEntity<Map<String, Object>> enjoylist(@RequestParam(defaultValue = "") String sido,
														@RequestParam(defaultValue = "") String content,
														@RequestParam(defaultValue = "") String word) throws Exception {
		Map<String, Object> map = new HashMap<>();
		try {
			Map<String,String> search = new HashMap<>();
			search.put("sido" , sido);
			search.put("content",content);
			search.put("word", word);
			List<EnjoyBoardDto> list = service.enjoyboardlist(search);
			System.out.println(list);
			map.put("resdata", list);
			map.put("resmsg", "조회성공");
		} catch (Exception e) {
			e.printStackTrace();
			map.put("resmsg", "조회실패");
		}
		
		ResponseEntity<Map<String, Object>> res = new ResponseEntity<Map<String,Object>>(map,HttpStatus.OK);
		
		return res;
	}
	
	@GetMapping("/commentlist")
	public ResponseEntity<Map<String, Object>> commentlist(String articleno) throws Exception {
		Map<String, Object> map = new HashMap<>();
		try {
			List<CommentDto> list = service.commentlist(articleno);
			System.out.println(list);
			map.put("resdata", list);
			map.put("resmsg", "조회성공");
		} catch (Exception e) {
			e.printStackTrace();
			map.put("resmsg", "조회실패");
		}
		
		ResponseEntity<Map<String, Object>> res = new ResponseEntity<Map<String,Object>>(map,HttpStatus.OK);
		
		return res;
	}
	
	@PostMapping("/commentwrite")
	public ResponseEntity<Map<String, Object>> commentinsert(@RequestBody CommentDto dto) throws IllegalStateException, IOException, SQLException {
		Map<String, Object> map = new HashMap<>();
		
		try {
			service.commentwrite(dto);
			map.put("resmsg", "입력성공");
			map.put("resdata", "1");
		} catch (Exception e) {
			e.printStackTrace();
			map.put("resmsg", "입력실패");
		}
		
		ResponseEntity<Map<String, Object>> res = new ResponseEntity<Map<String,Object>>(map,HttpStatus.OK);
		
		return res;
	}
	
	@DeleteMapping("/commentdelete")
	public ResponseEntity<Map<String, Object>> commentdelete(String commentno) throws SQLException {
		Map<String, Object> map = new HashMap<>();
		try {
			service.commentdelete(commentno);
			map.put("resdata", "1");
			map.put("resmsg", "삭제성공");
		} catch (Exception e) {
			e.printStackTrace();
			map.put("resmsg", "삭제실패");
		}
		
		ResponseEntity<Map<String, Object>> res = new ResponseEntity<Map<String,Object>>(map,HttpStatus.OK);
		
		return res;
	}
	
	@GetMapping("/boardtotalpage")
	public ResponseEntity<Map<String, Object>> boardtotalpage() throws SQLException {
		Map<String, Object> map = new HashMap<>();
		try {
			int totalpage = service.getTotalPage();
			map.put("resdata", totalpage);
			map.put("resmsg", "조회성공");
		} catch (Exception e) {
			e.printStackTrace();
			map.put("resmsg", "조회실패");
		}
		
		ResponseEntity<Map<String, Object>> res = new ResponseEntity<Map<String,Object>>(map,HttpStatus.OK);
		
		return res;
		
	}
}
