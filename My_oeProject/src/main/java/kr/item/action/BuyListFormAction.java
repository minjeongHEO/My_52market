package kr.item.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.controller.Action;
import kr.item.OItemDAO;
import kr.item.OItemOrderVO;
import kr.item.OItemVO;
import kr.util.PagingUtil;

public class BuyListFormAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		//전송된 데이터 인코딩 처리
		request.setCharacterEncoding("utf-8");
		
		HttpSession session = request.getSession();
		Integer user_num = (Integer)session.getAttribute("user_num");
		if(user_num == null) { //로그인x상태
			return "redirect:/member/loginForm.do";
		}
		
		String pageNum = request.getParameter("pageNum");
		if(pageNum == null) pageNum = "1";
		
		OItemDAO dao = OItemDAO.getInstance();
		int count = dao.getBuyListItemCount(user_num);
		
		//페이지 처리
		//keyfield, keyword,currentPage,count,rowCount,pageCount,url
		PagingUtil page = new PagingUtil(Integer.parseInt(pageNum), count, 20,10,"buyListForm.do");
		
		List<OItemOrderVO> list = null;
		if(count > 0) {
			list = dao.getBuyList(page.getStartCount(), page.getEndCount(),user_num);
			System.out.println(list);
		}
		request.setAttribute("count", count);
		request.setAttribute("orderList",list);
		request.setAttribute("pagingHtml", page.getPagingHtml());
				
		//JSP경로 반환
		return "/WEB-INF/views/item/buyListForm.jsp";
	}

}
