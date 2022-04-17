package kr.member.action;

import javax.servlet.http.HttpServletRequest; 
import javax.servlet.http.HttpServletResponse;

import kr.controller.Action;
import kr.member.OMemberDAO;
import kr.member.OMemberVO;

public class FindIdAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		//1. 전송된 데이터(이름,전화번호)인코딩 처리
		request.setCharacterEncoding("utf-8");
		
		//2. 전송된 데이터(name, phone) 반환
		String name = request.getParameter("name");	//name = mem_nick
		String phone = request.getParameter("phone");
		
		//3. name 인증
		OMemberDAO dao = OMemberDAO.getInstance();
		OMemberVO member = dao.checkName(name);
				//member에는 nick, id, phone, auth데이터 담김
		
		boolean check = false;
		
		//4. name이 존재
		if(member!=null) {
			//(1) phone 일치여부체크
			check = member.findIdMember(name,phone);	
			
			//(2) 실패시 메시지 (****저장방식 이렇게???)
			request.setAttribute("FindId", "NotSame");
			
		}else {	//name이 존재하지 않을 때(name==null)
			request.setAttribute("FindId", "NotExist");
		}
		
		//5. name과 phone이 일치 => request에 id 담아준다.
		if(check) {
			request.setAttribute("Success", member.getMem_id());
		}
		
		//6. JSP 경로반환
			return "/WEB-INF/views/member/findId.jsp";
	}
}
