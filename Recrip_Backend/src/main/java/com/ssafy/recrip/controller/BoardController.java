package com.ssafy.recrip.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
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
import org.springframework.web.multipart.MultipartFile;

import com.ssafy.recrip.model.BoardDto;
import com.ssafy.recrip.model.CommentDto;
import com.ssafy.recrip.model.FileDto;
import com.ssafy.recrip.model.AttractionDto;
import com.ssafy.recrip.service.BoardService;
import com.ssafy.recrip.service.MemberService;
import com.ssafy.recrip.service.S3UpDownloadService;
import com.ssafy.recrip.util.SizeConstant;

import io.swagger.annotations.Api;

@CrossOrigin("*")
@RestController
@Api(tags = {"Recrip Board API V1"})
public class BoardController {
	
	BoardService service;
	private S3UpDownloadService s3service;

	
	@Autowired
	public BoardController(BoardService service, S3UpDownloadService s3service) {
		super();
		this.service = service;
		this.s3service = s3service;
	}
	
	@GetMapping("/freeboardlike")
	public ResponseEntity<Map<String, Object>> freeboardlike(@RequestParam String articleno, @RequestParam String userid) throws IllegalStateException, IOException, SQLException {
		Map<String, Object> map = new HashMap<>();
		
		try {
			int dupl=service.freeBoardLikeCheck(articleno,userid);
			if(dupl==0) {
				service.freeBoardLike(articleno);
				service.freeBoardLikeAdd(articleno,userid);
				map.put("resmsg", "입력성공");
			}
			else if(dupl==1)
				map.put("resmsg","중복확인");
			
			map.put("resdata", "1");
		} catch (Exception e) {
			e.printStackTrace();
			map.put("resmsg", "입력실패");
			map.put("resdata", "0");
		}
		ResponseEntity<Map<String, Object>> res = new ResponseEntity<Map<String,Object>>(map,HttpStatus.OK);
		return res;
	}
	
	@GetMapping("/reviewboardlike")
	public ResponseEntity<Map<String, Object>> reviewboardlike(@RequestParam String articleno,@RequestParam String userid) throws IllegalStateException, IOException, SQLException {
		Map<String, Object> map = new HashMap<>();
		
		try {
			int dupl=service.reviewBoardLikeCheck(articleno,userid);
			if (dupl==0) {
				service.reviewBoardLike(articleno);
				service.reviewBoardLikeAdd(articleno,userid);
				map.put("resmsg", "입력성공");
			}
			else 
				map.put("resmsg","중복확인");
			map.put("resdata", "1");
		} catch (Exception e) {
			e.printStackTrace();
			map.put("resmsg", "입력실패");
			map.put("resdata", "0");
		}
		ResponseEntity<Map<String, Object>> res = new ResponseEntity<Map<String,Object>>(map,HttpStatus.OK);
		return res;
	}
	
	@GetMapping("/reviewboardmostlikes")
	public ResponseEntity<Map<String, Object>> reviewboardmostlikes() throws IllegalStateException, IOException, SQLException {
		Map<String, Object> map = new HashMap<>();
		try {
			List<BoardDto> dtoList=service.reviewBoardMostLikes();
			map.put("resmsg",dtoList);
			map.put("resdata", "1");
		} catch (Exception e) {
			e.printStackTrace();
			map.put("resmsg", "조회실패");
			map.put("resdata", "0");
		}
		ResponseEntity<Map<String, Object>> res = new ResponseEntity<Map<String,Object>>(map,HttpStatus.OK);
		return res;
	}
	
	@GetMapping("/freeboardlikecount")
	public ResponseEntity<Map<String, Object>> freeboardlikecount(@RequestParam String articleno) throws IllegalStateException, IOException, SQLException {
		Map<String, Object> map = new HashMap<>();
		
		try {
			int result=service.freeBoardLikeCount(articleno);
			map.put("resmsg", result);
			map.put("resdata", "1");
		} catch (Exception e) {
			e.printStackTrace();
			map.put("resmsg", "입력실패");
			map.put("resdata", "0");
		}
		ResponseEntity<Map<String, Object>> res = new ResponseEntity<Map<String,Object>>(map,HttpStatus.OK);
		return res;
	}
	
