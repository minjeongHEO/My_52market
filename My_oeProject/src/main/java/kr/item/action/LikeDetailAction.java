package kr.item.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.codehaus.jackson.map.ObjectMapper;

import kr.controller.Action;
import kr.item.OItemDAO;
import kr.item.OItemVO;
import kr.item.OLikeDAO;
import kr.item.OLikeVO;

public class LikeDetailAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		Map<String,Object> mapAjax = new HashMap<String,Object>();

		HttpSession session = request.getSession();
		Integer user_num = (Integer)session.getAttribute("user_num");

		request.setCharacterEncoding("utf-8");

		int item_num = Integer.parseInt(request.getParameter("item_num"));

		OLikeDAO dao = OLikeDAO.getInstance();
		if(user_num==null) {
			mapAjax.put("result", "success");
			mapAjax.put("status", "noFav");
			mapAjax.put("count", dao.selectLikeCount(item_num));
		}else {
			//로그인된 아이디 셋팅
			OLikeVO boardFav = dao.selectLike(item_num,user_num);

			if(boardFav!=null) {
				mapAjax.put("result", "success");
				mapAjax.put("status", "yesFav");
				mapAjax.put("count", dao.selectLikeCount(item_num));
			}else {
				mapAjax.put("result", "success");
				mapAjax.put("status", "noFav");
				mapAjax.put("count", dao.selectLikeCount(item_num));
			}
		}
		ObjectMapper mapper = new ObjectMapper();
		String ajaxData = mapper.writeValueAsString(mapAjax);

		request.setAttribute("ajaxData", ajaxData);
		return "/WEB-INF/views/common/ajax_view.jsp";
	}
}