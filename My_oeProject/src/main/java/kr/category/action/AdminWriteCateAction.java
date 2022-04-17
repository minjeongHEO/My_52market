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
import kr.item.OItemDAO;
import kr.item.OItemReplyVO;

public class AdminWriteCateAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//1. 로그인여부 확인
				Map<String,String> mapAjax = new HashMap<String,String>();
				HttpSession session = request.getSession();
				Integer user_num = (Integer)session.getAttribute("user_num");
				
					//로그인되지 않은 경우 => ajax로 json타입의 문자열 만들어서 알려주기 ('로그인안됐음)
					if(user_num==null) {
						mapAjax.put("result","logout");

					}else {
					//2. 로그인된 경우	
						//3. 전송된 데이터 인코딩처리
						request.setCharacterEncoding("utf-8");
						//4. 전송된 데이터를 자바빈(VO)에 저장
						OCategoryVO cate = new OCategoryVO();
						//cate.setMem_num(user_num);
						cate.setCate_name(request.getParameter("re_content"));
						cate.setCate_status(Integer.parseInt(request.getParameter("cate_status")));
						
						//5. 데이터가 저장된 VO객체를 insert메서드 실행 
						OCategoryDAO dao = OCategoryDAO.getInstance();
//						  dao.insertReplyItem(reply); // 댓글메서드1. 댓글등록 : insertReplyItem()
						dao.insertCate(cate); //카테고리등록(insertCate(OCategoryVO cate))
						
						mapAjax.put("result", "success");
					}
					
					//6. JSON 데이터 생성
					ObjectMapper mapper = new ObjectMapper();
					String ajaxData = mapper.writeValueAsString(mapAjax);
				
					//7. request에 ajax문자열 저장하기
					request.setAttribute("ajaxData", ajaxData);		
					
				return "/WEB-INF/views/common/ajax_view.jsp";
	}

}
