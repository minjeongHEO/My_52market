package kr.item.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.oreilly.servlet.MultipartRequest;

import kr.controller.Action;
import kr.item.OItemDAO;
import kr.item.OItemVO;
import kr.util.FileUtil;

public class ItemWriteAction implements Action{  

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		//인코딩처리
		request.setCharacterEncoding("utf-8");
		
		HttpSession session = request.getSession();
		Integer user_num = (Integer)session.getAttribute("user_num");
		if(user_num == null) {//로그인 되지 않은 경우
			return "redirect:/member/loginForm.do";
		}
		
		//로그인이 된경우
		MultipartRequest multi = FileUtil.createFile(request);
		OItemVO item = new OItemVO();
		item.setCate_num(Integer.parseInt(multi.getParameter("cate_num")));
		item.setMem_num(user_num);   //회원번호
		item.setTitle(multi.getParameter("title"));
		item.setPrice(Integer.parseInt(multi.getParameter("price")));
		item.setFilename(multi.getFilesystemName("filename"));
		item.setContent(multi.getParameter("content"));
		
		OItemDAO dao = OItemDAO.getInstance();
		dao.insertItem(item);
		
		return "/WEB-INF/views/item/itemWrite.jsp";
		
	}

}
