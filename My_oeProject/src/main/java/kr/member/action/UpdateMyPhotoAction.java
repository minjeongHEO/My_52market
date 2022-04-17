package kr.member.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.codehaus.jackson.map.ObjectMapper;

import com.oreilly.servlet.MultipartRequest;

import kr.controller.Action;
import kr.member.OMemberDAO;
import kr.member.OMemberVO;
import kr.util.FileUtil;

public class UpdateMyPhotoAction implements Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
/*
 * 기존방식과 다르게 AJAX통신(폼/화면을 보여주는 형태가 아니라 데이터를 보내주는 형식)을 하기 때문에
 * 만들어지는 데이터를 담기위한 MAP객체를 생성(=key와 value의 쌍으로 저장하기위함)
 */
		Map<String,String> mapAjax = new HashMap<String, String>();
		
		//회원제 서비스, 로그인 체크여부
		HttpSession session = request.getSession();
		Integer user_num = (Integer)session.getAttribute("user_num");
		
		//로그인이 안 된 경우 : 데이터만 넘기기에 HashMap에 넘긴다
		if(user_num==null) { 
			mapAjax.put("result", "logout");
/*
 * -사진 갱신은 UPDATE 개념이라 최초에 데이터를 넣을 때를 제외하고는 기존파일이 있다면 삭제를 해줘야 한다.
 * (기존 이미지 파일을 갱신할 때 DB에서는 파일명만 갱신하기 때문에)
 * -먼저 기존정보가 있을 수 있기 때문에 기존의 이미지 파일 정보를 읽어온다.		
 * (FileUtil에서 파일명이 null이면 무반응이도록 만들어놨으니, null이 아닐 경우에만 작업하게 된다.)
 */
		//로그인이 된 경우 : 클라이언트에 정보를 보낸다.
 		}else { 
			OMemberDAO dao = OMemberDAO.getInstance();
			OMemberVO db_member = dao.getMember(user_num);//이전 이미지 파일 정보 읽기
/*
 * 전송된 파일 업로드 처리 시 MultipartRequest 타입으로 반환을 해주면 되는데, 
 * 유틸리티(FileUtil)를 만들었으니 호출하여 request를 넣어준다.
 * FileUtil의 createFile()가 실행되면 절대경로의 파일을 업로드 한 것이다.
 */
		//전송된 파일 업로드 처리
		MultipartRequest multi =FileUtil.createFile(request);
		
		//서버에 저장된(업로드한 파일) 파일명 반환
		String photo = multi.getFilesystemName("photo"); //"photo"라고 파라미터 네임 명시
		
		//프로필 수정작업
		dao.updateMyPhoto(photo, user_num); //(파일명, 회원번호(mem_num))
/*
 * 로그인 할 때 LoginAction에서 session에 user_photo라는 이름으로 사진정보를 저장해놨는데
 * 갱신 작업을 하면 DB에 있는 사진이름과, Session에 있는 사진이름이 달라질 것이다.
 * 그런 현상 때문에 수정을 했으면 Session에 저장된 사진 정보를 갱신해줘야한다.
 */
		//세션에 저장된 프로필 사진 정보 갱신
		session.setAttribute("user_photo", photo);
		
		//갱신 후 이전의 프로필 이미지 삭제하기(VO의.getMem_photo가 null이 아니면 삭제 작업을 할 것이다.)
		FileUtil.removeFile(request, db_member.getMem_photo()); 
		
		mapAjax.put("result","success");
 		}
	     
		//JSON데이터로 변환하기
		ObjectMapper mapper = new ObjectMapper(); //ObjectMapper객체 생성
		String ajaxData = mapper.writeValueAsString(mapAjax);
		
		//모델클래스는 전송을 못하니까 request에 저장 후 JSP경로 반환
		request.setAttribute("ajaxData", ajaxData);
		
		return "/WEB-INF/views/common/ajax_view.jsp"; //mypage.jsp에서 작업을한다.
	}

}
