package kr.board.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.board.OBoardDAO;
import kr.board.OBoardVO;
import kr.controller.Action;
import kr.util.StringUtil;

public class oDetailAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		//1. 글번호 반환
		int board_num = Integer.parseInt(request.getParameter("board_num"));
		OBoardDAO dao = OBoardDAO.getInstance();
		
		//2. 해당 글번호에 조회수 증가 메서드적용(상세페이지를 누르면 조회수가 ++ 되도록)
		dao.updateReadcount(board_num);
		
		//3. board객체에 해당 글번호의 데이터 담기 
		OBoardVO board = dao.getBoard(board_num);
			
		//4. 제목과 내용에 태그 허용여부 처리
			//제목 - HTML태그를 허용하지 않음
			board.setTitle(StringUtil.useNoHtml(board.getTitle()));
			//내용 - HTML태그를 허용하지 않으면서 줄바꿈 처리
			board.setContent(StringUtil.useBrNoHtml(board.getContent()));
	
		//5. request에 board객체 저장
		request.setAttribute("board", board);
	
		return "/WEB-INF/views/board/oDetail.jsp";
	}
}
