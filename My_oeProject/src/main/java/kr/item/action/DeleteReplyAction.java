package kr.item.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.codehaus.jackson.map.ObjectMapper;

import kr.item.OItemDAO;
import kr.item.OItemReplyVO;
import kr.controller.Action;

public class DeleteReplyAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//전송된 데이터 인코딩 처리
		request.setCharacterEncoding("utf-8");
		//전송된 데이터 반환
		int re_num = Integer.parseInt(request.getParameter("re_num"));
		
		Map<String,String> mapAjax= new HashMap<String,String>();
			
		OItemDAO dao = OItemDAO.getInstance();
		
		OItemReplyVO db_reReply = dao.getReplyItem(re_num); //댓글상세메서드(getReplyItem)
		
		HttpSession session = request.getSession();
		Integer user_num = (Integer)session.getAttribute("user_num");
		
		if(user_num==null) {
			//로그인 X인 경우
			mapAjax.put("reult","logout");
			//로그인 O, 			로그인한 회원번호와 	댓글작성회원번호가 "일치"
		}else if(user_num!=null && user_num == db_reReply.getMem_num()) {
			
			dao.deleteReplyItem(re_num); //댓글삭제메서드(deleteReplyBoard)
			
			mapAjax.put("result","success");
			
		}else {
			//로그인 O, 로그인한 회원번호와 댓글작성회원번호가 "불일치"
			 mapAjax.put("result","wrongAccess");
		}
		//JSON데이터로 변환
				ObjectMapper mapper = new ObjectMapper();
				String ajaxData = mapper.writeValueAsString(mapAjax);
				
				request.setAttribute("ajaxData", ajaxData);
				
				return "/WEB-INF/views/common/ajax_view.jsp";
	}

}
