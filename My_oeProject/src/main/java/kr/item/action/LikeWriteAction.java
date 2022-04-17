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
  
public class LikeWriteAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		Map<String,Object> mapAjax = new HashMap<String,Object>();

		HttpSession session = request.getSession();
		Integer user_num = (Integer)session.getAttribute("user_num");

		if(user_num == null) {//로그인이 되지 않은 경우
			mapAjax.put("result", "logout");

		}else {//로그인이 된 경우

			request.setCharacterEncoding("utf-8");
			int item_num = Integer.parseInt(request.getParameter("item_num"));
			
			OLikeDAO dao = OLikeDAO.getInstance();
			OLikeVO vo = dao.selectLike(item_num,user_num);

			if(vo!=null) {
				dao.deleteLike(vo.getLike_num());

				mapAjax.put("result", "success");
				mapAjax.put("status", "noFav");
				mapAjax.put("count", dao.selectLikeCount(item_num));
			}else {
				dao.insertLike(item_num, user_num);

				mapAjax.put("result", "success");
				mapAjax.put("status", "yesFav");
				mapAjax.put("count", dao.selectLikeCount(item_num));
			}
		}
		ObjectMapper mapper = new ObjectMapper();
		String ajaxData = mapper.writeValueAsString(mapAjax);

		request.setAttribute("ajaxData", ajaxData);
		return "/WEB-INF/views/common/ajax_view.jsp";
		
		//return "/WEB-INF/views/item/likeList.jsp";
	   }
	}
	
