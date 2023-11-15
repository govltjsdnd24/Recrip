package com.ssafy.recrip.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.ssafy.recrip.model.BoardDto;
import com.ssafy.recrip.model.CommentDto;
import com.ssafy.recrip.model.FileDto;
import com.ssafy.recrip.model.AttractionDto;
import com.ssafy.recrip.util.PageNavigation;


public interface BoardService {
	Map<String,Object> boardList(Map<String, String> map) throws Exception;
	int freeBoardWrite(BoardDto dto);
	int reviewBoardWrite(BoardDto dto);
	int freeBoardDelete(String articleno);
	int reviewBoardDelete(String articleno);
	int freeBoardUpdate(BoardDto dto);
	int reviewBoardUpdate(BoardDto dto);
	BoardDto freeBoardView(String articleno);
	BoardDto reviewBoardView(String articleno);
	List<CommentDto> freeCommentList(String articleno);
	List<CommentDto> reviewCommentList(String articleno);
	int freeCommentWrite(CommentDto dto);
	int reviewCommentWrite(CommentDto dto);
	int freeCommentDelete(String commentno);
	int reviewCommentDelete(String commentno);
	List<FileDto> freeBoardFileList(String articleno);
	List<FileDto> reviewBoardFileList(String articleno);
	int freeBoardFileWrite(FileDto dto);
	int reviewBoardFileWrite(FileDto dto);
}
