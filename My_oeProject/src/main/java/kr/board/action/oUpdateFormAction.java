package kr.board.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.board.OBoardDAO;
import kr.board.OBoardVO;
import kr.controller.Action;

public class oUpdateFormAction implements Action{

   @Override
   public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
      HttpSession session = request.getSession();
      Integer user_num = (Integer)session.getAttribute("user_num");
      if(user_num==null) {//로그인 되지 않은 경우
         return "redirect:/member/loginForm.do";
      }
      
      int board_num = Integer.parseInt(request.getParameter("board_num"));
      OBoardDAO dao = OBoardDAO.getInstance();
      OBoardVO db_board = dao.getBoard(board_num);
      if(user_num != db_board.getMem_num()) {//로그인한 회원번호와 작성자 회원번호 불일치
         return "/WEB-INF/views/common/notice.jsp";
      }
      
      //로그인이 되어있고 로그인한 회원번호와 작성자 회원번호 일치
      request.setAttribute("board", db_board);
      
      return "/WEB-INF/views/board/oUpdateForm.jsp";
   }

}