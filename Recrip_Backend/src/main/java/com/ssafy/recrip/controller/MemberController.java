package com.ssafy.recrip.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ssafy.recrip.model.AttractionDto;
import com.ssafy.recrip.model.BoardDto;
import com.ssafy.recrip.model.CourseDto;
import com.ssafy.recrip.model.MemberDto;
import com.ssafy.recrip.model.MessageDto;
import com.ssafy.recrip.model.WishHisDto;
import com.ssafy.recrip.service.MemberService;
import com.ssafy.recrip.service.S3UpDownloadService;
import com.ssafy.recrip.util.SizeConstant;

import io.swagger.annotations.Api;

@CrossOrigin("*")
@RestController
@Api(tags = {"Recrip Member API"})
public class MemberController {
	
	private MemberService service;
	private S3UpDownloadService s3service;
	
	@Autowired
	public MemberController(MemberService service, S3UpDownloadService s3service) {
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
	
	@GetMapping("/download")
	public ResponseEntity<byte[]> download(@RequestParam String fileName) throws IllegalStateException, IOException, SQLException {
		Map<String, Object> map = new HashMap<>();
		try {
			map = s3service.getFile(fileName);
		} catch (Exception e) {
			e.printStackTrace();
			map.put("resmsg", e.toString());
		}
		ResponseEntity<byte[]> download=new ResponseEntity<byte[]>((byte[])map.get("bytes"),(HttpHeaders)map.get("headers"),HttpStatus.OK);
		return download;
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
				map.put("resmsg", "로그인 정보 존재하지 않음");
				map.put("resdata", "0");
			}
		} catch (Exception e) {
			e.printStackTrace();
			map.put("resmsg", "로그인 오류 발생");
		}
		
		ResponseEntity<Map<String, Object>> res = new ResponseEntity<Map<String,Object>>(map,HttpStatus.OK);
		
