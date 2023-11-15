package com.ssafy.enjoytrip.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.ssafy.enjoytrip.model.BoardDto;
import com.ssafy.enjoytrip.model.CommentDto;
import com.ssafy.enjoytrip.model.EnjoyBoardDto;
import com.ssafy.enjoytrip.util.PageNavigation;


public interface BoardService {
	List<BoardDto> boardlist(int currentpage);
	void boardwrite(BoardDto dto);
	void boarddelete(String articleno);
	void boardupdate(BoardDto dto);
	BoardDto boardview(String articleno);
	List<EnjoyBoardDto> enjoyboardlist(Map<String,String> map);
	List<CommentDto> commentlist(String articleno);
	void commentwrite(CommentDto dto);
	void commentdelete(String commentno);
	PageNavigation makePageNavigation(int pgno) throws Exception;
	int getTotalPage() throws SQLException;
	Map<String,Object> boardlist(Map<String, String> map) throws Exception;
}