	@GetMapping("/reviewboardlikecount")
	public ResponseEntity<Map<String, Object>> reviewboardlikecount(@RequestParam String articleno) throws IllegalStateException, IOException, SQLException {
		Map<String, Object> map = new HashMap<>();
		
		try {
			int result=service.reviewBoardLikeCount(articleno);
			map.put("resmsg", result);
			map.put("resdata", "1");
		} catch (Exception e) {
			e.printStackTrace();
			map.put("resmsg", "입력실패");
			map.put("resdata", "0");
		}
		ResponseEntity<Map<String, Object>> res = new ResponseEntity<Map<String,Object>>(map,HttpStatus.OK);
		return res;
	}

	@PostMapping("/freeboardwrite")
	public ResponseEntity<Map<String, Object>> freeboardwrite(@RequestParam String userid , @RequestParam String subject, @RequestParam String content, @RequestParam(required = false) List<MultipartFile> multipartFile) throws IllegalStateException, IOException, SQLException {
		Map<String, Object> map = new HashMap<>();
		BoardDto dto = new BoardDto();
		dto.setUserid(userid);
		dto.setSubject(subject);
		dto.setContent(content);
		
		try {
			service.freeBoardWrite(dto);
			int articleno = service.freeBoardLastArticleno();
			
			if(multipartFile != null) {
				for (MultipartFile files : multipartFile) {
					FileDto fileDto = new FileDto();
					fileDto.setArticleno(String.valueOf(articleno));
					fileDto.setFilename(files.getOriginalFilename());
					fileDto.setUrl(s3service.saveFile(files));
					service.freeBoardFileWrite(fileDto);
				}
			}
			
			map.put("resmsg", "입력성공");
			map.put("resdata", "1");
		} catch (Exception e) {
			e.printStackTrace();
			map.put("resmsg", "입력실패");
			map.put("resdata", "0");
		}
		
		ResponseEntity<Map<String, Object>> res = new ResponseEntity<Map<String,Object>>(map,HttpStatus.OK);
		
		return res;
	}
	
	@PostMapping("/reviewboardwrite")
	public ResponseEntity<Map<String, Object>> reviewboardwrite(@RequestParam String userid , @RequestParam String subject, @RequestParam String content, @RequestParam String contentid, @RequestParam String starscore, @RequestParam(required = false) List<MultipartFile> multipartFile) throws IllegalStateException, IOException, SQLException {
		Map<String, Object> map = new HashMap<>();
		BoardDto dto=new BoardDto();
		dto.setUserid(userid);
		dto.setSubject(subject);
		dto.setContent(content);
		dto.setStarscore(starscore);
		dto.setContentid(contentid);
		
		try {
			service.reviewBoardWrite(dto);
			
			int articleno = service.reviewBoardLastArticleno();
			if(multipartFile != null) {
				for (MultipartFile files : multipartFile) {
					FileDto fileDto = new FileDto();
					fileDto.setArticleno(String.valueOf(articleno));
					fileDto.setFilename(files.getOriginalFilename());
					fileDto.setUrl(s3service.saveFile(files));
					service.reviewBoardFileWrite(fileDto);
				}
			}
			
			service.addscore(contentid,starscore);
			
			map.put("resmsg", "입력성공");
			map.put("resdata", "1");
		} catch (Exception e) {
			e.printStackTrace();
			map.put("resmsg", "입력실패");
			map.put("resdata", "0");
		}
		
		ResponseEntity<Map<String, Object>> res = new ResponseEntity<Map<String,Object>>(map,HttpStatus.OK);
		
		return res;
	}
	
