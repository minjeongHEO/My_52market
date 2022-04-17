package kr.board.action;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.oreilly.servlet.MultipartRequest;

import kr.board.OBoardDAO;
import kr.board.OBoardVO;
import kr.controller.Action;
import kr.util.FileUtil;

public class oWriteAction implements Action{

   @Override
   public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
      HttpSession session = request.getSession();
      Integer user_num = (Integer)session.getAttribute("user_num");
      if(user_num == null) {//로그인이 되지 않은 경우
         return "redirect:/member/loginForm.do";
      }
      
      //로그인 된 경우
      MultipartRequest multi = FileUtil.createFile(request);
      OBoardVO board = new OBoardVO();
      board.setTitle(multi.getParameter("title"));
      board.setContent(multi.getParameter("content"));
      board.setIp(request.getRemoteAddr());
      board.setFilename(multi.getFilesystemName("filename"));
      board.setMem_num(user_num);
      
      OBoardDAO dao = OBoardDAO.getInstance();
      dao.insertBoard(board);
      
      return "/WEB-INF/views/board/oWrite.jsp";
      }

   }
      
   