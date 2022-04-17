package kr.board.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.board.OBoardDAO;
import kr.board.OBoardVO;
import kr.controller.Action;
import kr.util.FileUtil;

public class oDeleteAction implements Action{

   @Override
   public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
      HttpSession session = request.getSession();
      Integer user_num = (Integer)session.getAttribute("user_num");
      if(user_num==null) {//로그인이 되지 않은 경우
         return "redirect:/member/loginForm.do";
      }
      
      //로그인된 경우
      int board_num = Integer.parseInt(request.getParameter("board_num"));
      OBoardDAO dao = OBoardDAO.getInstance();
      OBoardVO db_board = dao.getBoard(board_num);
      if(user_num != db_board.getMem_num()) {//로그인한 회원번호와 작성자 회원번호가 불일치
         return "/WEB-INF/views/common/notice.jsp";
      }
      
      //로그인한 회원번호와 작성자 회원번호가 일치
      dao.deleteBoard(board_num);
      //파일 삭제
      FileUtil.removeFile(request, db_board.getFilename());      
      
      return "redirect:/board/oList.do";
   }

}