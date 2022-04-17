package kr.chatting.action;

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
import kr.item.OItemDAO;
import kr.item.OItemVO;

public class ChattingListForBuyerAjaxAction implements Action{

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

			OItemDAO Itemdao = OItemDAO.getInstance();
			OItemVO item = Itemdao.getItem(item_num);

			OChattingDAO dao = OChattingDAO.getInstance();

			List<OChattingVO> list = dao.getChattingList(item_num,user_num);
			mapAjax.put("result", "success");
			mapAjax.put("list", list);
		}
		//JSON 데이터로 변환
		ObjectMapper mapper = new ObjectMapper();
		String ajaxData = mapper.writeValueAsString(mapAjax);

		request.setAttribute("ajaxData", ajaxData);

		return "/WEB-INF/views/common/ajax_view.jsp";
	}

}
