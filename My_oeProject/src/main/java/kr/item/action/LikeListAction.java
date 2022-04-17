package kr.item.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.controller.Action;
import kr.item.OItemVO;
import kr.item.OLikeDAO;
import kr.item.OLikeVO;
import kr.util.PagingUtil;

public class LikeListAction implements Action{

   @Override
   public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
      HttpSession session = request.getSession();
      Integer user_num = (Integer)session.getAttribute("user_num");
      if(user_num == null) {//로그인이 되지 않은 경우
         return "redirect:/member/loginForm.do";
      }
      OLikeDAO dao = OLikeDAO.getInstance();
      
      List<OLikeVO> list = null;
      
      list = dao.getListLike(user_num);
      
      request.setAttribute("list", list);
      
      
      return "/WEB-INF/views/item/likeList.jsp";
   }

}