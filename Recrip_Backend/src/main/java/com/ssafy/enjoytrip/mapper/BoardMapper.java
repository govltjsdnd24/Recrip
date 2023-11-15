package com.ssafy.enjoytrip.mapper;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.enjoytrip.model.BoardDto;
import com.ssafy.enjoytrip.model.CommentDto;
import com.ssafy.enjoytrip.model.EnjoyBoardDto;

@Mapper
public interface BoardMapper {
	
	List<BoardDto> boardlist(int currentpage);
	void boardwrite(BoardDto dto);
	void boarddelete(String articleno);
	void boardupdate(BoardDto dto);
	BoardDto boardview(String articleno);
	List<EnjoyBoardDto> enjoyboardlist(Map<String,String> map);
	List<CommentDto> commentlist(String articleno);
	void commentwrite(CommentDto dto);
	void commentdelete(String commentno);
	int getTotalArticleCount() throws SQLException;
	List<BoardDto> listArticle(Map<String, Object> param) throws SQLException;
	int getTotalArticleCount(Map<String, Object> param) throws SQLException;
}
