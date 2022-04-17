package kr.member.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.controller.Action;

public class ModifyPasswordFormAction implements Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//회원제 서비스이므로 회원 로그인 체크
		HttpSession session = request.getSession();
		Integer user_num = (Integer)session.getAttribute("user_num");
		
		//로그인이 안 된 경우
		if(user_num==null) { //로그인이 안됀 경우
//			return "redirect:/member/loginForm.do";
		}
		
		//로그인이 됬을 때 (받을 데이터 없음)
		
		//JSP경로 반환
		return "/WEB-INF/views/member/modifyPasswordForm.jsp";
	}
	
}

