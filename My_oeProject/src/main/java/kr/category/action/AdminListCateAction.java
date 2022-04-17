package kr.category.action;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.codehaus.jackson.map.ObjectMapper;

import kr.category.OCategoryVO;
import kr.controller.Action;
import kr.category.OCategoryDAO;

public class AdminListCateAction implements Action {

   @Override
   public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {


      //1. 전송된 데이터 인코딩처리
      request.setCharacterEncoding("utf-8");

      /*
                     //2. pageNum 초기화
                     String pageNum = request.getParameter("pageNum");
                     if(pageNum==null) pageNum = "1";
       */


      //1. count 값 구하기
      OCategoryDAO dao = OCategoryDAO.getInstance();
      int count = dao.getCateCount();



      //2. list 객체 생성 => 저장된 category list 구하기
      List<OCategoryVO> cateList = null;
      if(count > 0) {
         cateList = dao.getListCate(1, 10);
      }else {   //count>0이 아닐경우 빈배열형태로 출력할 수 있도록 처리
         cateList = Collections.emptyList(); 
      }


      //3. user_num 데이터 받기
      HttpSession session = request.getSession();
      Integer user_num = (Integer)session.getAttribute("user_num");

      if(user_num == null) { //로그인 되지 않은 경우
         return "redirect:/member/loginForm.do";
      }        
      //0:정지회원/ 1:일반회원.default / 2:관리자
      Integer user_auth = (Integer)session.getAttribute("user_auth");
      if(user_auth < 2) { //관리자로 로그인 하지 않은 경우
         return "/WEB-INF/views/common/notice.jsp";
      }


      //관리자로 로그인한 경우   
      //4. HashMap에 json으로 만들 문구 넣어주기
      Map<String,Object> mapAjax = new HashMap<String,Object>();   
      //ajax로 전달되는 데이터가 String일때도있고, integer일때도있어서 <String,String>이 아니라 <String,Object>
      mapAjax.put("count", count);
      mapAjax.put("list", cateList);
      mapAjax.put("user_num", user_num);


      //5. JSON 데이터로 반환   
      ObjectMapper mapper = new ObjectMapper();   
      String ajaxData = mapper.writeValueAsString(mapAjax);


      //6. request에 저장
      request.setAttribute("ajaxData", ajaxData);

      //10. return 주소 반환 (ajax연결을 해줄 jsp로 연결시킴)   
      return "/WEB-INF/views/common/ajax_view.jsp";
   }

}
