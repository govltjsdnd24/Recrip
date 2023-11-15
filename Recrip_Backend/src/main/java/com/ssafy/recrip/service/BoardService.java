package com.ssafy.recrip.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.ssafy.recrip.model.BoardDto;
import com.ssafy.recrip.model.CommentDto;
import com.ssafy.recrip.model.AttractionDto;
import com.ssafy.recrip.util.PageNavigation;


public interface BoardService {
	Map<String,Object> boardList(Map<String, String> map) throws Exception;
}
