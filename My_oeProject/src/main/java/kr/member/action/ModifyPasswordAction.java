package kr.member.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.controller.Action;
import kr.member.OMemberDAO;
import kr.member.OMemberVO;

public class ModifyPasswordAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//회원제 서비스이기 때문에 로그인 조건체크를 해준다.
		 HttpSession session = request.getSession();
		 Integer user_num = (Integer)session.getAttribute("user_num");

		 	//로그인이 안된 경우
		 if(user_num == null) { 
//			 return "redirect:/member/loginForm.do";
			 return "redirect:/member/loginForm.do";
		 }
		 
		 //로그인이 된 경우
		 //전송된 데이터 인코딩 처리
		 request.setCharacterEncoding("utf-8"); 
		 
		 //전송된 데이터 반환
		 String id = request.getParameter("id"); 
		
		 //현재 비밀번호를 받는다
		 String origin_passwd = request.getParameter("origin_passwd"); 
		 
		 //새 비밀번호를 받는다
		 String passwd = request.getParameter("passwd");  
		 
		 //현재 로그인한 아이디 구하기
		 String user_id = (String)session.getAttribute("user_id");
		 //인증을 하기 위해 (checkmember ?) 메서드를 활용 (그러므로 dao호출)
		 OMemberDAO dao = OMemberDAO.getInstance();
		 //아이디가 있다면 한 건의 레코드를 읽어온다.
		 OMemberVO member = dao.checkMember(id);
		 
		 //사용자가 입력한 아이디가 존재하며, 로그인한 아이디와 일치하는지를 체크하기위해 변수를 하나 지정한다.
		 boolean check = false; 
		 //아이디 일치여부 체크
		 if(member!=null && id.equals(user_id)) { //아이디가 일치한다면
			 //비밀번호 일치 여부 체크(VO의 PW일치여부메서드 이용)
			 check = member.isCheckedPassword(origin_passwd);
		 }
		 
		//인증성공의 경우(true)
		 if(check) { 
			 //비밀번호 변경한다
			 dao.updatePassword(passwd, user_num); //새 비밀번호와, 회원번호를 넘겨준다
		 } 
		 
		 //정보가 잘못됬을 때 form을 호출하기 위한 UI처리를 위해, 위에서 지정했던 check변수값 저장
		 request.setAttribute("check", check);
		 
		 return "/WEB-INF/views/member/modifyPassword.jsp";

	}

}
