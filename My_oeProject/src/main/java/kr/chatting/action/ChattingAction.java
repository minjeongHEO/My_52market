package kr.chatting.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.chatting.OChattingVO;
import kr.controller.Action;
import kr.item.OItemDAO;
import kr.item.OItemVO;
import kr.member.OMemberDAO;
import kr.member.OMemberVO;

public class ChattingAction implements Action{

	@Override		//현재 페이지는 / 상품detail에서 연결되는 페이지임
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {


		HttpSession session = request.getSession();
		Integer user_num = (Integer)session.getAttribute("user_num");
		if(user_num==null) {//로그인이 되지 않은 경우
			return "redirect:/member/loginForm.do";
		}

		int item_num = Integer.parseInt(request.getParameter("item_num"));
		int trans_num = Integer.parseInt(request.getParameter("trans_num"));
		OItemDAO dao = OItemDAO.getInstance();
		OItemVO item = dao.getItem(item_num);
		
		OMemberDAO memberDao = OMemberDAO.getInstance();
		OMemberVO db_member = memberDao.getMember(trans_num);
		
		OItemDAO itemDao = OItemDAO.getInstance();
		OItemVO db_item = itemDao.getItem(item_num);

		request.setAttribute("item", item);
		request.setAttribute("trans_num", trans_num);
		request.setAttribute("member", db_member);
		request.setAttribute("item", db_item);

		return "/WEB-INF/views/chatting/chatting.jsp";	//xml에 입력하기
	}
}