		return res;
	}
	
	@GetMapping("/restselectdeleted")
	public ResponseEntity<Map<String, Object>> restselectdeleted() throws IllegalStateException, IOException, SQLException {
		Map<String, Object> map = new HashMap<>();
		try {
			List<MemberDto> deleted = service.selectDeleted();
			if(deleted.size()>0) {
				map.put("resmsg", deleted);
				map.put("resdata", "1");
			} else {
				map.put("resmsg", "삭제된 사용자 없음");
				map.put("resdata", "0");
			}
		} catch (Exception e) {
			e.printStackTrace();
			map.put("resmsg", "삭제된 사용자 조회 중 오류 발생");
		}
		
		ResponseEntity<Map<String, Object>> res = new ResponseEntity<Map<String,Object>>(map,HttpStatus.OK);
		
		return res;
	}
	
	@PostMapping("/restmeminsert")
	public ResponseEntity<Map<String, Object>> restmeminsert(@RequestParam(required = false) MultipartFile multipartFile, @RequestParam String userid, @RequestParam String username, @RequestParam String userpwd) throws Exception {
		Map<String, Object> map = new HashMap<>();
		MemberDto dto=new MemberDto();
		dto.setUserid(userid);
		dto.setUsername(username);
		dto.setUserpwd(userpwd);
		try {
			String upload;
			if(multipartFile!=null) {
				upload = s3service.saveFile(multipartFile);
				dto.setProfile(upload);
			}
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
	public ResponseEntity<Map<String, Object>> restmemupdate(@RequestParam(required = false) MultipartFile multipartFile, @RequestParam String userid, @RequestParam String username, @RequestParam String userpwd) throws SQLException {
		Map<String, Object> map = new HashMap<>();
		MemberDto dto=new MemberDto();
		dto.setUserid(userid);
		dto.setUsername(username);
		dto.setUserpwd(userpwd);
		try {
			if(multipartFile!=null) {
				String upload = s3service.saveFile(multipartFile);
				dto.setProfile(upload);
			}
			int result = service.memberUpdate(dto);
			if(result != 0) {
				map.put("resdata", "1");
				map.put("profile",dto.getProfile());
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
	
	@DeleteMapping("/restmempermadelete")
	public ResponseEntity<Map<String, Object>> restmempermadelete(String userid) throws SQLException {
		Map<String, Object> map = new HashMap<>();
		try {
			int result = service.memberPermaDelete(userid);
			if(result != 0) {
				map.put("resdata", "1");
				map.put("resmsg", "멤버 영구 삭제 성공");
			} else {
				map.put("resdata", "0");
				map.put("resmsg", "멤버 영구 삭제 실패");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			map.put("resmsg", "멤버 영구 삭제 오류 발생");
		}
		
		ResponseEntity<Map<String, Object>> res = new ResponseEntity<Map<String,Object>>(map,HttpStatus.OK);
		
		return res;
	}
	
	@GetMapping("/historylist")
	public ResponseEntity<Map<String, Object>> historylist(@RequestParam Map<String, Object> param) throws IllegalStateException, IOException, SQLException {
		Map<String, Object> map = new HashMap<>();
		System.out.println(param);
		try {
			List<WishHisDto> list = service.historyList(param);
			List<AttractionDto> result = new ArrayList<>();
			
			for (WishHisDto dto : list) {
				AttractionDto a = service.getAttrInfo(dto.getContentid());
				result.add(a);
			}
			
			int totalpage = service.getTotalCount(param) / Integer.parseInt((String) param.get("size")) + 1;
			if(list.size() > 0) {
				map.put("resmsg", result);
				map.put("totalpage", totalpage);
				map.put("resdata", "1");
			} else {
				map.put("resmsg", "조회 실패");
				map.put("resdata", "0");
			}
			System.out.println(result.size());
		} catch (Exception e) {
			e.printStackTrace();
			map.put("resmsg", "조회 중 오류 발생");
		}
		
		ResponseEntity<Map<String, Object>> res = new ResponseEntity<Map<String,Object>>(map,HttpStatus.OK);
		
		return res;
	}
	
	@GetMapping("/wishlist")
	public ResponseEntity<Map<String, Object>> wishlist(@RequestParam Map<String, Object> param) throws IllegalStateException, IOException, SQLException {
		Map<String, Object> map = new HashMap<>();
		try {
			List<WishHisDto> list = service.wishList(param);
			List<AttractionDto> result = new ArrayList<>();
			
			for (WishHisDto dto : list) {
				AttractionDto a = service.getAttrInfo(dto.getContentid());
				result.add(a);
			}
			
			int totalpage = service.getTotalCount(param) / Integer.parseInt((String) param.get("size")) + 1;
			
			if(list.size()>0) {
				map.put("resid",list);
				map.put("resmsg", result);
				map.put("totalpage",totalpage);
				map.put("resdata", "1");
			} else {
				map.put("resmsg", "조회 실패");
				map.put("resdata", "0");
			}
		} catch (Exception e) {
			e.printStackTrace();
			map.put("resmsg", "조회 중 오류 발생");
		}
		
		ResponseEntity<Map<String, Object>> res = new ResponseEntity<Map<String,Object>>(map,HttpStatus.OK);
		
		return res;
	}
	
	@GetMapping("/courselist")
	public ResponseEntity<Map<String, Object>> courselist(@RequestParam Map<String, Object> param) throws IllegalStateException, IOException, SQLException {
		Map<String, Object> map = new HashMap<>();
		int size = Integer.parseInt((String)param.get("size"));
		
		try {
			List<CourseDto> list = service.courseList(param);
			List<AttractionDto> result = new ArrayList<>();
			List<String> group = new ArrayList<>();
			
			for (CourseDto dto : list) {
				AttractionDto a = service.getAttrInfo(dto.getContentid());
				result.add(a);
				group.add(dto.getGroupno());
			}
			
			System.out.println(list);
			
			int totalpage = service.getCourseCount((String)param.get("userid")) / size + 1;
			
			if(list.size()>0) {
				map.put("resmsg", result);
				map.put("resgroup", group);
				map.put("totalpage",totalpage);
				map.put("resdata", "1");
			} else {
				map.put("resmsg", "조회 실패");
				map.put("resdata", "0");
			}
		} catch (Exception e) {
			e.printStackTrace();
			map.put("resmsg", "조회 중 오류 발생");
		}
		
		ResponseEntity<Map<String, Object>> res = new ResponseEntity<Map<String,Object>>(map,HttpStatus.OK);
		
		return res;
	}
	
	@GetMapping("/coursereview")
	public ResponseEntity<Map<String, Object>> coursereview(@RequestParam Map<String, Object> param) throws IllegalStateException, IOException, SQLException {
		Map<String, Object> map = new HashMap<>();
		List<Integer> course = service.courseReview(param);
		List<AttractionDto> result = new ArrayList<>();
		try {
			for (Integer c : course) {
				AttractionDto attrInfo = service.getAttrInfo(String.valueOf(c));
				result.add(attrInfo);
			}
			if(result.size()>0) {
				map.put("resmsg", result);
				map.put("resdata", "1");
			} else {
				map.put("resmsg", "조회 실패");
				map.put("resdata", "0");
			}
		} catch (Exception e) {
			e.printStackTrace();
			map.put("resmsg", "조회 중 오류 발생");
		}
		
		ResponseEntity<Map<String, Object>> res = new ResponseEntity<Map<String,Object>>(map,HttpStatus.OK);
		
		return res;
	}
	
	@PostMapping("/historyinsert")
	public ResponseEntity<Map<String, Object>> historyinsert(@RequestBody WishHisDto dto) throws Exception {
		Map<String, Object> map = new HashMap<>();
		System.out.println(dto);
		try {
			int result = service.histroyInsert(dto);
			if(result != 0) {
				map.put("resdata", "1");
				map.put("resmsg", "등록 성공");
			} else {
				map.put("resdata", "0");
				map.put("resmsg", "등록 실패");
			}
		} catch (Exception e) {
			e.printStackTrace();
			map.put("resmsg", "등록 오류 발생");
		}
		
		ResponseEntity<Map<String, Object>> res = new ResponseEntity<Map<String,Object>>(map,HttpStatus.OK);
		
		return res;
	}
	
	@PostMapping("/wishinsert")
	public ResponseEntity<Map<String, Object>> wishinsert(@RequestBody WishHisDto dto) throws Exception {
		Map<String, Object> map = new HashMap<>();
		try {
			int result = service.wishInsert(dto);
			if(result != 0) {
				map.put("resdata", "1");
				map.put("resmsg", "등록 성공");
			} else {
				map.put("resdata", "0");
				map.put("resmsg", "등록 실패");
			}
		} catch (Exception e) {
			e.printStackTrace();
			map.put("resmsg", "등록 오류 발생");
		}
		
		ResponseEntity<Map<String, Object>> res = new ResponseEntity<Map<String,Object>>(map,HttpStatus.OK);
		
		return res;
	}
	
	@PostMapping("/courseinsert")
	public ResponseEntity<Map<String, Object>> courseinsert(@RequestBody String dto) throws Exception {
		Map<String, Object> map = new HashMap<>();
		JSONParser parser = new JSONParser();
		JSONObject object = (JSONObject) parser.parse(dto);
		
		JSONArray list = (JSONArray) parser.parse((String) object.get("dto"));
		
		System.out.println(list);
		List<CourseDto> param = new ArrayList<CourseDto>();
		
		for (Object obj : list) {
			CourseDto d = new CourseDto();
			d.setContentid((String)((JSONObject) obj).get("content_id"));
			d.setUserid((String)((JSONObject) obj).get("userid"));
			param.add(d);
		}
		
		try {
			int result = service.courseInsert(param);
			if(result != 0) {
				map.put("resdata", "1");
				map.put("resmsg", "등록 성공");
			} else {
				map.put("resdata", "0");
				map.put("resmsg", "등록 실패");
			}
		} catch (Exception e) {
			e.printStackTrace();
			map.put("resmsg", "등록 오류 발생");
		}
		
		ResponseEntity<Map<String, Object>> res = new ResponseEntity<Map<String,Object>>(map,HttpStatus.OK);
		
		return res;
	}
	
	@DeleteMapping("/histroydelete")
	public ResponseEntity<Map<String, Object>> histroydelete(String contentid) throws SQLException {
		Map<String, Object> map = new HashMap<>();
		try {
			int result = service.historydelete(contentid);
			if(result != 0) {
				map.put("resdata", "1");
				map.put("resmsg", "삭제 성공");
			} else {
				map.put("resdata", "0");
				map.put("resmsg", "삭제 실패");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			map.put("resmsg", "삭제 오류 발생");
		}
		
		ResponseEntity<Map<String, Object>> res = new ResponseEntity<Map<String,Object>>(map,HttpStatus.OK);
		
		return res;
	}
	
	@DeleteMapping("/wishdelete")
	public ResponseEntity<Map<String, Object>> wishdelete(String articleno) throws SQLException {
		Map<String, Object> map = new HashMap<>();
		try {
			int result = service.wishdelete(articleno);
			if(result != 0) {
				map.put("resdata", "1");
				map.put("resmsg", "삭제 성공");
			} else {
				map.put("resdata", "0");
				map.put("resmsg", "삭제 실패");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			map.put("resmsg", "삭제 오류 발생");
		}
		
		ResponseEntity<Map<String, Object>> res = new ResponseEntity<Map<String,Object>>(map,HttpStatus.OK);
		
		return res;
	}
	
	@DeleteMapping("/coursedelete")
	public ResponseEntity<Map<String, Object>> coursedelete(String groupno) throws SQLException {
		Map<String, Object> map = new HashMap<>();
		try {
			int result = service.coursedelete(groupno);
			if(result != 0) {
				map.put("resdata", "1");
				map.put("resmsg", "삭제 성공");
			} else {
				map.put("resdata", "0");
				map.put("resmsg", "삭제 실패");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			map.put("resmsg", "삭제 오류 발생");
		}
		
		ResponseEntity<Map<String, Object>> res = new ResponseEntity<Map<String,Object>>(map,HttpStatus.OK);
		
		return res;
	}

	@GetMapping("/messagelist")
	public ResponseEntity<Map<String, Object>> messagelist(@RequestParam String userid) throws IllegalStateException, IOException, SQLException {
		Map<String, Object> map = new HashMap<>();
		try {
			List<MessageDto> list = service.messageList(userid);
			if(list.size()>0) {
				map.put("resmsg", list);
				map.put("resdata", "1");
			} else {
				map.put("resmsg", "조회 실패");
				map.put("resdata", "0");
			}
		} catch (Exception e) {
			e.printStackTrace();
			map.put("resmsg", "조회 중 오류 발생");
		}
		
		ResponseEntity<Map<String, Object>> res = new ResponseEntity<Map<String,Object>>(map,HttpStatus.OK);
		
		return res;
	}
	
	@PostMapping("/messageinsert")
	public ResponseEntity<Map<String, Object>> messageinsert(@RequestBody MessageDto dto) throws Exception {
		Map<String, Object> map = new HashMap<>();
		try {
			int result = service.messageinsert(dto);
			if(result != 0) {
				map.put("resdata", "1");
				map.put("resmsg", "등록 성공");
			} else {
				map.put("resdata", "0");
				map.put("resmsg", "등록 실패");
			}
		} catch (Exception e) {
			e.printStackTrace();
			map.put("resmsg", "등록 오류 발생");
		}
		
		ResponseEntity<Map<String, Object>> res = new ResponseEntity<Map<String,Object>>(map,HttpStatus.OK);
		
		return res;
	}
	
	@DeleteMapping("/messagedelete")
	public ResponseEntity<Map<String, Object>> messagedelete(String messageno) throws SQLException {
		Map<String, Object> map = new HashMap<>();
		try {
			int result = service.messagedelete(messageno);
			if(result != 0) {
				map.put("resdata", "1");
				map.put("resmsg", "삭제 성공");
			} else {
				map.put("resdata", "0");
				map.put("resmsg", "삭제 실패");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			map.put("resmsg", "삭제 오류 발생");
		}
		
		ResponseEntity<Map<String, Object>> res = new ResponseEntity<Map<String,Object>>(map,HttpStatus.OK);
		
		return res;
	}
	
	@GetMapping("/getcourse")
	public ResponseEntity<Map<String, Object>> getcourse(String groupno, String userid) throws IllegalStateException, IOException, SQLException {
		Map<String, Object> map = new HashMap<>();
		
		Map<String, String> param = new HashMap<>();
		param.put("groupno", groupno);
		param.put("userid", userid);
		List<Integer> getcourse = service.getcourse(param);
		List<AttractionDto> result = new ArrayList<>();
		
		for (Integer id : getcourse) {
			AttractionDto attr = service.getAttrInfo(String.valueOf(id));
			result.add(attr);
		}
		
		try {
			
			if(result.size()>0) {
				map.put("resdata", result);
				map.put("resmsg", "1");
			} else {
				map.put("resmsg", "조회 실패");
				map.put("resdata", "0");
			}
		} catch (Exception e) {
			e.printStackTrace();
			map.put("resmsg", "조회 중 오류 발생");
		}
		
		ResponseEntity<Map<String, Object>> res = new ResponseEntity<Map<String,Object>>(map,HttpStatus.OK);
		
		return res;
	}
	
	@GetMapping("/getattraction")
	public ResponseEntity<Map<String, Object>> getattracion(String content_id) throws IllegalStateException, IOException, SQLException {
		Map<String, Object> map = new HashMap<>();
		
		List<AttractionDto> result = new ArrayList<>();
		AttractionDto attr = service.getAttrInfo(content_id);
		result.add(attr);
		
		try {
			if(result.size() > 0) {
				map.put("resdata", result);
				map.put("resmsg", "1");
			} else {
				map.put("resmsg", "조회 실패");
				map.put("resdata", "0");
			}
		} catch (Exception e) {
			e.printStackTrace();
			map.put("resmsg", "조회 중 오류 발생");
		}
		
		ResponseEntity<Map<String, Object>> res = new ResponseEntity<Map<String,Object>>(map,HttpStatus.OK);
		
		return res;
	}
}