package kr.item.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.controller.Action;
import kr.item.OItemDAO;
import kr.item.OItemVO;
import kr.util.FileUtil;

public class ItemDeleteAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		Integer user_num = (Integer)session.getAttribute("user_num");
		if(user_num==null) {//로그인이 되지 않은 경우
			return "redirect:/member/loginForm.do";
		}
		
		//로그인된 경우
		int item_num = Integer.parseInt(request.getParameter("item_num"));
		OItemDAO dao = OItemDAO.getInstance();
		OItemVO db_item = dao.getItem(item_num);
		if(user_num!=db_item.getMem_num()) {//로그인한 회원번호와 작성자 회원번호 불일치
			return "/WEB-INF/views/common/notice.jsp";
		}
		
		//로그인한 회원번호와 작성자 회원번호가 일치
		dao.deleteItem(item_num);
		//파일삭제
		FileUtil.removeFile(request, db_item.getFilename());
		
		return "/WEB-INF/views/item/itemDelete.jsp";
	}

}
