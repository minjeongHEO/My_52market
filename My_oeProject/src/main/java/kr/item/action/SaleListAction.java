package kr.item.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.category.OCategoryDAO;
import kr.category.OCategoryVO;
import kr.controller.Action;
import kr.item.OItemDAO;
import kr.item.OItemVO;
import kr.util.PagingUtil;

public class SaleListAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		//** 페이징처리
		// (1) pageNum 초기세팅
		String pageNum = request.getParameter("pageNum");
		if(pageNum==null) pageNum="1";
		
		// (2) keyfield, keyword 초기세팅
		String keyfield = request.getParameter("keyfield");
		String keyword = request.getParameter("keyword");
		
		String cate_num = request.getParameter("cate_num");
		if(cate_num == null) cate_num = "0";
				
		// (3) count 세팅
		OItemDAO dao = OItemDAO.getInstance();
		int count = dao.getListItemCount(keyfield, keyword, Integer.parseInt(cate_num));
		
		//(4) 페이지 처리
		//keyfield와 keyword, currentPage, count, rowCount, pageCount, url 을 넘겨준다
		PagingUtil page = new PagingUtil(keyfield, keyword, Integer.parseInt(pageNum), count, 12,10,"saleList.do","&cate_num="+Integer.parseInt(cate_num));
		
		// (5) list에 저장된 정보 불러와서, VEIW에서 출력할 수 있도록 request에 담아주기
		List<OItemVO> list = null;
		//정보를 읽어오고
		if(count >0) {
			list = dao.getListItem(page.getStartCount(), page.getEndCount(), keyfield, keyword, Integer.parseInt(cate_num));
		}
		
		OCategoryDAO cateDao = OCategoryDAO.getInstance();
		OCategoryVO cateVo = cateDao.getCategory(Integer.parseInt(cate_num));
		
		//정보를 넘겨주고
		request.setAttribute("count", count);
		request.setAttribute("list", list);
		request.setAttribute("pagingHtml", page.getPagingHtml());
		request.setAttribute("category", cateVo);
		
		//JSP경로반환
		return "/WEB-INF/views/item/saleList.jsp";
	}
}
