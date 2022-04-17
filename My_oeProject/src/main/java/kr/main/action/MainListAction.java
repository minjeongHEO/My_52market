package kr.main.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.controller.Action;
import kr.item.OItemDAO;
import kr.item.OItemVO;
import kr.util.PagingUtil;

public class MainListAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		//** 페이징처리
		// (1) pageNum 초기세팅
		String pageNum = request.getParameter("pageNum");
		if(pageNum==null) pageNum="1";		
		
		
		// (2) keyfield, keyword 초기세팅
		String keyfield = request.getParameter("keyfield");	//얘 빼도 되나 ?
		String keyword = request.getParameter("keyword");
		
		
		// (3) count를 바꿔야 페이징처리를 할 수 있으니까
		OItemDAO dao = OItemDAO.getInstance();
		int count = dao.getListMainCount(keyword);		
		
		// (4) 페이지 처리
		//keyfield와 keyword, currentPage, count, rowCount, pageCount, url 을 넘겨준다
		PagingUtil page = new PagingUtil("", keyword, Integer.parseInt(pageNum), count, 20,10,"mainList.do");
		
		List<OItemVO> list = null;
		//정보를 읽어오고
		if(count >0) {
			list = dao.getListMain(keyword);
		}
		
		//정보를 넘겨주고
		request.setAttribute("count", count);
		request.setAttribute("list", list);
		request.setAttribute("pagingHtml", page.getPagingHtml());
		
		//JSP경로반환
		return "/WEB-INF/views/main/mainList.jsp";
	}
}
