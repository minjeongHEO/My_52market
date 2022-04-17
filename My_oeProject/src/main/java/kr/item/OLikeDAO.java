package kr.item;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import kr.util.DBUtil;
import kr.util.StringUtil;

public class OLikeDAO {
   private static OLikeDAO instance = new OLikeDAO();
   
   public static OLikeDAO getInstance() {
      return instance;
   }
   private OLikeDAO() {}
   
   
   
   
   //찜하기 등록 
   public void insertLike(int item_num, int mem_num)throws Exception{
      Connection conn = null;
      PreparedStatement pstmt = null;
      String sql = null;
      
      try {
         //커넥션풀로부터 커넥션 할당 
         conn = DBUtil.getConnection();
         //SQL문 작성
         sql = "INSERT INTO olike (like_num, item_num, mem_num) VALUES (olike_seq.nextval,?,?)";
         //PreparedStatement 객체 생성
         pstmt = conn.prepareStatement(sql);
         //?에 데이터 바인딩
         pstmt.setInt(1, item_num);
         pstmt.setInt(2, mem_num);
         //SQL문 실행
         pstmt.executeUpdate();
         
      }catch(Exception e) {
         throw new Exception(e);
      }finally {
         //자원정리 
         DBUtil.executeClose(null, pstmt, conn);
      }
   }
   
   
   
   
   //찜하기 하트 눌리는거
   public OLikeVO selectLike(int item_num, int mem_num) throws Exception {
	      Connection conn = null;
	      PreparedStatement pstmt = null;
	      ResultSet rs = null;
	      OLikeVO set_like = null;
	      String sql = null;
	      
	      try {
	         //1. 커넥션풀로부터 커넥션을 할당
	         conn = DBUtil.getConnection();
	         //2. SQL문 작성         
	         sql = "SELECT * FROM OLIKE WHERE item_num=? AND mem_num=?";
	         
	         //3. PreparedStatement객체 생성
	         pstmt = conn.prepareStatement(sql);
	         //4. ?에 데이터바인딩
	         pstmt.setInt(1, item_num);
	         pstmt.setInt(2, mem_num);
	         
	         //5. SQL문을 실행해서 결과행을 ResultSet에 담음
	         rs = pstmt.executeQuery();   
	         if(rs.next()) {
	            set_like = new OLikeVO();
	            set_like.setLike_num(rs.getInt("like_num"));
	            set_like.setMem_num(rs.getInt("mem_num"));
	            set_like.setItem_num(rs.getInt("item_num"));
	         }
	      }catch(Exception e) {
	         throw new Exception(e);
	      }finally {
	         DBUtil.executeClose(rs, pstmt, conn);
	      }
	      return set_like;
	   }   
   //찜하기 갯수
   public int selectLikeCount(Integer item_num)throws Exception{
	   Connection conn = null;
	   PreparedStatement pstmt = null;
	   ResultSet rs = null;
	   String sql = null; 
	   int count = 0;
	   
	   try {
		 //커넥션풀로부터 커넥션 할당
		conn = DBUtil.getConnection();
		//SQL문 작성
		sql = "SELECT COUNT(*) from olike where item_num=?";
		//PreparedStatement 객체 생성
		pstmt = conn.prepareStatement(sql);
		//?에 데이터 바인딩
		pstmt.setInt(1, item_num);
		//SQL문 실행
		rs = pstmt.executeQuery();
		if(rs.next()) {
			count = rs.getInt(1);
		}
		
	   }catch(Exception e) {
		   throw new Exception(e);
	   }finally {
		   //자원정리
		   DBUtil.executeClose(null, pstmt, conn);
		}
	   
	return count;
	   
   }
   //찜 목록 수
  /* public int getLikeCount(String keyfield,String keyword)throws Exception{
	     Connection conn = null;
		 PreparedStatement pstmt = null;
		 ResultSet rs = null;
		 String sql = null;
		 String sub_sql = "";
		 int count = 0;
		 
		 try {
			 //커넥션풀로부터 커넥션 할당
			 conn = DBUtil.getConnection();
			 
			 if(keyword != null && !"".equals(keyword)) {
				 if(keyfield.equals("1")) sub_sql = "WHERE i.title LIKE ?";
				 else if(keyfield.equals("2")) sub_sql = "WHERE m.id LIKE ?";
				 else if(keyfield.equals("3")) sub_sql = "WHERE i.content LIKE ?";
			 }
			 
			 sql = "SELECT COUNT(*) FROM oitem i JOIN omember m USING(mem_num) " + sub_sql;
			 
			 pstmt = conn.prepareStatement(sql);
			 if(keyword != null && !"".equals(keyword)) {
				 pstmt.setString(1, "%"+keyword+"%");
			 }
			 
			 //SQL문을 실행하고 결과행을 ResultSet에 담음
			 rs = pstmt.executeQuery();
			 if(rs.next()) {
				 count = rs.getInt(1);
			 }	 
		 }catch(Exception e) {
			 throw new Exception(e);
		 }finally {
			 //자원정리
			 DBUtil.executeClose(rs, pstmt, conn);
		 }
		 return count;
	 } 
   
   
   //찜하기 목록
   public List<OItemVO> getListLike(int startRow, int endRow, 
			            String keyfield, String keyword)throws Exception{
	   Connection conn = null;
	   PreparedStatement pstmt = null;
	   ResultSet rs = null;
	   List<OItemVO> list = null;
	   String sql = null;
	   String sub_sql = "";
	   int cnt = 0;
	   
	   try {
		//커넥션풀로부터 커넥션 할당
		conn = DBUtil.getConnection();
		
		 if(keyword != null && !"".equals(keyword)) {
			 if(keyfield.equals("1")) sub_sql = "WHERE i.title LIKE ?";
			 else if(keyfield.equals("2")) sub_sql = "WHERE m.id LIKE ?";
			 else if(keyfield.equals("3")) sub_sql = "WHERE i.content LIKE ?";
		 }
		 sql = "SELECT * FROM (SELECT a.*, rownum rnum FROM "
		 		+ "(SELECT * FROM oitem i JOIN omember m USING(mem_num) "
		 		+ sub_sql + " ORDER BY i.item_num DESC)a) "
		 		+ "WHERE rnum >= ? AND rnum <= ?";
		 
		//PreparedStatement 객체 생성
		 pstmt = conn.prepareStatement(sql);
		 if(keyword != null && !"".equals(keyword)) {
			 pstmt.setString(++cnt, "%"+keyword+"%");
		 }
		 pstmt.setInt(++cnt, startRow);
		 pstmt.setInt(++cnt, endRow);
		 
		//SQL문을 실행해서 결과행들을 ResultSet에 담음
		 rs = pstmt.executeQuery();
		 list = new ArrayList<OItemVO>();
		 while(rs.next()) {
		 OItemVO item = new OItemVO();
		 item.setItem_num(rs.getInt("item_num"));
		 item.setTitle(StringUtil.useBrNoHtml(rs.getString("title")));
		 item.setFilename(rs.getString("filename"));
		 item.setPrice(rs.getInt("price"));
		 
		 //BoardVO를 ArrayList에 저장
		 list.add(item);
		 }
		 
	   }catch(Exception e) {
		   throw new Exception(e);
	   }finally {
		   //자원정리
		   DBUtil.executeClose(rs, pstmt, conn);
	   }
	   return list;
   }
   
  */ 
   
