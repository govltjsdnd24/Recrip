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
	
}
