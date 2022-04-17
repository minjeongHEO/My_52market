package kr.category.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.codehaus.jackson.map.ObjectMapper;

import kr.category.OCategoryDAO;
import kr.controller.Action;

public class AdminDeleteCateAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		
		//1. 전송된 데이터 인코딩처리(by.post방식)
		request.setCharacterEncoding("utf-8");
				
		//2. 전송된 데이터 반환
		int cate_num = Integer.parseInt(request.getParameter("cate_num"));
		
		Map<String,String> mapAjax = new HashMap<String,String>();
		
		
		//3. 로그인되지 않은경우
		HttpSession session = request.getSession();
		Integer user_num = (Integer)session.getAttribute("user_num");		
		Integer user_auth = (Integer)session.getAttribute("user_auth"); 
		
		if(user_num==null) { //로그인이 되지않은 경우
			mapAjax.put("result", "logout");
			
		//4. 로그인이 되어있고, 관리자로 로그인하지 않은 경우	
		}else if(user_num!=null && user_auth<2) {
			mapAjax.put("result", "wrongAccess");
			
		//5. 로그인이 되어있고, 관리자로 로그인한 경우 => 삭제
		}else{
			OCategoryDAO dao = OCategoryDAO.getInstance();
			dao.deleteCate(cate_num);
			mapAjax.put("result", "success");
		}		
		
		//6. JSON 데이터로 변환
		ObjectMapper mapper = new ObjectMapper();
		String ajaxData = mapper.writeValueAsString(mapAjax);
		
		request.setAttribute("ajaxData", ajaxData);
		
		return "/WEB-INF/views/common/ajax_view.jsp";
	}

}
