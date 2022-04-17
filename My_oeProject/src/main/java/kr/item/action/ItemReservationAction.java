package kr.item.action;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.codehaus.jackson.map.ObjectMapper;

import com.oreilly.servlet.MultipartRequest;

import kr.chatting.OChattingDAO;
import kr.chatting.OChattingVO;
import kr.controller.Action;
import kr.item.OItemDAO;
import kr.item.OItemVO;
import kr.util.FileUtil;

public class ItemReservationAction implements Action{  
  
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
	         int rev_num = Integer.parseInt(request.getParameter("rev_num"));
	         
	         OItemDAO dao = OItemDAO.getInstance();
	         dao.updateItemOrder(item_num, rev_num, 1);
	         
	         mapAjax.put("result", "success");
	      }
	      //JSON 데이터로 변환
	      ObjectMapper mapper = new ObjectMapper();
	      String ajaxData = mapper.writeValueAsString(mapAjax);
	      
	      request.setAttribute("ajaxData", ajaxData);
		
		//10. return 주소 반환 (ajax연결을 해줄 jsp로 연결시킴)
		return "/WEB-INF/views/common/ajax_view.jsp";
		
	}

}
