package kr.category.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


import kr.category.OCategoryDAO;
import kr.category.OCategoryVO;
import kr.controller.Action;

public class AdminCateAction implements Action{
   @Override
   public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
 
      HttpSession session = request.getSession();
      Integer user_num = (Integer)session.getAttribute("user_num");
     Integer user_auth = (Integer)session.getAttribute("user_auth"); 
            
      if(user_num == null) { //로그인 되지 않은 경우
         return "redirect:/member/loginForm.do";
      }
      //0:정지회원/ 1:일반회원.default / 2:관리자
      if(user_auth < 2) { //관리자로 로그인 하지 않은 경우
         return "/WEB-INF/views/common/notice.jsp";
      }      
     
      request.setAttribute("user_num", user_num);
      request.setAttribute("user_auth", user_auth);
      
      /*
      //관리자로 로그인한 경우   
      OCategoryVO cate = new OCategoryVO();
      
      cate.setCate_name(request.getParameter("re_content"));
      cate.setCate_status(Integer.parseInt(request.getParameter("cate_status") ));
      
      OCategoryDAO dao = OCategoryDAO.getInstance();
      dao.getListCate(1,8);
      
      request.setAttribute("cate_name", cate.getCate_name());
      request.setAttribute("cate_status", cate.getCate_status());
      */
      
      return "/WEB-INF/views/category/adminCate.jsp";
   }

}