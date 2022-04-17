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
import kr.util.FileUtil;

public class ItemDeleteFileAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		//수정form에서 바로 파일삭제 가능하도록 ajax방식이용
		
		//1. json타입 데이터를 만들기위해 데이터운반 저장소 Map객체 생성
		Map<String,String> mapAjax = new HashMap<String,String>();
		
		//2. 로그인되어있는지 확인
		HttpSession session = request.getSession();
		Integer user_num = (Integer)session.getAttribute("user_num");
			//로그인이 안된 경우
			if(user_num==null) {
				mapAjax.put("result", "logout");
			}else {
			//**로그인이 된 경우 - 해당 게시판에 저장된 데이터 읽어오기.	
				int item_num = Integer.parseInt(request.getParameter("item_num"));
				
				OItemDAO dao = OItemDAO.getInstance();
				OItemVO db_item = dao.getItem(item_num);
				
				
				//로그인한 회원번호와 작성자 회원번호가 불일치 
				if(user_num!=db_item.getMem_num()) {
					mapAjax.put("result", "wrongAccess");
				}else {
					//로그인한 회원번호와 작성자 회원번호가 일치 = > 삭제
					dao.deleteFile(item_num);
					FileUtil.removeFile(request, db_item.getFilename());
				
					mapAjax.put("result", "success");
				}
			}
		//3. JSON 데이터 생성
		ObjectMapper mapper = new ObjectMapper();
		String ajaxData = mapper.writeValueAsString(mapAjax);
		
		//4. 모델클래스는 VIEW로 데이터 전송 불가. => jsp를 한번 거쳐서, 만든 jsp에서 param으로 데이터 전달
		request.setAttribute("ajaxData", ajaxData); 
		
		return "/WEB-INF/views/common/ajax_view.jsp";
	}

}
  