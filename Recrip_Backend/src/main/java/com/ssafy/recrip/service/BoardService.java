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
	List<CommentDto> freeCommentList(Map<String,String> map);
	List<CommentDto> reviewCommentList(Map<String,String> map);
	int freeCommentWrite(CommentDto dto);
	int reviewCommentWrite(CommentDto dto);
	int freeCommentDelete(String commentno);
	int reviewCommentDelete(String commentno);
	List<FileDto> freeBoardFileList(String articleno);
	List<FileDto> reviewBoardFileList(String articleno);
	int freeBoardFileWrite(FileDto dto);
	int reviewBoardFileWrite(FileDto dto);
	int freeBoardLastArticleno();
	int reviewBoardLastArticleno();
	void addscore(String contentid, String starscore);
	int freeBoardHit(String articleno);
	int reviewBoardHit(String articleno);
	int freeBoardLikeCheck(String articleno, String userid);
	int freeBoardLike(String articleno);
	int reviewBoardLikeCheck(String articleno,String userid);
	int reviewBoardLike(String articleno);
	int freeBoardLikeAdd(String articleno, String userid);
	int reviewBoardLikeAdd(String articleno, String userid);
	int freeBoardLikeCount(String articleno);
	int reviewBoardLikeCount(String articleno);
	List<BoardDto> reviewBoardMostLikes();
	int freeCommentCount(String articleno);
	int reviewCommentCount(String articleno);
	List<CommentDto> freeCommentChildren(String articleno);
	List<CommentDto> reviewCommentChildren(String articleno);
	int freeCommentChildWrite(CommentDto dto);
	int reviewCommentChildWrite(CommentDto dto);
	int freeMaxComment(String articleno);
	int reviewMaxComment(String articleno);
	int reviewBoardAttrWrite(Map<String,Object> param);
	List<Map<String,Object>> reviewBoardAttrList(String articleno);
	String profilePicture(String userid);
}