   //찜목록
   public List<OLikeVO> getListLike(int mem_num)throws Exception{
	   	Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<OLikeVO> list = null;
		String sql = null;
		
		try {
			//커넥션풀로부터 커넥션을 할당
			conn = DBUtil.getConnection();
			//SQL문 작성
			sql = "SELECT * FROM olike l JOIN oitem i "
				+ "ON l.item_num = i.item_num WHERE l.mem_num = ? "
				+ "ORDER BY i.item_num ASC";
			//PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			//?에 데이터 바인딩
			pstmt.setInt(1, mem_num);
			//SQL문을 실행해서 결과행들을 ResultSet에 담음
			rs = pstmt.executeQuery();
			
			list = new ArrayList<OLikeVO>();
			while(rs.next()) {
				OLikeVO like = new OLikeVO();
				like.setItem_num(rs.getInt("item_num"));
				like.setLike_num(rs.getInt("like_num"));
				like.setMem_num(rs.getInt("mem_num"));
				
				OItemVO item = new OItemVO();
				item.setTitle(rs.getString("title"));
				item.setPrice(rs.getInt("price"));
				item.setFilename(rs.getString("filename"));
				item.setState(rs.getInt("state"));
				
				//OItemVO를 OLikeVO에 저장
				like.setItem(item);
				
				list.add(like);
			}
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			//자원정리
			DBUtil.executeClose(rs, pstmt, conn);
		}
		return list;
   }
   
   //찜하기 삭제1
   public void deleteLike(int like_num)throws Exception{
      Connection conn = null;
      PreparedStatement pstmt = null;
      String sql = null;
      
      try {
         //커넥션풀로부터 커넥션 할당
         conn = DBUtil.getConnection();
         //SQL문 작성
         sql = "DELETE FROM olike WHERE like_num=?";
         //PreparedStatement 객체 생성
         pstmt = conn.prepareStatement(sql);
         //?에 데이터 바인딩
         pstmt.setInt(1, like_num);
         //SQL문 실행
         pstmt.executeUpdate();
      }catch(Exception e) {
         throw new Exception(e);
      }finally {
         //자원정리
    	  DBUtil.getConnection();
      }
   }   
   
   //찜하기 삭제2(회원번호별)
   public void deleteLikeByItemNum(Integer item_num) throws Exception{
	   Connection conn = null;
	      PreparedStatement pstmt = null;
	      String sql = null;
	      
	      try {
	    	//커넥션풀로부터 커넥션 할당
	         conn = DBUtil.getConnection();
	          //SQL문 작성
	          sql = "DELETE FROM olike WHERE item_num=?";
	          //PreparedStatement 객체 생성
	          pstmt = conn.prepareStatement(sql);
	          //?에 데이터 바인딩
	          pstmt.setInt(1, item_num);
	          //SQL문 실행
	          pstmt.executeUpdate();
	      }catch(Exception e) {
	    	  throw new Exception(e);
	      }finally {
	    	  //자원정리
	    	  DBUtil.executeClose(null, pstmt, conn);
	      }
   }
   
}
   

