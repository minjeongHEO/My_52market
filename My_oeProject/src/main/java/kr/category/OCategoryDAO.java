package kr.category;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import kr.util.DBUtil;

public class OCategoryDAO {
   //싱글턴 패턴
   private static OCategoryDAO instance = new OCategoryDAO();
   
   public static OCategoryDAO getInstance() {
      return instance;
   }
   private OCategoryDAO() {}
   
   //**운영자 - 카테고리 등록(insertReplyItem()댓글 메서드 참고함)
   public void insertCate(OCategoryVO cate)throws Exception{
      Connection conn = null;
      PreparedStatement pstmt = null;
      String sql = null;
      
      try {
         //커넥션풀로부터 커넥션 할당
         conn = DBUtil.getConnection();
         //SQL문 작성
         sql = "INSERT INTO ocategory(cate_num, cate_name, cate_status) VALUES (ocategory_seq.nextval,?,?)";
         //PreparedStatement 객체 생성
         pstmt = conn.prepareStatement(sql);
         //?에 데이터 바인딩
         pstmt.setString(1, cate.getCate_name());
         pstmt.setInt(2, cate.getCate_status());
         
         //SQL문 실행
         pstmt.executeUpdate();
         
      }catch(Exception e) {
         throw new Exception(e);
      }finally {
         //자원정리
         DBUtil.executeClose(null, pstmt, conn);
      }
      
   }