	@GetMapping("/boardlist")
	public ResponseEntity<Map<String, Object>> boardlist(@RequestParam Map<String, String> map) throws Exception {
		//System.out.println(map);
		Map<String, Object> resultmap = new HashMap<>();
		try {
			Map<String, Object> result = service.boardList(map);
			resultmap.put("resdata", result);
			resultmap.put("resmsg", "조회성공");
		} catch (Exception e) { 
			e.printStackTrace();
			resultmap.put("resmsg", "조회실패");
			resultmap.put("resdata", "0");
		}
		
		ResponseEntity<Map<String, Object>> res = new ResponseEntity<Map<String,Object>>(resultmap,HttpStatus.OK);
		
		return res;
	}
	
	@GetMapping("/freeboardview")
	public ResponseEntity<Map<String, Object>> freeboardview(String articleno) throws SQLException {
		Map<String, Object> map = new HashMap<>();
		try {
			BoardDto dto = service.freeBoardView(articleno);
			service.freeBoardHit(articleno);
			map.put("resdata", dto);
			map.put("resmsg", "조회성공");
		} catch (Exception e) {
			e.printStackTrace();
			map.put("resmsg", "조회실패");
			map.put("resdata", "0");
		}
		
		ResponseEntity<Map<String, Object>> res = new ResponseEntity<Map<String,Object>>(map,HttpStatus.OK);
		
		return res;
		
	}
	
	@GetMapping("/reviewboardview")
	public ResponseEntity<Map<String, Object>> reviewboardview(String articleno) throws SQLException {
		Map<String, Object> map = new HashMap<>();
		try {
			BoardDto dto = service.reviewBoardView(articleno);
			service.reviewBoardHit(articleno);
			map.put("resdata", dto);
			map.put("resmsg", "조회성공");
		} catch (Exception e) {
			e.printStackTrace();
			map.put("resmsg", "조회실패");
			map.put("resdata", "0");
		}
		
		ResponseEntity<Map<String, Object>> res = new ResponseEntity<Map<String,Object>>(map,HttpStatus.OK);
		
		return res;
		
	}
	
	@PutMapping("/freeboardupdate")
	public ResponseEntity<Map<String, Object>> freeboardupdate(@RequestBody BoardDto dto) throws SQLException {
		Map<String, Object> map = new HashMap<>();
		try {
			service.freeBoardUpdate(dto);
			map.put("resdata", dto);
			map.put("resmsg", "수정성공");
		} catch (Exception e) {
			e.printStackTrace();
			map.put("resmsg", "수정실패");
			map.put("resdata", "0");
		}
		
		ResponseEntity<Map<String, Object>> res = new ResponseEntity<Map<String,Object>>(map,HttpStatus.OK);
		
		return res;
	}
	
	@PutMapping("/reviewboardupdate")
	public ResponseEntity<Map<String, Object>> reviewboardupdate(@RequestBody BoardDto dto) throws SQLException {
		Map<String, Object> map = new HashMap<>();
		try {
			service.reviewBoardUpdate(dto);
			map.put("resdata", dto);
			map.put("resmsg", "수정성공");
		} catch (Exception e) {
			e.printStackTrace();
			map.put("resmsg", "수정실패");
			map.put("resdata", "0");
		}
		
		ResponseEntity<Map<String, Object>> res = new ResponseEntity<Map<String,Object>>(map,HttpStatus.OK);
		
		return res;
	}
	
	@DeleteMapping("/freeboarddelete")
	public ResponseEntity<Map<String, Object>> freeboarddelete(String articleno) throws SQLException {
		Map<String, Object> map = new HashMap<>();
		try {
			service.freeBoardDelete(articleno);
			map.put("resdata", "1");
			map.put("resmsg", "삭제성공");
		} catch (Exception e) {
			e.printStackTrace();
			map.put("resmsg", "삭제실패");
			map.put("resdata", "0");
		}
		
		ResponseEntity<Map<String, Object>> res = new ResponseEntity<Map<String,Object>>(map,HttpStatus.OK);
		
		return res;
	}
	
