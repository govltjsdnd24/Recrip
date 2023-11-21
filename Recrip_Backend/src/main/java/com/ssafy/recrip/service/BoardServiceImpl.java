package com.ssafy.recrip.service;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.recrip.mapper.BoardMapper;
import com.ssafy.recrip.model.BoardDto;
import com.ssafy.recrip.model.CommentDto;
import com.ssafy.recrip.model.FileDto;
import com.ssafy.recrip.model.AttractionDto;
import com.ssafy.recrip.util.PageNavigation;
import com.ssafy.recrip.util.SizeConstant;



@Service
public class BoardServiceImpl implements BoardService {

	SqlSession session;
	
	@Autowired
	public BoardServiceImpl(SqlSession session) {
		super();
		this.session = session;
	}

	@Override
	public Map<String,Object> boardList(Map<String, String> map) throws Exception {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("word", map.get("word") == null ? "" : map.get("word"));
		int currentPage = Integer.parseInt(map.get("pgno") == null ? "1" : map.get("pgno"));
		int sizePerPage = Integer.parseInt(map.get("spp") == null ? "20" : map.get("spp"));
		int start = currentPage * sizePerPage - sizePerPage;
		param.put("start", start);
		param.put("listsize", sizePerPage);
		param.put("table", map.get("table"));
		
		String key = map.get("key");
		param.put("key", key == null ? "" : key);
		System.out.println(param);
		List<BoardDto> list = session.getMapper(BoardMapper.class).boardList(param);

		int totalArticleCount = session.getMapper(BoardMapper.class).getTotalListCount(param);
		int totalPageCount = (totalArticleCount - 1) / sizePerPage + 1;
		Map<String, Object> resultmap = new HashMap<String, Object>();
		
		resultmap.put("list", list);
		resultmap.put("totalpage", totalPageCount);
		return resultmap;
	}

	@Override
	public int freeBoardWrite(BoardDto dto) {
		// TODO Auto-generated method stub
		return session.getMapper(BoardMapper.class).freeBoardWrite(dto);
	}

	@Override
	public int reviewBoardWrite(BoardDto dto) {
		// TODO Auto-generated method stub
		return session.getMapper(BoardMapper.class).reviewBoardWrite(dto);
	}

	@Override
	public int freeBoardDelete(String articleno) {
		// TODO Auto-generated method stub
		return session.getMapper(BoardMapper.class).freeBoardDelete(articleno);
	}

	@Override
	public int reviewBoardDelete(String articleno) {
		// TODO Auto-generated method stub
		return session.getMapper(BoardMapper.class).reviewBoardDelete(articleno);
	}

	@Override
	public int freeBoardUpdate(BoardDto dto) {
		// TODO Auto-generated method stub
		return session.getMapper(BoardMapper.class).freeBoardUpdate(dto);
	}

	@Override
	public int reviewBoardUpdate(BoardDto dto) {
		// TODO Auto-generated method stub
		return session.getMapper(BoardMapper.class).reviewBoardUpdate(dto);
	}

	@Override
	public BoardDto freeBoardView(String articleno) {
		// TODO Auto-generated method stub
		return session.getMapper(BoardMapper.class).freeBoardView(articleno);
	}

	@Override
	public BoardDto reviewBoardView(String articleno) {
		// TODO Auto-generated method stub
		return session.getMapper(BoardMapper.class).reviewBoardView(articleno);
	}

	@Override
	public List<CommentDto> freeCommentList(String articleno) {
		// TODO Auto-generated method stub
		return session.getMapper(BoardMapper.class).freeCommentList(articleno);
	}

	@Override
	public List<CommentDto> reviewCommentList(String articleno) {
		// TODO Auto-generated method stub
		return session.getMapper(BoardMapper.class).reviewCommentList(articleno);
	}

	@Override
	public int freeCommentWrite(CommentDto dto) {
		// TODO Auto-generated method stub
		return session.getMapper(BoardMapper.class).freeCommentWrite(dto);
	}

	@Override
	public int reviewCommentWrite(CommentDto dto) {
		// TODO Auto-generated method stub
		return session.getMapper(BoardMapper.class).reviewCommentWrite(dto);
	}

