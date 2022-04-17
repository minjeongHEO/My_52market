package kr.chatting.action;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.codehaus.jackson.map.ObjectMapper;

import kr.chatting.OChattingDAO;
import kr.chatting.OChattingVO;
import kr.controller.Action;
import kr.util.PagingUtil;

public class ChattingListAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		Map<String,Object> mapAjax = new HashMap<String,Object>();
	      
	      HttpSession session = request.getSession();
	      Integer user_num = (Integer)session.getAttribute("user_num");
	      if(user_num==null) {//로그인이 되지 않은 경우
	         mapAjax.put("result", "logout");
	      }else {//로그인 된 경우
	         //전송된 데이터 인코딩 처리
	         request.setCharacterEncoding("utf-8");

	         int item_num = Integer.parseInt(request.getParameter("item_num"));
	         int trans_num = Integer.parseInt(request.getParameter("trans_num"));
	         
	         OChattingDAO dao = OChattingDAO.getInstance();
	         int count = dao.getChattingDetailCount(item_num, user_num, trans_num);
	         List<OChattingVO> list = null;
	         if(count > 0) {         
	            list = dao.getChattingDetail(item_num,user_num, trans_num);
	         }else {
	            list = Collections.emptyList();
	         }
	         mapAjax.put("result", "success");
	         mapAjax.put("count", count);
	         mapAjax.put("list", list);
	      }
	      //JSON 데이터로 변환
	      ObjectMapper mapper = new ObjectMapper();
	      String ajaxData = mapper.writeValueAsString(mapAjax);
	      
	      request.setAttribute("ajaxData", ajaxData);
		
		//10. return 주소 반환 (ajax연결을 해줄 jsp로 연결시킴)
		return "/WEB-INF/views/common/ajax_view.jsp";
	}
}
