package kr.category.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.codehaus.jackson.map.ObjectMapper;

import kr.category.OCategoryDAO;
import kr.category.OCategoryVO;
import kr.controller.Action;

public class AdminUpdateCateAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		//1. 로그인정보 불러오기   
		HttpSession session = request.getSession();
		Integer user_num = (Integer)session.getAttribute("user_num");
		Integer user_auth = (Integer)session.getAttribute("user_auth"); 

		request.setAttribute("user_num", user_num);
		request.setAttribute("user_auth", user_auth);		

		
		Map<String, String> mapAjax = new HashMap<String, String>();
		if(user_num == null) {
			mapAjax.put("result","logout");
		}
		//0:정지회원/ 1:일반회원.default / 2:관리자
		if(user_auth < 2) { //관리자로 로그인 하지 않은 경우
			mapAjax.put("result", "wrongAccess");
		}	   
	
		
		OCategoryVO cate = new OCategoryVO();
		cate.setCate_num(Integer.parseInt(request.getParameter("cate_num")));
		cate.setCate_name(request.getParameter("re_content"));
		cate.setCate_status(Integer.parseInt(request.getParameter("cate_status")));
		
		OCategoryDAO dao = OCategoryDAO.getInstance();
		dao.updateCate(cate);

		mapAjax.put("result", "success");
	
		//JSON 데이터로 변환
		ObjectMapper mapper = new ObjectMapper();
		String ajaxData = mapper.writeValueAsString(mapAjax);
		
		request.setAttribute("ajaxData", ajaxData);
		
		return "/WEB-INF/views/common/ajax_view.jsp";
	}

}