   //[ 댓글 메서드2. 댓글 갯수 : getReplyItemCount() ]참고
   // - 카테고리갯수
      public int getCateCount()throws Exception{
         Connection conn = null;
         PreparedStatement pstmt = null;
         ResultSet rs = null;
         String sql = null;
         int count = 0;
         
         try {
            //1. ConnectionPool로부터 커넥션 할당
            conn = DBUtil.getConnection();
            //2. SQL문 작성
            sql = "SELECT COUNT(*) FROM ocategory" ;
                  
            //3. PreparedStatement객체 생성
            pstmt = conn.prepareStatement(sql);

            //4. sql문을 실행해서 결과행을 ResultSet에 담음
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
      
   
   
   //[ 댓글 메서드3. 댓글 목록 : getListReplyItem() ]참고
   // 부모글이 같은 댓글을 읽어오니까 인자에 BOARD_NUM을 추가하는거 카테는 부모글없으니 빼고무관???
   //**운영자 - 카테고리 목록(insertReplyItem()댓글 메서드 참고함)
   //public List<OCategoryVO> getListCate(int startRow, int endRow, int item_num)throws Exception{
   public List<OCategoryVO> getListCate(int startRow, int endRow)throws Exception{
      Connection conn = null;
      PreparedStatement pstmt = null;
      ResultSet rs = null;
      List<OCategoryVO> list = null;
      String sql = null;
      
      try {
         //1. ConnectionPool로부터 커넥션할당
         conn = DBUtil.getConnection();
         //2. sql문 작성
         /*
         sql = "SELECT * FROM (SELECT a.*, rownum rnum FROM "
               + "(SELECT r.re_num, TO_CHAR(r.re_date,'YYYY-MM-DD HH24:MI:SS') re_date,"
               + "TO_CHAR(r.re_modifydate,'YYYY-MM-DD HH24:MI:SS') re_modifydate,"
               + "r.re_content,r.item_num,mem_num,m.mem_id FROM oitem_reply r "   
                              //mem_num은 USING을 쓸거기때문에 테이블알리아스(b.mem_num)을 쓰면 안됨 / ON을 쓸 때는 알리아스 넣어줘야함
               + "JOIN omember m USING(mem_num) WHERE r.item_num=? "
               + "ORDER BY r.re_num DESC)a) "
               + "WHERE rnum>=? AND rnum<= ?";
         */
         //조인안해도될꺼같은데 그냥이름순으로 정렬하자.. //여기서 DISTINCT쓰지말고 애초에 카테명 입력받을 때 중복값못쓰게하자
            sql = "SELECT * FROM (SELECT a.*, rownum rnum FROM "
                  + "(SELECT  cate_num, cate_name, cate_status "
                  + "FROM ocategory ORDER BY cate_name ASC)a) "
                  + "WHERE rnum>=? AND rnum<=?";
         
         //3. psmt객체 생성
         pstmt = conn.prepareStatement(sql);
         //4. ?에 데이터바인딩
         pstmt.setInt(1, startRow);
         pstmt.setInt(2, endRow);
         
         //5. sql문장을 수행해서 결과행들을 ResultSet에 담음
         rs = pstmt.executeQuery();
         list = new ArrayList<OCategoryVO>();
         while(rs.next()) {
            OCategoryVO cate = new OCategoryVO();
            
            cate.setCate_name(rs.getString("cate_name"));
            cate.setCate_status(rs.getInt("cate_status"));
            cate.setCate_num(rs.getInt("cate_num"));
            
            list.add(cate);
         }   
         
      }catch(Exception e) {
         throw new Exception(e);
      }finally {
         //자원정리
         DBUtil.executeClose(rs, pstmt, conn);
      }      
      return list;
   }   
   
   public OCategoryVO getCategory(int cate_num)throws Exception{
       Connection conn = null;
       PreparedStatement pstmt = null;
       ResultSet rs = null;
       OCategoryVO vo = null;
       String sql = null;
       int count = 0;
       
       try {
          //1. ConnectionPool로부터 커넥션 할당
          conn = DBUtil.getConnection();
          //2. SQL문 작성
          sql = "SELECT * FROM ocategory WHERE cate_num=?" ;
                
          //3. PreparedStatement객체 생성
          pstmt = conn.prepareStatement(sql);
          pstmt.setInt(1, cate_num);

          //4. sql문을 실행해서 결과행을 ResultSet에 담음
          rs = pstmt.executeQuery();

          if(rs.next()) {
             vo = new OCategoryVO();
             vo.setCate_num(rs.getInt("cate_num"));
             vo.setCate_name(rs.getNString("cate_name"));
          }
       }catch(Exception e) {
          throw new Exception(e);
       }finally {
          //자원정리
          DBUtil.executeClose(rs, pstmt, conn);
       }
       return vo;
    }   
   
   
   //** 카테고리 목록(insertReplyItem()댓글 메서드 참고함)
   public List<OCategoryVO> getListCateMenu()throws Exception{
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
      List<OCategoryVO> list = null;
      String sql = null;
      
      try {
         //1. ConnectionPool로부터 커넥션할당
         conn = DBUtil.getConnection();
         //2. sql문 작성
            sql = "SELECT * FROM ocategory WHERE cate_status=1 ORDER BY cate_name";
         //3. pstmt객체 생성
         pstmt = conn.prepareStatement(sql);
        
         //4. sql문장을 수행해서 결과행들을 ResultSet에 담음
         rs = pstmt.executeQuery();
         list = new ArrayList<OCategoryVO>();
         while(rs.next()) {
            OCategoryVO cate = new OCategoryVO();
            
            cate.setCate_name(rs.getString("cate_name"));
            cate.setCate_status(rs.getInt("cate_status"));
            cate.setCate_num(rs.getInt("cate_num"));
            
            list.add(cate);
         }   
         
      }catch(Exception e) {
         throw new Exception(e);
      }finally {
         //자원정리
         DBUtil.executeClose(rs, pstmt, conn);
      }      
      return list;
   }   
   
   
   /* 카테고리 삭제 */
   public void deleteCate(int cate_num)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
	
		try {
			//1. 커넥션풀로부터 커넥션 할당
			conn = DBUtil.getConnection();
			//2. sql문장 작성
			sql = "DELETE FROM OCATEGORY WHERE cate_num=?";
			//3. pstmt객체 생성 
			pstmt = conn.prepareStatement(sql);
			//4. ?에 데이터 바인딩
			pstmt.setInt(1, cate_num);
			//5. sql문 실행
			pstmt.executeUpdate();
			
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			//자원정리
			DBUtil.executeClose(null, pstmt, conn);
		}
	}
   
   /*카테고리 수정*/
   public void updateCate(OCategoryVO cate)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		
		try {
			//1. ConnectionPool로부터 Connection할당
			conn = DBUtil.getConnection();
			//2. sql문 작성
			sql="UPDATE OCATEGORY SET cate_name=?, cate_status=? WHERE cate_num=?";
			//3. pstmt객체 생성
			pstmt = conn.prepareStatement(sql);
			//4. ?에 데이터 바인딩
			pstmt.setString(1, cate.getCate_name());
			pstmt.setInt(2, cate.getCate_status());
			pstmt.setInt(3, cate.getCate_num());
			//5. sql문 실행
			pstmt.executeUpdate();
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.getConnection();
		}
   }
   
   
   
}