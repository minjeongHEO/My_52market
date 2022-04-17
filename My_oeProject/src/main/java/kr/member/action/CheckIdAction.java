package kr.member.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;

import kr.controller.Action;
import kr.member.OMemberDAO;
import kr.member.OMemberVO;

public class CheckIdAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//전송된 데이터 인코딩 처리
				request.setCharacterEncoding("utf-8");
				
				//전송된 데이터 반환
				String mem_id = request.getParameter("mem_id");
				
				OMemberDAO dao = OMemberDAO.getInstance();
				OMemberVO member = dao.checkMember(mem_id); //한건의 레코드를 보내거나 null임
				
				Map<String,String> mapAjax = new HashMap<String, String>();
				if(member == null) { //id 미중복
					mapAjax.put("result", "idNotFound");
				}else { //id 중복
					mapAjax.put("result", "idDuplicated");
				}
				
				/*
				 * JSON형식으로 변환하기를 원하는 문자열을 HashMap에 key와 value의 쌍으로 저장한 후
				 * ObjectMapper의 writeValueAsString메서드에 Map객체를 전달해서
				 * 일반 문자열 데이터를 JSON형식의 데이터로 변환 후 반환.
				 */
				ObjectMapper mapper = new ObjectMapper();
				//클라이언트가 데이터저장만 가능하지, 전송을 불가능하므로 jsp으로 보냄
				String ajaxData = mapper.writeValueAsString(mapAjax); //얘가 제이슨문자열 생성역할
				
				//ajax문자열을 만들어줌(공유가능, 전송만 해줌)
				request.setAttribute("ajaxData", ajaxData);
				
				//JSP경로 반환
				return "/WEB-INF/views/common/ajax_view.jsp";
	}
} 