	@DeleteMapping("/reviewboarddelete")
	public ResponseEntity<Map<String, Object>> reviewboarddelete(String articleno) throws SQLException {
		Map<String, Object> map = new HashMap<>();
		try {
			service.reviewBoardDelete(articleno);
			map.put("resdata", "1");
			map.put("resmsg", "삭제성공");
		} catch (Exception e) {
			e.printStackTrace();
			map.put("resmsg", "삭제실패");
			map.put("resdata", "0");
		}
		
		ResponseEntity<Map<String, Object>> res = new ResponseEntity<Map<String,Object>>(map,HttpStatus.OK);
		
		return res;
	}
	
	@GetMapping("/freecommentlist")
	public ResponseEntity<Map<String, Object>> freecommentlist(String articleno) throws Exception {
		Map<String, Object> map = new HashMap<>();
		try {
			Map<String,String> param = new HashMap<>();
			param.put("articleno", articleno);
			param.put("parentcomment", "0");
			List<CommentDto> list = new ArrayList<CommentDto>();
			boolean[] check = new boolean[service.freeMaxComment(articleno)+1];
			recursive(param,list,0,check);
			map.put("resdata", list);
			map.put("resmsg", "조회성공");
		} catch (Exception e) {
			e.printStackTrace();
			map.put("resmsg", "조회실패");
			map.put("resdata", "0");
		}
		
		ResponseEntity<Map<String, Object>> res = new ResponseEntity<Map<String,Object>>(map,HttpStatus.OK);
		
		return res;
	}
	
	public void recursive(Map<String,String> map, List<CommentDto> list, int index, boolean[] check) {
		if(list.size() == 0) {
			list.addAll(service.freeCommentList(map));
		}
		
		for (int i = index; i < list.size(); i++) {
			if(check[Integer.parseInt(list.get(i).getCommentno())]) {
				continue;
			}
			map.put("parentcomment",list.get(i).getCommentno());
			System.out.println(i);
			List<CommentDto> f = service.freeCommentList(map);
			check[Integer.parseInt(map.get("parentcomment"))] = true;
			if(f.size() > 0) {
				list.addAll(i+1,f);
				recursive(map,list,i+1,check);
			}
		}
	}
	
	
	@GetMapping("/reviewcommentlist")
	public ResponseEntity<Map<String, Object>> reviewcommentlist(String articleno) throws Exception {
		Map<String, Object> map = new HashMap<>();
		try {
			Map<String,String> param = new HashMap<>();
			param.put("articleno", articleno);
			param.put("parentcomment", "0");
			System.out.println(param);
			List<CommentDto> list = new ArrayList<CommentDto>();
			boolean[] check = new boolean[service.reviewMaxComment(articleno)+1];
			rrecursive(param,list,0,check);
			map.put("resdata", list);
			map.put("resmsg", "조회성공");
		} catch (Exception e) {
			e.printStackTrace();
			map.put("resmsg", "조회실패");
			map.put("resdata", "0");
		}
		
		ResponseEntity<Map<String, Object>> res = new ResponseEntity<Map<String,Object>>(map,HttpStatus.OK);
		
		return res;
	}
	
	public void rrecursive(Map<String,String> map, List<CommentDto> list, int index, boolean[] check) {
		if(list.size() == 0) {
			list.addAll(service.reviewCommentList(map));
		}
		
		for (int i = index; i < list.size(); i++) {
			if(check[Integer.parseInt(list.get(i).getCommentno())]) {
				continue;
			}
			map.put("parentcomment",list.get(i).getCommentno());
			System.out.println(i);
			List<CommentDto> f = service.reviewCommentList(map);
			check[Integer.parseInt(map.get("parentcomment"))] = true;
			if(f.size() > 0) {
				list.addAll(i+1,f);
				recursive(map,list,i+1,check);
			}
		}
	}
	
