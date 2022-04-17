package kr.chatting.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.controller.Action;
import kr.item.OItemDAO;
import kr.item.OItemVO;

public class ChattingListForBuyerAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		HttpSession session = request.getSession();
		Integer user_num = (Integer)session.getAttribute("user_num");
		if(user_num==null) {//로그인이 되지 않은 경우
			return "redirect:/member/loginForm.do";
		}

		int item_num = Integer.parseInt(request.getParameter("item_num"));
		OItemDAO dao = OItemDAO.getInstance();
		OItemVO item = dao.getItem(item_num);

		request.setAttribute("item", item);

		return "/WEB-INF/views/chatting/chattingListForBuyer.jsp";
	}

}
