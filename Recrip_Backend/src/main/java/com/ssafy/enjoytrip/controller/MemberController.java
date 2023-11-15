package com.ssafy.enjoytrip.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.ssafy.enjoytrip.model.MemberDto;
import com.ssafy.enjoytrip.service.MemberService;

import springfox.documentation.annotations.ApiIgnore;

@CrossOrigin("*")
@ApiIgnore
@Controller
public class MemberController {
	
	private MemberService service;
	
	@Autowired
	public MemberController(MemberService service) {
		this.service = service;
	}
	
	@PostMapping("/login")
	public ModelAndView login(MemberDto dto, ModelAndView mv, HttpServletRequest req) {
		try {
			MemberDto login = service.login(dto);
			if(login != null) {
				req.getSession().setAttribute("userinfo", login);
				mv.setViewName("index");
			}else {
				mv.setViewName("index");
			}
		}catch(Exception e) {
			e.printStackTrace();
			mv.setViewName("error");
		}
		return mv;
	}

	@GetMapping("logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "index";
	}
	
	@PostMapping("/meminsert")
	public ModelAndView MemberInsert(MemberDto dto, ModelAndView mav) {
		try {
			int res = service.memberInsert(dto);
			mav.setViewName("index");
		} catch (Exception e) {
			e.printStackTrace();
			mav.setViewName("error");
		}
		return mav;
	};

	@PostMapping("/memupdate")
	public ModelAndView MemberUpdate(@ModelAttribute() MemberDto dto, ModelAndView mav,HttpServletRequest req) {
		try {
			int res = service.memberUpdate(dto);
			req.getSession().setAttribute("userinfo", dto);
			mav.setViewName("index");
		} catch (Exception e) {
			e.printStackTrace();
			mav.setViewName("error");
		}
		return mav;
	};
	
	@GetMapping("/memdelete")
	public ModelAndView MemberDelete(@RequestParam String userid, ModelAndView mav, HttpSession session) {
		try {
			int res = service.memberDelete(userid);
			logout(session);
			mav.setViewName("index");
		} catch (Exception e) {
			e.printStackTrace();
			mav.setViewName("error");
		}
		return mav;
	};


}
