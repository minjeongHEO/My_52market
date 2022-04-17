package kr.item.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.controller.Action;
import kr.item.OItemDAO;
import kr.item.OItemVO;
import kr.util.PagingUtil;

public class MySaleListAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		Integer user_num = (Integer)session.getAttribute("user_num");
		if(user_num == null) {//로그인 되지 않은 경우
			return "redirect:/member/loginForm.do";
		}
		//로그인이 된 경우
		String pageNum = request.getParameter("pageNum");
		if(pageNum == null) pageNum="1";
		
		String keyfield = request.getParameter("keyfield");
		String keyword = request.getParameter("keyword");
				
		//count를 바꿔야 페이징처리를 할 수 있으니까
		OItemDAO dao = OItemDAO.getInstance();
		int count = dao.getItemCountByMem_num(keyfield, keyword, user_num);
		//페이지 처리
		//keyfield와 keyword, currentPage, count, rowCount, pageCount, url 을 넘겨준다
		PagingUtil page = new PagingUtil(keyfield, keyword, Integer.parseInt(pageNum), count, 12,10,"mySaleList.do");
		
		List<OItemVO> list = null;
		//정보를 읽어오고
		if(count >0) {
			list = dao.getListItemByMem_num(page.getStartCount(), page.getEndCount(), keyfield, keyword, user_num);
		}
		
		//정보를 넘겨주고
		request.setAttribute("count", count);
		request.setAttribute("list", list);
		request.setAttribute("pagingHtml", page.getPagingHtml());
		
		//JSP경로반환
		return "/WEB-INF/views/item/mySaleList.jsp";
		
	}

}

