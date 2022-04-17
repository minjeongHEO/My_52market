package kr.category.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.category.OCategoryDAO;
import kr.category.OCategoryVO;
import kr.controller.Action;

public class AdminHeaderAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//전송된 데이터 인코딩 처리
		request.setCharacterEncoding("utf-8");	
		/*       
	    HttpSession session = request.getSession();
		Integer user_num = (Integer)session.getAttribute("user_num");
		Integer user_auth = (Integer)session.getAttribute("user_auth"); 
		 if(user_num == null) { //로그인 되지 않은 경우
		     return "redirect:/member/loginForm.do";
		  }
		  //0:정지회원/ 1:일반회원.default / 2:관리자
		  if(user_auth < 2) { //관리자로 로그인 하지 않은 경우
		     return "/WEB-INF/views/common/notice.jsp";
		  }      
		 
		  request.setAttribute("user_num", user_num);
		  request.setAttribute("user_auth", user_auth);
		 */
		
		OCategoryDAO dao = OCategoryDAO.getInstance();
		 
		List<OCategoryVO> list = null;
		list = dao.getListCateMenu();
		 
		request.setAttribute("cateName_list", list);
	     
	return "/WEB-INF/views/common/header.jsp";
	
	}

}
