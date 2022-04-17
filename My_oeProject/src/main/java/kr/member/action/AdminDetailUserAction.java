package kr.member.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.controller.Action;
import kr.member.OMemberDAO;
import kr.member.OMemberVO;

public class AdminDetailUserAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		HttpSession session = request.getSession();
		Integer user_num = (Integer)session.getAttribute("user_num");
		if(user_num == null) {//로그인이 되지 않은 경우
			return "redirect:/member/loginForm.do";
		}
		
		Integer user_auth = (Integer)session.getAttribute("user_auth");
		if(user_auth < 2) {//관리자로 로그인하지 않은 경우
			return "/WEB-INF/views/common/notice.jsp";
		}
		//관리자로 로그인한 경우
		//전송된 데이터 인코딩 처리
		request.setCharacterEncoding("utf-8");
		
		OMemberVO member = new OMemberVO();
		member.setMem_num(Integer.parseInt(request.getParameter("mem_num")));
		member.setMem_auth(Integer.parseInt(request.getParameter("auth")));
		member.setMem_nick(request.getParameter("name"));
		member.setMem_phone(request.getParameter("phone"));
		member.setMem_email(request.getParameter("email"));
		member.setMem_zipcode(request.getParameter("zipcode"));
		member.setMem_addr(request.getParameter("address1"));
		member.setMem_addr2(request.getParameter("address2"));
		
		OMemberDAO dao = OMemberDAO.getInstance();
		dao.updateMemberByAdmin(member);
		
		return "/WEB-INF/views/member/adminDetailUser.jsp";
	}

}
