package kr.item.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.codehaus.jackson.map.ObjectMapper;

import kr.controller.Action;
import kr.item.OItemDAO;
import kr.item.OItemReplyVO;

public class UpdateReplyAction implements Action{
	@Override
	public String execute(HttpServletRequest request,HttpServletResponse response)throws Exception {
		//post방식의 전송된 데이터 인코딩처리
		request.setCharacterEncoding("utf-8");
		
/*
 * request.getParameter/setParameter : String의 값만 받는다.
 * request.getAttribute/setAttribute : Object타입으로 리스트를 받는다.
 */
		//댓글번호
		int re_num = Integer.parseInt(request.getParameter("re_num"));
		
		OItemDAO dao = OItemDAO.getInstance();
		OItemReplyVO db_reply = dao.getReplyItem(re_num); //댓글상세메서드(getReplyItem)
		 
		HttpSession session = request.getSession(); //session에서 user_num을 구한다.
		Integer user_num = (Integer)session.getAttribute("user_num");
		
		Map<String, String> mapAjax = new HashMap<String, String>();
		if(user_num==null) { //로그인이 안 된 경우
			mapAjax.put("result","logout");
				//로그인이 되어있고   로그인한 회원번호와 작성자 회원번호 "일치"
		}else if(user_num!=null && user_num==db_reply.getMem_num()) {
			OItemReplyVO reply = new OItemReplyVO();
			//전송된 객체를 넣어준다.
			reply.setRe_num(re_num);
			reply.setRe_content(request.getParameter("re_content"));
			reply.setRe_ip(request.getRemoteAddr());
			
			dao.updateReplyItem(reply); //댓글수정메서드(updateReplyItem)
			
			mapAjax.put("result","success");
			
		}else {
			//로그인이 되어있고 로그인한 회원번호와 작성자 회원번호 "불일치"
			mapAjax.put("result","wrongAccess");
		}
/*
 * ObjectMapper : jackson라이브러리에서 포함된 클래스로, 
 * 자바 오브젝트를 JSON형식으로 변환하거나 JSON형식을 오브젝트 형식으로 변환하는 기능을 제공한다.	
 */
		//JSON데이터로 변환
		ObjectMapper mapper = new ObjectMapper(); 
		String ajaxData = mapper.writeValueAsString(mapAjax);
		
		request.setAttribute("ajaxData", ajaxData); 
		
		return "/WEB-INF/views/common/ajax_view.jsp";
	}
}
