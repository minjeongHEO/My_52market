package kr.item.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.oreilly.servlet.MultipartRequest;

import kr.controller.Action;
import kr.item.OItemDAO;
import kr.item.OItemVO;
import kr.util.FileUtil;
import kr.util.StringUtil;

public class ItemDetailAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		

		/* 로그인하지 않아도 판매상품이 보여야한다.
		//1. 로그인 확인
		HttpSession session = request.getSession();
		Integer user_num = (Integer)session.getAttribute("user_num");
			//로그인 되지 않은 경우
			if(user_num == null) {
				return "redirect:/member/loginForm.do";
			}
		*/
			
		request.setCharacterEncoding("utf-8");
		
		//2. 상품번호(item_num) 반환
		int item_num = Integer.parseInt(request.getParameter("item_num"));
		OItemDAO dao = OItemDAO.getInstance();
		
		//3. 해당 상품번호에 조회수 증가 메서드적용
		dao.updateReadcount(item_num);	
		
		//4. item객체에 해당 상품번호의 데이터 담기		
		OItemVO item = dao.getItem(item_num); 
		
		//5. 제목과 내용에 태그 허용여부 처리
		//제목 - HTML태그를 허용하지 않음
		item.setTitle(StringUtil.useNoHtml(item.getTitle()));
		//내용 - HTML태그를 허용하지 않으면서 줄바꿈 처리
		item.setContent(StringUtil.useBrNoHtml(item.getContent()));

		
		//6.request에 item객체 데이터 저장
		request.setAttribute("item", item);
		
		return "/WEB-INF/views/item/itemDetail.jsp";
	}
   
}
