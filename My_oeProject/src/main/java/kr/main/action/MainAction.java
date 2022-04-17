package kr.main.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.category.OCategoryDAO;
import kr.category.OCategoryVO;
import kr.controller.Action;
import kr.item.OItemDAO;
import kr.item.OItemVO;

public class MainAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		//상품정보 읽기
		OItemDAO dao = OItemDAO.getInstance();
		List<OItemVO> itemList = dao.getListItemForMain(1, 10);	
		//state값 안줬음(DAO구성도 state값없음)

		request.setAttribute("itemList", itemList);

		//JSP 경로 반환
		return "/WEB-INF/views/main/main.jsp";
	}
}
