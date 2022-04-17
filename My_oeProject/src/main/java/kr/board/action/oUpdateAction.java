package kr.board.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.oreilly.servlet.MultipartRequest;

import kr.board.OBoardDAO;
import kr.board.OBoardVO;
import kr.controller.Action;
import kr.util.FileUtil;

public class oUpdateAction implements Action{

   @Override
   public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
      HttpSession session = request.getSession();
      Integer user_num = (Integer)session.getAttribute("user_num");
      if(user_num==null) {//로그인이 되지 않은 경우
         return "redirect:/member/loginForm.do";
      }
      MultipartRequest multi = FileUtil.createFile(request);
      int board_num = Integer.parseInt(multi.getParameter("board_num"));
      String filename = multi.getFilesystemName("filename");
      
      OBoardDAO dao = OBoardDAO.getInstance();
      //수정 전 데이터 
      OBoardVO db_board = dao.getBoard(board_num);
      if(user_num!=db_board.getMem_num()) {//로그인한 회원번호와 작성자 회원번호가 불일치
         FileUtil.removeFile(request, filename);//업로드된 파일이 있으면 파일 삭제
         return "/WEB-INF/views/common/notice.jsp";
      }
      
      //로그인한 회원번호와 작성자 회원번호가 일치
            OBoardVO board = new OBoardVO();
            board.setBoard_num(board_num);
            board.setTitle(multi.getParameter("title"));
            board.setContent(multi.getParameter("content"));
            board.setIp(request.getRemoteAddr());
            board.setFilename(filename);
            
            //글수정
            dao.updateBoard(board);
            
            //전송된 파일이 있을 경우 이전 파일 삭제
            if(filename!=null) {
               FileUtil.removeFile(request, db_board.getFilename());
            }
            
            return "redirect:/board/oDetail.do?board_num="+board_num;
         }

      }