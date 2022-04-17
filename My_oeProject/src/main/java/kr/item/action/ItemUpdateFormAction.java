package kr.item.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.controller.Action;
import kr.item.OItemDAO;
import kr.item.OItemVO;

public class ItemUpdateFormAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		Integer user_num = (Integer)session.getAttribute("user_num");
		
		//로그인 되지 않은 경우
		if(user_num==null) {
			return "redirect:/member/loginForm.do";
		}
		
		//로그인은 되어있는데 작성자가 아닐때 (잘못된 접속)
		int item_num = Integer.parseInt(request.getParameter("item_num"));
		OItemDAO dao = OItemDAO.getInstance();
		OItemVO db_item = dao.getItem(item_num);
		
		//로그인이 되어있고 로그인한 회원번호와 작성자 회원번호 일치
		request.setAttribute("item", db_item);
		
		
		return "/WEB-INF/views/item/itemUpdateForm.jsp";
	}

}
