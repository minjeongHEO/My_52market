package kr.item.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.oreilly.servlet.MultipartRequest;

import kr.controller.Action;
import kr.item.OItemDAO;
import kr.item.OItemVO;
import kr.util.FileUtil;

public class ItemUpdateAction implements Action{

   @Override
   public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
	 //1. 로그인정보 불러오기   
      HttpSession session = request.getSession();
      Integer user_num = (Integer)session.getAttribute("user_num");
	      //로그인이 되지 않은 경우
	      if(user_num==null) return "redirect:/member/loginForm.do";
	      
	
	//<로그인이 된 경우>      
	      
    //2. MultipartRequest객체 생성=> request에 담긴 데이터를 multi에 넘겨주기
      MultipartRequest multi = FileUtil.createFile(request);
      int item_num = Integer.parseInt(multi.getParameter("item_num"));
  
    //3. DAO객체 생성
      OItemDAO dao = OItemDAO.getInstance();
      
      //수정전 데이터 저장
      OItemVO db_item = dao.getItem(item_num);	//db_item : request에 담겨온 item
      if(user_num!=db_item.getMem_num()) {//로그인한 회원번호와 작성자 회원번호가 불일치
 //        FileUtil.removeFile(request, filename);//업로드된 파일이 있으면 파일 삭제
         return "/WEB-INF/views/common/notice.jsp";
      }
      //로그인한 회원번호와 작성자 회원번호가 일치
      OItemVO item = new OItemVO();
      item.setItem_num(item_num);
      item.setMem_num(user_num);
      item.setState(Integer.parseInt(multi.getParameter("state")));
      item.setCate_num(Integer.parseInt(multi.getParameter("cate_num")));
      item.setTitle(multi.getParameter("title"));
      item.setPrice(Integer.parseInt(multi.getParameter("price")));
      item.setContent(multi.getParameter("content"));
      item.setFilename(multi.getFilesystemName("filename"));
    
      //판매 상품 수정
      dao.updateItem(item);
      
      //전송된 파일이 있을 경우 이전 파일 삭제
      String filename = multi.getFilesystemName("filename");   //request(수정폼)에 담겨온 파일name      
      if(filename != null) {
    	  FileUtil.removeFile(request, db_item.getFilename());
      }
 
      return "/WEB-INF/views/item/itemUpdate.jsp";
   }

}