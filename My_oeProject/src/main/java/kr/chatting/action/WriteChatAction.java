package kr.chatting.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.codehaus.jackson.map.ObjectMapper;

import kr.chatting.OChattingDAO;
import kr.chatting.OChattingVO;
import kr.controller.Action;

public class WriteChatAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		Map<String,String> mapAjax = new HashMap<String,String>();
		
		HttpSession session = request.getSession();
		Integer user_num = (Integer)session.getAttribute("user_num");
		
		//로그인되지 않은경우
		if(user_num == null) {
			mapAjax.put("result","logout");
			
		//로그인된 경우	
		}else {
			//전송된 데이터 인코딩
			request.setCharacterEncoding("utf-8");
			
			//전송된 데이터를 자바빈(VO)에 저장
			OChattingVO chat = new OChattingVO();
			chat.setTo_num(Integer.parseInt(request.getParameter("to_num")));//판매자회원번호
			chat.setFrom_num(user_num);
			chat.setContent(request.getParameter("content"));
			chat.setItem_num(Integer.parseInt(request.getParameter("item_num")));
			
			//데이터가 저장된 VO객체를 insert메서드 실행
			OChattingDAO dao = OChattingDAO.getInstance();
			dao.insertChat(chat);
			
			mapAjax.put("result", "success");
		}
		
		//JSON데이터 생성
		ObjectMapper mapper = new ObjectMapper();
		String ajaxData = mapper.writeValueAsString(mapAjax);
				
		//request에 ajax문자열 저장하기	
		request.setAttribute("ajaxData", ajaxData);		
		
	return "/WEB-INF/views/common/ajax_view.jsp";
	}
}
