package kr.item.action;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.codehaus.jackson.map.ObjectMapper;

import kr.controller.Action;
import kr.item.OItemDAO;
import kr.item.OItemReplyVO;
import kr.util.PagingUtil;

public class ListReplyAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//1. 전송된 데이터 인코딩처리
		request.setCharacterEncoding("utf-8");
		//2. pageNum 초기화
		String pageNum = request.getParameter("pageNum");
		if(pageNum==null) pageNum = "1";
		//3. count값 구하기
		int item_num = Integer.parseInt(request.getParameter("item_num"));
		OItemDAO dao = OItemDAO.getInstance();
		int count = dao.getReplyItemCount(item_num);

		//4. PagingUtil 객체 생성 
		//목록데이터의 페이지 처리를 위한 rownum번호를 구하는 것이 목적임
		int rowCount=10;	//한 페이지에 10개의 레코드가 보이도록 할 것임
		PagingUtil page = new PagingUtil(Integer.parseInt(pageNum),count,rowCount,1,null);
								// PagingUtil을 통해서 startRownum과 endRownum을 구함.
		//5. list 객체 생성
		List<OItemReplyVO> list = null;
		if(count > 0) {
			list = dao.getListReplyItem(page.getStartCount(), page.getEndCount(), item_num);
		}else {	//count>0이 아닐경우 빈배열형태로 출력할 수 있도록 처리
			list = Collections.emptyList(); 
		}
		
		//6. user_num 데이터 받기
		HttpSession session = request.getSession();
		Integer user_num = (Integer)session.getAttribute("user_num");
		
		//7. HashMap에 json으로 만들 문구 넣어주기
		Map<String,Object> mapAjax = new HashMap<String,Object>();	
				//ajax로 전달되는 데이터가 String일때도있고, integer일때도있어서 <String,String>이 아니라 <String,Object>
		mapAjax.put("count", count);
		mapAjax.put("rowCount", rowCount);
		mapAjax.put("list", list);
		mapAjax.put("user_num", user_num);

		//8. JSON 데이터로 반환	
		ObjectMapper mapper = new ObjectMapper();	
		String ajaxData = mapper.writeValueAsString(mapAjax);

		//9. request에 저장
		request.setAttribute("ajaxData", ajaxData);

		//10. return 주소 반환 (ajax연결을 해줄 jsp로 연결시킴)
		return "/WEB-INF/views/common/ajax_view.jsp";
	}
 
}
