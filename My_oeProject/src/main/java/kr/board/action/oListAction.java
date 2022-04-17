package kr.board.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.board.OBoardDAO;
import kr.board.OBoardVO;
import kr.controller.Action;
import kr.util.PagingUtil;

public class oListAction implements Action{

   @Override
   public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
      
      String pageNum = request.getParameter("pageNum");
      if(pageNum == null) pageNum = "1";
      
      String keyfield = request.getParameter("keyfield");
      String keyword = request.getParameter("keyword");
      
      OBoardDAO dao = OBoardDAO.getInstance();
      int count = dao.getBoardCount(keyfield, keyword);
      
      //페이지 처리
      //keyfield,keyword,currentPage,count,rowCount,pageCount,url
      PagingUtil page = new PagingUtil(keyfield,keyword,
                      Integer.parseInt(pageNum),count,20,10,"oList.do");
      
      List<OBoardVO> list = null;
      if(count > 0) {
         list = dao.getListBoard(page.getStartCount(), page.getEndCount(), 
                                                    keyfield, keyword);
      }
      request.setAttribute("count", count);
      request.setAttribute("list", list);
      request.setAttribute("pagingHtml", page.getPagingHtml());
      
      //JSP 경로 반환
      return "/WEB-INF/views/board/oList.jsp";
   }

}