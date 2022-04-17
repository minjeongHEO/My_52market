package kr.member.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.controller.Action;
import kr.member.OMemberDAO;
import kr.member.OMemberVO;
import kr.util.FileUtil;

public class DeleteUserAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		HttpSession session = request.getSession();
		Integer user_num = (Integer)session.getAttribute("user_num");
		if(user_num == null ) {//로그인 되지 않은 경우
			return "redirect:/member/loginForm.do";
		}
		
		//로그인 된 경우
		//인코딩 처리
		request.setCharacterEncoding("utf-8");
		
		String id= request.getParameter("id");
		String passwd = request.getParameter("passwd");
		
		//로그인한 아이디
		String user_id = (String)session.getAttribute("user_id");
		
		OMemberDAO dao = OMemberDAO.getInstance();
		OMemberVO db_member = dao.checkMember(id);
		boolean check = false;
		
		//사용자가 입력한 아이디가 존재하고 로그인한 아이디가 일치하는지 체크
		if(db_member!=null && id.equals(user_id)) {
			//비밀번호 일치 여부 체크
			check = db_member.isCheckedPassword(passwd);
		}
		if(check) {
			//회원 정보 삭제
			dao.deleteMember(user_num);
			//프로필 삭제
			FileUtil.removeFile(request, db_member.getMem_photo());
			//로그아웃
			session.invalidate();
		}

		request.setAttribute("check", check);
		
		return "/WEB-INF/views/member/deleteUser.jsp";
	}

}