	@PostMapping("/freecommentwrite")
	public ResponseEntity<Map<String, Object>> freecommentwrite(@RequestBody CommentDto dto) throws IllegalStateException, IOException, SQLException {
		Map<String, Object> map = new HashMap<>();
		
		try {
			service.freeCommentWrite(dto);
			map.put("resmsg", "입력성공");
			map.put("resdata", "1");
		} catch (Exception e) {
			e.printStackTrace();
			map.put("resmsg", "입력실패");
			map.put("resdata", "0");
		}
		
		ResponseEntity<Map<String, Object>> res = new ResponseEntity<Map<String,Object>>(map,HttpStatus.OK);
		
		return res;
	}
	
	@PostMapping("/reviewcommentwrite")
	public ResponseEntity<Map<String, Object>> reviewcommentwrite(@RequestBody CommentDto dto) throws IllegalStateException, IOException, SQLException {
		Map<String, Object> map = new HashMap<>();
		
		try {
			service.reviewCommentWrite(dto);
			map.put("resmsg", "입력성공");
			map.put("resdata", "1");
		} catch (Exception e) {
			e.printStackTrace();
			map.put("resmsg", "입력실패");
			map.put("resdata", "0");
		}
		
		ResponseEntity<Map<String, Object>> res = new ResponseEntity<Map<String,Object>>(map,HttpStatus.OK);
		
		return res;
	}
	
	@DeleteMapping("/freecommentdelete")
	public ResponseEntity<Map<String, Object>> freecommentdelete(String commentno) throws SQLException {
		Map<String, Object> map = new HashMap<>();
		try {
			service.freeCommentDelete(commentno);
			map.put("resdata", "1");
			map.put("resmsg", "삭제성공");
		} catch (Exception e) {
			e.printStackTrace();
			map.put("resmsg", "삭제실패");
			map.put("resdata", "0");
		}
		
		ResponseEntity<Map<String, Object>> res = new ResponseEntity<Map<String,Object>>(map,HttpStatus.OK);
		
		return res;
	}
	
	@DeleteMapping("/reviewcommentdelete")
	public ResponseEntity<Map<String, Object>> reviewcommentdelete(String commentno) throws SQLException {
		Map<String, Object> map = new HashMap<>();
		try {
			service.reviewCommentDelete(commentno);
			map.put("resdata", "1");
			map.put("resmsg", "삭제성공");
		} catch (Exception e) {
			e.printStackTrace();
			map.put("resmsg", "삭제실패");
			map.put("resdata", "0");
		}
		
		ResponseEntity<Map<String, Object>> res = new ResponseEntity<Map<String,Object>>(map,HttpStatus.OK);
		
		return res;
	}
	
	@GetMapping("/freeboardfilelist")
	public ResponseEntity<Map<String, Object>> freeboardfilelist(String articleno) throws Exception {
		Map<String, Object> map = new HashMap<>();
		try {
			List<FileDto> list = service.freeBoardFileList(articleno);
			//System.out.println(list);
			map.put("resdata", list);
			map.put("resmsg", "조회성공");
		} catch (Exception e) {
			e.printStackTrace();
			map.put("resmsg", "조회실패");
			map.put("resdata", "0");
		}
		
		ResponseEntity<Map<String, Object>> res = new ResponseEntity<Map<String,Object>>(map,HttpStatus.OK);
		
		return res;
	}
	
	@GetMapping("/reviewboardfilelist")
	public ResponseEntity<Map<String, Object>> reviewboardfilelist(String articleno) throws Exception {
		Map<String, Object> map = new HashMap<>();
		try {
			List<FileDto> list = service.reviewBoardFileList(articleno);
			map.put("resdata", list);
			map.put("resmsg", "조회성공");
		} catch (Exception e) {
			e.printStackTrace();
			map.put("resmsg", "조회실패");
			map.put("resdata", "0");
		}
		
		ResponseEntity<Map<String, Object>> res = new ResponseEntity<Map<String,Object>>(map,HttpStatus.OK);
		
		return res;
	}
	
