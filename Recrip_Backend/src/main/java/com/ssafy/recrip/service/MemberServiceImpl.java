package com.ssafy.recrip.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.recrip.mapper.MemberMapper;
import com.ssafy.recrip.model.AttractionDto;
import com.ssafy.recrip.model.CourseDto;
import com.ssafy.recrip.model.MemberDto;
import com.ssafy.recrip.model.MessageDto;
import com.ssafy.recrip.model.WishHisDto;

@Service
public class MemberServiceImpl implements MemberService{
	
	private SqlSession session;
	
	@Autowired
	public MemberServiceImpl(SqlSession session) {
		super();
		this.session = session;
	}

	@Override
	public MemberDto login(MemberDto dto) {
		// TODO Auto-generated method stub
		return session.getMapper(MemberMapper.class).login(dto);
	}
	
	@Override
	public List<MemberDto> selectDeleted() {
		// TODO Auto-generated method stub
		return session.getMapper(MemberMapper.class).selectDeleted();
	}

	@Override
	public int memberInsert(MemberDto dto) {
		// TODO Auto-generated method stub
		return session.getMapper(MemberMapper.class).memberInsert(dto);
	}

	@Override
	public int memberUpdate(MemberDto dto) {
		// TODO Auto-generated method stub
		return session.getMapper(MemberMapper.class).memberUpdate(dto);
	}

	@Override
	public int memberDelete(String userid) {
		// TODO Auto-generated method stub
		return session.getMapper(MemberMapper.class).memberDelete(userid);
	}
	
	@Override
	public int memberPermaDelete(String userid) {
		// TODO Auto-generated method stub
		return session.getMapper(MemberMapper.class).memberPermaDelete(userid);
	}

	@Override
	public List<WishHisDto> historyList(Map<String,Object> param) {
		// TODO Auto-generated method stub
		int current = (Integer.parseInt((String) param.get("current")) - 1) * Integer.parseInt((String) param.get("size"));
		param.put("current", current);
		return session.getMapper(MemberMapper.class).historyList(param);
	}

	@Override
	public List<WishHisDto> wishList(Map<String,Object> param) {
		// TODO Auto-generated method stub
		int current = (Integer.parseInt((String) param.get("current")) - 1) * Integer.parseInt((String) param.get("size"));
		param.put("current", current);
		return session.getMapper(MemberMapper.class).wishList(param);
	}

	@Override
	public List<CourseDto> courseList(Map<String,Object> param) {
		// TODO Auto-generated method stub
		int total = session.getMapper(MemberMapper.class).getCourseCount((String)param.get("userid"));
		int current = (total - (Integer.parseInt((String) param.get("current")) - 1) * Integer.parseInt((String) param.get("size")));
		int size = (total - (Integer.parseInt((String) param.get("current"))) * Integer.parseInt((String) param.get("size")) + 1);
		param.put("current", current);
		param.put("size", size);
		System.out.println("asdasdsadasd"+param);
		return session.getMapper(MemberMapper.class).courseList(param);
	}

	@Override
	public int histroyInsert(WishHisDto dto) {
		// TODO Auto-generated method stub
		return session.getMapper(MemberMapper.class).histroyInsert(dto);
	}

	@Override
	public int wishInsert(WishHisDto dto) {
		// TODO Auto-generated method stub
		return session.getMapper(MemberMapper.class).wishInsert(dto);
	}

	@Override
	public int courseInsert(List<CourseDto> dto) {
		// TODO Auto-generated method stub
		try {
			String groupno = session.getMapper(MemberMapper.class).findMaxGroupno();
			if(groupno == null) {
				for (CourseDto courseDto : dto) {
					courseDto.setGroupno("1");
				}
			} else {
				for (CourseDto courseDto : dto) {
					courseDto.setGroupno(String.valueOf(Integer.parseInt(groupno)+1));
				}
			}
			for (CourseDto courseDto : dto) {
				System.out.println(courseDto);
				session.getMapper(MemberMapper.class).courseInsert(courseDto);
			}
			
			return 1;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return 0;
		}
	}

	@Override
	public int historydelete(String contentid) {
		// TODO Auto-generated method stub
		return session.getMapper(MemberMapper.class).historydelete(contentid);
	}

	@Override
	public int wishdelete(String contentid) {
		// TODO Auto-generated method stub
		return session.getMapper(MemberMapper.class).wishdelete(contentid);
	}

	@Override
	public int coursedelete(String groupno) {
		// TODO Auto-generated method stub
		return session.getMapper(MemberMapper.class).coursedelete(groupno);
	}

	@Override
	public List<MessageDto> messageList(String userid) {
		// TODO Auto-generated method stub
		return session.getMapper(MemberMapper.class).messageList(userid);
	}

	@Override
	public int messageinsert(MessageDto dto) {
		// TODO Auto-generated method stub
		return session.getMapper(MemberMapper.class).messageinsert(dto);
	}

	@Override
	public int messagedelete(String messageno) {
		// TODO Auto-generated method stub
		return session.getMapper(MemberMapper.class).messagedelete(messageno);
	}

	@Override
	public int getTotalCount(Map<String, Object> param) {
		// TODO Auto-generated method stub
		return session.getMapper(MemberMapper.class).getTotalCount(param);
	}

	@Override
	public AttractionDto getAttrInfo(String contentid) {
		// TODO Auto-generated method stub
		return session.getMapper(MemberMapper.class).getAttrInfo(contentid);
	}

	@Override
	public int getCourseCount(String userid) {
		// TODO Auto-generated method stub
		return session.getMapper(MemberMapper.class).getCourseCount(userid);
	}

	@Override
	public List<Integer> courseReview(Map<String, Object> param) {
		// TODO Auto-generated method stub
		return session.getMapper(MemberMapper.class).courseReview(param);
	}

	@Override
	public List<Integer> getcourse(String groupno) {
		// TODO Auto-generated method stub
		return session.getMapper(MemberMapper.class).getcourse(groupno);
	}

}