	@Override
	public int freeCommentDelete(String commentno) {
		// TODO Auto-generated method stub
		return session.getMapper(BoardMapper.class).freeCommentDelete(commentno);
	}

	@Override
	public int reviewCommentDelete(String commentno) {
		// TODO Auto-generated method stub
		return session.getMapper(BoardMapper.class).reviewCommentDelete(commentno);
	}

	@Override
	public List<FileDto> freeBoardFileList(String articleno) {
		// TODO Auto-generated method stub
		return session.getMapper(BoardMapper.class).freeBoardFileList(articleno);
	}

	@Override
	public List<FileDto> reviewBoardFileList(String articleno) {
		// TODO Auto-generated method stub
		return session.getMapper(BoardMapper.class).reviewBoardFileList(articleno);
	}

	@Override
	public int freeBoardFileWrite(FileDto dto) {
		// TODO Auto-generated method stub
		return session.getMapper(BoardMapper.class).freeBoardFileWrite(dto);
	}

	@Override
	public int reviewBoardFileWrite(FileDto dto) {
		// TODO Auto-generated method stub
		return session.getMapper(BoardMapper.class).reviewBoardFileWrite(dto);
	}

	@Override
	public int freeBoardLastArticleno() {
		// TODO Auto-generated method stub
		return session.getMapper(BoardMapper.class).freeBoardLastArticleno();
	}

	@Override
	public int reviewBoardLastArticleno() {
		return session.getMapper(BoardMapper.class).reviewBoardLastArticleno();
	}

	@Override
	public void addscore(String contentid, String starscore) {
		Map<String,String> map = new HashMap<>();
		map.put("content_id", contentid);
		map.put("starscore", String.valueOf(Double.parseDouble(starscore) * 4));
		
		Map<String, Object> result = session.getMapper(BoardMapper.class).findscore(contentid);
		System.out.println(result);
		
		map.put("rating", String.valueOf(Integer.parseInt(String.valueOf(result.get("rating")))+Double.parseDouble(map.get("starscore"))));
		map.put("count", String.valueOf(Integer.parseInt(String.valueOf(result.get("count"))+1)));
		map.put("score", String.valueOf(Double.parseDouble(starscore)*10));
		
		System.out.println(map);
		session.getMapper(BoardMapper.class).addscore(map);
	}

	@Override
	public int freeBoardHit(String articleno) {
		return session.getMapper(BoardMapper.class).freeBoardHit(articleno);
		
	}

	@Override
	public int reviewBoardHit(String articleno) {
		return session.getMapper(BoardMapper.class).reviewBoardHit(articleno);
	}

	@Override
	public int freeBoardLikeCheck(String articleno,String userid) {
		Integer result=session.getMapper(BoardMapper.class).freeBoardLikeCheck(articleno,userid);
		return result!=null?result.intValue():0;
	}

	@Override
	public int freeBoardLike(String articleno) {
		return session.getMapper(BoardMapper.class).freeBoardLike(articleno);
	}

	@Override
	public int reviewBoardLikeCheck(String articleno,String userid) {
		Integer result=session.getMapper(BoardMapper.class).reviewBoardLikeCheck(articleno,userid);
		return result!=null?result.intValue():0;
	}

	@Override
	public int reviewBoardLike(String articleno) {
		return session.getMapper(BoardMapper.class).reviewBoardLike(articleno);
	}

	@Override
	public int freeBoardLikeAdd(String articleno, String userid) {
		return session.getMapper(BoardMapper.class).freeBoardLikeAdd(articleno,userid);
	}

	@Override
	public int reviewBoardLikeAdd(String articleno, String userid) {
		return session.getMapper(BoardMapper.class).reviewBoardLikeAdd(articleno,userid);
	}

	@Override
	public int freeBoardLikeCount(String articleno) {
		return session.getMapper(BoardMapper.class).freeBoardLikeCount(articleno);
	}

	@Override
	public int reviewBoardLikeCount(String articleno) {
		return session.getMapper(BoardMapper.class).reviewBoardLikeCount(articleno);
	}

	@Override
	public List<BoardDto> reviewBoardMostLikes() {
		return session.getMapper(BoardMapper.class).reviewBoardMostLikes();
	}
	
}
