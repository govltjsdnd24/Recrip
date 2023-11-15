package com.ssafy.recrip.controller;

import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.junit.Ignore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ssafy.recrip.model.BoardDto;
import com.ssafy.recrip.model.CommentDto;
import com.ssafy.recrip.service.BoardService;
import com.ssafy.recrip.util.PageNavigation;

import springfox.documentation.annotations.ApiIgnore;

@ApiIgnore
@Controller
public class MainController {
	
	BoardService service;
	
	@Autowired
	public MainController(BoardService service) {
		super();
		this.service = service;
	}

	@GetMapping("/")
	public String index() {
		return "index";
	}
	
	@GetMapping("/map")
	public String map() {
		return "map";
	}
	
	@GetMapping("/board")
	public String board() {
		return "board";
	}
	
	@PostMapping("/board")
	public String boardp() {
		return "board";
	}
	
	@GetMapping("/view")
	public String view(HttpSession session, String articleno) {
		BoardDto dto = service.boardview(articleno);
		session.setAttribute("view", dto);
		
		List<CommentDto> list = service.commentlist(articleno);
		session.setAttribute("commentlist", list);
		return "view";
	}
	
	@GetMapping("/makenav")
	public void makenav(HttpServletResponse response, String pgno) throws NumberFormatException, Exception {
		response.setContentType("plain/text; charset=utf-8;");
		response.getWriter().write(service.makePageNavigation(Integer.parseInt(pgno)).getNavigator());
	}
	
	@GetMapping("/modify")
	public String modify() {
		return "modify";
	}
	
	@GetMapping("/write")
	public String writeform() {
		return "write";
	}
}
