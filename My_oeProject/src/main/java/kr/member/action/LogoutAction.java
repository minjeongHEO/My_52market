package kr.member.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.controller.Action;

public class LogoutAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//세션을 구함
		HttpSession session = request.getSession();
		
		//세션의 모든 속성 제거(로그아웃 처리)
		session.invalidate();
				
		//리다이렉트 방식으로 /main/main.do호출
		return "redirect:/main/main.do";
	}
  
}
