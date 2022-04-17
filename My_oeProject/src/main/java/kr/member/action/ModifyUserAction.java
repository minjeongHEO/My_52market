package kr.member.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.controller.Action;
import kr.member.OMemberDAO;
import kr.member.OMemberVO;

public class ModifyUserAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		HttpSession session = request.getSession();
		Integer user_num = (Integer)session.getAttribute("user_num");
		if(user_num == null) {//로그인 되지 않은 경우
			return "redirect:/member/loginForm.do";
		}
		//로그인 된 경우
		//전송된 데이터 인코딩 처리
		request.setCharacterEncoding("utf-8");
		
		//자바빈 생성
		OMemberVO member = new OMemberVO();
		member.setMem_num(user_num);
		member.setMem_nick(request.getParameter("name"));
		member.setMem_phone(request.getParameter("phone"));
		member.setMem_email(request.getParameter("email"));
		member.setMem_zipcode(request.getParameter("zipcode"));
		member.setMem_addr(request.getParameter("address1"));
		member.setMem_addr2(request.getParameter("address2"));
		
		OMemberDAO dao = OMemberDAO.getInstance();
		dao.updateMember(member);
		
		return "/WEB-INF/views/member/modifyUser.jsp";
	}

}