	@GetMapping("/freecommentcount")
	public ResponseEntity<Map<String, Object>> freecommentcount(String articleno) throws Exception {
		Map<String, Object> map = new HashMap<>();
		try {
			int count = service.freeCommentCount(articleno);
			map.put("resdata", count);
			map.put("resmsg", "조회성공");
		} catch (Exception e) {
			e.printStackTrace();
			map.put("resmsg", "조회실패");
			map.put("resdata", "0");
		}
		
		ResponseEntity<Map<String, Object>> res = new ResponseEntity<Map<String,Object>>(map,HttpStatus.OK);
		
		return res;
	}
	
	@GetMapping("/reviewcommentcount")
	public ResponseEntity<Map<String, Object>> reviewcommentcount(String articleno) throws Exception {
		Map<String, Object> map = new HashMap<>();
		try {
			int count = service.reviewCommentCount(articleno);
			map.put("resdata", count);
			map.put("resmsg", "조회성공");
		} catch (Exception e) {
			e.printStackTrace();
			map.put("resmsg", "조회실패");
			map.put("resdata", "0");
		}
		
		ResponseEntity<Map<String, Object>> res = new ResponseEntity<Map<String,Object>>(map,HttpStatus.OK);
		
		return res;
	}
	
	@GetMapping("/freecommentchildren")
	public ResponseEntity<Map<String, Object>> freeCommentChildren(String articleno) throws Exception {
		Map<String, Object> map = new HashMap<>();
		try {
			List<CommentDto> comments = service.freeCommentChildren(articleno);
			map.put("resdata", comments);
			map.put("resmsg", "조회성공");
		} catch (Exception e) {
			e.printStackTrace();
			map.put("resmsg", "조회실패");
			map.put("resdata", "0");
		}
		
		ResponseEntity<Map<String, Object>> res = new ResponseEntity<Map<String,Object>>(map,HttpStatus.OK);
		
		return res;
	}
	
	@GetMapping("/reviewcommentchildren")
	public ResponseEntity<Map<String, Object>> reviewCommentChildren(String articleno) throws Exception {
		Map<String, Object> map = new HashMap<>();
		try {
			List<CommentDto> comments = service.reviewCommentChildren(articleno);
			map.put("resdata", comments);
			map.put("resmsg", "조회성공");
		} catch (Exception e) {
			e.printStackTrace();
			map.put("resmsg", "조회실패");
			map.put("resdata", "0");
		}
		
		ResponseEntity<Map<String, Object>> res = new ResponseEntity<Map<String,Object>>(map,HttpStatus.OK);
		
		return res;
	}
	
	@PostMapping("/freecommentchildwrite")
	public ResponseEntity<Map<String, Object>> freecommentchildwrite(@RequestBody CommentDto dto) throws SQLException {
		Map<String, Object> map = new HashMap<>();
		try {
			System.out.println(dto);
			service.freeCommentChildWrite(dto);
			map.put("resdata", "추가성공");
			map.put("resmsg", "1");
		} catch (Exception e) {
			e.printStackTrace();
			map.put("resmsg", "추가실패");
			map.put("resdata", "0");
		}
		ResponseEntity<Map<String, Object>> res = new ResponseEntity<Map<String,Object>>(map,HttpStatus.OK);
		
		return res;
	}
	
	@PostMapping("/reviewcommentchildwrte")
	public ResponseEntity<Map<String, Object>> reviewcommentchildwrite(@RequestBody CommentDto dto) throws SQLException {
		Map<String, Object> map = new HashMap<>();
		try {
			service.reviewCommentChildWrite(dto);
			map.put("resdata", "추가성공");
			map.put("resmsg", "1");
		} catch (Exception e) {
			e.printStackTrace();
			map.put("resmsg", "추가실패");
			map.put("resdata", "0");
		}
		ResponseEntity<Map<String, Object>> res = new ResponseEntity<Map<String,Object>>(map,HttpStatus.OK);
		
		return res;
	}
	
	
	
}
