package kr.member.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.controller.Action;
import kr.member.OMemberDAO;
import kr.member.OMemberVO;

public class LoginAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//전송된 데이터 인코딩 처리
		request.setCharacterEncoding("utf-8");
				
		//전송된 데이터 반환
		String id = request.getParameter("id");
		String passwd = request.getParameter("passwd");
				
		OMemberDAO dao = OMemberDAO.getInstance();
		OMemberVO member = dao.checkMember(id); //id데이터를 담음
		boolean check = false;
				
		if(member!=null) { //id존재
			//비밀번호 일치 여부 체크
			check = member.isCheckedPassword(passwd);
					
			//로그인 실패시 auth 체크용
			request.setAttribute("auth", member.getMem_auth());
		}
				
		if(check) {//인증 성공(check값==true)
			//로그인 성공 처리
			HttpSession session = request.getSession(); //세션객체 생성
			session.setAttribute("user_num", member.getMem_num());
			session.setAttribute("user_id", member.getMem_id());
			session.setAttribute("user_auth", member.getMem_auth());
			session.setAttribute("user_photo", member.getMem_photo()); //사진은 null값 인정됨
					
			//인증 성공시 호출
			//리다이렉트 방식으로 /main/main.do 호출
			return "redirect:/main/main.do";
		}
		//인증 실패시 호출, forward방식으로
		return "/WEB-INF/views/member/login.jsp";
	}
}