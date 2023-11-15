package com.ssafy.enjoytrip.service;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.enjoytrip.mapper.BoardMapper;
import com.ssafy.enjoytrip.model.BoardDto;
import com.ssafy.enjoytrip.model.CommentDto;
import com.ssafy.enjoytrip.model.EnjoyBoardDto;
import com.ssafy.enjoytrip.util.PageNavigation;
import com.ssafy.enjoytrip.util.SizeConstant;



@Service
public class BoardServiceImpl implements BoardService {

	SqlSession session;
	
	@Autowired
	public BoardServiceImpl(SqlSession session) {
		super();
		this.session = session;
	}

	@Override
	public Map<String,Object> boardlist(Map<String, String> map) throws Exception {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("word", map.get("word") == null ? "" : map.get("word"));
		int currentPage = Integer.parseInt(map.get("pgno") == null ? "1" : map.get("pgno"));
		int sizePerPage = Integer.parseInt(map.get("spp") == null ? "20" : map.get("spp"));
		int start = currentPage * sizePerPage - sizePerPage;
		param.put("start", start);
		param.put("listsize", sizePerPage);

		String key = map.get("key");
		param.put("key", key == null ? "" : key);
		System.out.println(param);
		List<BoardDto> list = session.getMapper(BoardMapper.class).listArticle(param);

		int totalArticleCount = session.getMapper(BoardMapper.class).getTotalArticleCount(param);
		int totalPageCount = (totalArticleCount - 1) / sizePerPage + 1;
		Map<String, Object> resultmap = new HashMap<String, Object>();
		
		resultmap.put("list", list);
		resultmap.put("totalpage", totalPageCount);
		return resultmap;
	}
	
	@Override
	public List<BoardDto> boardlist(int currentpage) {
		// TODO Auto-generated method stub
		return session.getMapper(BoardMapper.class).boardlist(currentpage);
	}

	@Override
	public void boardwrite(BoardDto dto) {
		// TODO Auto-generated method stub
		session.getMapper(BoardMapper.class).boardwrite(dto);
	}

	@Override
	public void boarddelete(String articleno) {
		// TODO Auto-generated method stub
		session.getMapper(BoardMapper.class).boarddelete(articleno);
	}

	@Override
	public void boardupdate(BoardDto dto) {
		// TODO Auto-generated method stub
		session.getMapper(BoardMapper.class).boardupdate(dto);
	}

	@Override
	public BoardDto boardview(String articleno) {
		// TODO Auto-generated method stub
		return session.getMapper(BoardMapper.class).boardview(articleno);
	}

	@Override
	public List<EnjoyBoardDto> enjoyboardlist(Map<String, String> map) {
		// TODO Auto-generated method stub
		return session.getMapper(BoardMapper.class).enjoyboardlist(map);
	}

	@Override
	public List<CommentDto> commentlist(String articleno) {
		// TODO Auto-generated method stub
		return session.getMapper(BoardMapper.class).commentlist(articleno);
	}

	@Override
	public void commentwrite(CommentDto dto) {
		// TODO Auto-generated method stub
		session.getMapper(BoardMapper.class).commentwrite(dto);
	}

	@Override
	public void commentdelete(String commentno) {
		// TODO Auto-generated method stub
		session.getMapper(BoardMapper.class).commentdelete(commentno);
	}

	@Override
	public PageNavigation makePageNavigation(int pgno) throws Exception {
		PageNavigation pageNavigation = new PageNavigation();

		int naviSize = SizeConstant.NAVIGATION_SIZE;
		int sizePerPage = SizeConstant.LIST_SIZE;
		int currentPage = pgno;

		pageNavigation.setCurrentPage(currentPage);
		pageNavigation.setNaviSize(naviSize);
		int totalCount = session.getMapper(BoardMapper.class).getTotalArticleCount();
		pageNavigation.setTotalCount(totalCount);
		int totalPageCount = (totalCount - 1) / sizePerPage + 1;
		pageNavigation.setTotalPageCount(totalPageCount);
		boolean startRange = currentPage <= naviSize;
		pageNavigation.setStartRange(startRange);
		boolean endRange = (totalPageCount - 1) / naviSize * naviSize < currentPage;
		pageNavigation.setEndRange(endRange);
		pageNavigation.makeNavigator();

		return pageNavigation;
	}

	@Override
	public int getTotalPage() throws SQLException {
		// TODO Auto-generated method stub
		return session.getMapper(BoardMapper.class).getTotalArticleCount();
	}

	
	
	
}
