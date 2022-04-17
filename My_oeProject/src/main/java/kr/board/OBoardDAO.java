package kr.board;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import kr.board.OBoardVO;
import kr.util.DBUtil;
import kr.util.DurationFromNow;
import kr.util.StringUtil;


//[ 메서드1. 글등록 : insertBoard( ) ]
//[ 메서드2. 총 레코드 수(검색 레코드 수) : getBoardCount( ) ]
//[ 메서드3. 글 목록 : getListBoard() ]
//[ 메서드4. 글상세 : getBoard() ]
//[ 메서드5. 조회수 증가 : updateReadcount() ]
//[ 메서드6. 글수정 : updateBoard() ]
//[ 메서드7. 파일삭제 : deleteFile() ]
//[ 메서드8. 글삭제 : deleteBoard() ]


/*****댓글*****/
//[댓글 메서드1. 댓글 등록 : insertReplyBoard() ]
//[댓글 메서드2. 댓글 갯수 : getReplyBoardCount() ]
//[댓글 메서드3. 댓글 목록 : getListReplyBoard() ]
//[댓글 메서드4. 댓글 상세 : getReplyBoard() ]
//[댓글 메서드5. 댓글 수정 : updateReplyBoard() ]
//[댓글 메서드6. 댓글 삭제 : deleteReplyBoard() ]


public class OBoardDAO {
   //싱글턴 패턴
   private static OBoardDAO instance = new OBoardDAO();
   public static OBoardDAO getInstance() {
      return instance;
   }
   private OBoardDAO() {}
   
   //글등록
   public void insertBoard(OBoardVO board)throws Exception{
       Connection conn = null;
       PreparedStatement pstmt = null;
       String sql = null;
       
       try {
          //커넥션풀로부터 커넥션을 할당
          conn = DBUtil.getConnection();
          //SQL문 작성
          sql = "INSERT INTO oboard (board_num,title,content,filename,ip,"
              + "mem_num) VALUES (oboard_seq.nextval,?,?,?,?,?)";
          //PreparedStatement 객체 생성
          pstmt = conn.prepareStatement(sql);
          //?에 데이터를 바인딩
          pstmt.setString(1, board.getTitle());
          pstmt.setString(2, board.getContent());
          pstmt.setString(3, board.getFilename());
          pstmt.setString(4, board.getIp());
          pstmt.setInt(5, board.getMem_num());
          //SQL문 실행
          pstmt.executeUpdate();
       }catch(Exception e) {
          throw new Exception(e);
       }finally {
          //자원정리
          DBUtil.executeClose(null, pstmt, conn);
       }
   }
   
   //총 레코드 수(검색 레코드 수)
       public int getBoardCount(String keyfield,String keyword)throws Exception{
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
                if(keyfield.equals("1")) sub_sql = "WHERE b.title LIKE ?";
                else if(keyfield.equals("2")) sub_sql = "WHERE m.mem_id LIKE ?";
                else if(keyfield.equals("3")) sub_sql = "WHERE b.content LIKE ?";
             }
             
             sql = "SELECT COUNT(*) FROM oboard b JOIN omember m USING(mem_num) " + sub_sql;
                
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
       //목록
       public List<OBoardVO> getListBoard(int startRow, int endRow, 
                           String keyfield, String keyword)throws Exception{
          Connection conn = null;
          PreparedStatement pstmt = null;
          ResultSet rs = null;
          List<OBoardVO> list = null;
          String sql = null;
          String sub_sql = "";
          int cnt = 0;
          
          try {
            //커넥션풀로부터 커넥션 할당
             conn = DBUtil.getConnection();
             
             if(keyword != null && !"".equals(keyword)) {
                if(keyfield.equals("1")) sub_sql = "WHERE b.title LIKE ?";
                else if(keyfield.equals("2")) sub_sql = "WHERE m.mem_id LIKE ?";
                else if(keyfield.equals("3")) sub_sql = "WHERE b.content LIKE ?";
             }
             
             sql = "SELECT * FROM (SELECT a.*, rownum rnum FROM "
                    + "(SELECT * FROM oboard b JOIN omember m USING(mem_num) "
                    + sub_sql + " ORDER BY b.board_num DESC)a) "
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
                list = new ArrayList<OBoardVO>();
                while(rs.next()) {
                   OBoardVO board = new OBoardVO();
                   board.setBoard_num(rs.getInt("board_num"));
                  //HTML태그를 허용하지 않음
                   board.setTitle(StringUtil.useNoHtml(rs.getString("title")));
                   board.setHit(rs.getInt("hit"));
                   board.setReg_date(rs.getDate("reg_date"));
                   board.setId(rs.getString("mem_id"));
                   
                   //BoardVO를 ArrayList에 저장
                   list.add(board);
                }
          }catch(Exception e) {
             throw new Exception(e);
          }finally {
            //자원정리
             DBUtil.executeClose(rs, pstmt, conn); 
          }
          return list; 
       }
       //글상세
       public OBoardVO getBoard(int board_num)throws Exception {
          Connection conn = null;
          PreparedStatement pstmt = null;
          ResultSet rs = null;
          OBoardVO board = null;
          String sql = null;
          
          try {
            //커넥션풀로부터 커넥션을 할당
             conn = DBUtil.getConnection();
             //SQL문 작성
             sql = "SELECT * FROM oboard b JOIN omember m "
                + "ON b.mem_num=m.mem_num WHERE b.board_num=?";
             //PreparedStatement 객체 생성
             pstmt = conn.prepareStatement(sql);
             //?에 데이터를 바인딩
             pstmt.setInt(1, board_num);
             //SQL문 실행해서 결과행을 ResultSet에 담음
             rs = pstmt.executeQuery(); 
             if(rs.next()) {
             board = new OBoardVO();
             board.setBoard_num(rs.getInt("board_num"));
             board.setTitle(rs.getString("title"));
             board.setContent(rs.getString("content"));
             board.setHit(rs.getInt("hit"));
             board.setReg_date(rs.getDate("reg_date"));
             board.setModify_date(rs.getDate("modify_date"));
             board.setFilename(rs.getString("filename"));
             board.setMem_num(rs.getInt("mem_num"));
             board.setId(rs.getString("mem_id"));
             }
          }catch(Exception e) {
             throw new Exception(e);
          }finally {
             //자원정리
             DBUtil.executeClose(rs, pstmt, conn);
          }
       return board;
   }
       //조회수 증가
       public void updateReadcount(int board_num)throws Exception{
          Connection conn = null;
          PreparedStatement pstmt = null;
          String sql = null;
          
          try {
             //커넥션풀로부터 커넥션을 할당
             conn = DBUtil.getConnection();
             
             //SQL문 작성
             sql = "UPDATE oboard SET hit=hit+1 WHERE board_num=?";
             //PreparedStatement 객체 생성
             pstmt = conn.prepareStatement(sql);
             //?에 데이터를 바인딩
             pstmt.setInt(1, board_num);
             //SQL문 실행
             pstmt.executeUpdate();
          }catch(Exception e) {
             throw new Exception(e);
          }finally {
             //자원정리
             DBUtil.executeClose(null, pstmt, conn);
          }
       }
       
       //글수정
       public void updateBoard(OBoardVO board)throws Exception{
          Connection conn = null;
          PreparedStatement pstmt = null;
          String sql = null;
          String sub_sql = "";
          int cnt = 0;
          
          try {
             //커넥션풀로부터 커넥션 할당
             conn = DBUtil.getConnection();
             
             if(board.getFilename()!=null) {
                sub_sql = ",filename=?";
             }
             
             sql = "UPDATE oboard SET title=?, content=?, modify_date=SYSDATE" 
                 + sub_sql + ", ip=? WHERE board_num=?";
             //PreparedStatement 객체 생성
             pstmt = conn.prepareStatement(sql);
             pstmt.setString(++cnt, board.getTitle());
             pstmt.setString(++cnt, board.getContent());
             if(board.getFilename()!=null) {
                pstmt.setString(++cnt, board.getFilename());
             }
             pstmt.setString(++cnt, board.getIp());
             pstmt.setInt(++cnt, board.getBoard_num());
             
             //SQL문 실행
             pstmt.executeUpdate();
             
          }catch(Exception e) {
             throw new Exception(e);
          }finally {
             //자원정리
             DBUtil.executeClose(null, pstmt, conn);
          }
       }
       //파일삭제
       public void deleteFile(int board_num)throws Exception{
          Connection conn = null;
          PreparedStatement pstmt = null;
          String sql = null;
          
          try {
             //커넥션풀로부터 커넥션 할당
             conn = DBUtil.getConnection();
             //SQL문 작성
             sql = "UPDATE oboard SET filename='' WHERE board_num=?";
             //PreparedStatement 객체 생성
             pstmt = conn.prepareStatement(sql);
             //?에 데이터 바인딩
             pstmt.setInt(1, board_num);
             
             //SQL문 실행
             pstmt.executeUpdate();          
          }catch(Exception e) {
             throw new Exception(e);
          }finally {
             //자원정리
             DBUtil.executeClose(null, pstmt, conn);
          }
       }
       //글삭제
       public void deleteBoard(int board_num)throws Exception{
          Connection conn = null;
          PreparedStatement pstmt = null;
          PreparedStatement pstmt2 = null;
          String sql = null;
          
          try {
             //커넥션풀로부터 커넥션 할당
             conn = DBUtil.getConnection();
             //오토커밋 해제
             conn.setAutoCommit(false);
             
             //댓글 삭제
             sql = "DELETE FROM oboard_reply WHERE board_num=?";
             //PreparedStatement 객체 생성
             pstmt = conn.prepareStatement(sql);
             //?에 데이터 바인딩
             pstmt.setInt(1, board_num);
             //SQL문 실행
             pstmt.executeUpdate();
             
             //부모글 삭제
             sql = "DELETE FROM oboard WHERE board_num=?";
             //PrepardStatement 객체 생성
             pstmt2 = conn.prepareStatement(sql);
             //?에 데이터 바인딩
             pstmt2.setInt(1, board_num);
             pstmt2.executeUpdate();
             
             //정상적으로 모든 SQL문을 실행
             conn.commit();
          }catch(Exception e) {
             //SQL문이 하나라도 실패하면
             conn.rollback();
             throw new Exception(e);
          }finally {
             //자원정리
             DBUtil.executeClose(null, pstmt2, null);
             DBUtil.executeClose(null, pstmt, conn);
          }
       }    
       //-----------댓글-------------
       //댓글 등록
      public void insertReplyBoard(OBoard_ReplyVO boardReply)throws Exception{
         Connection conn = null;
          PreparedStatement pstmt = null;
          String sql = null;
          
          try {
            //커넥션풀로부터 커넥션을 할당
            conn = DBUtil.getConnection();
            //SQL문 작성
            sql = "INSERT INTO oboard_reply (re_num,re_content,re_ip,"
               + "mem_num,board_num) VALUES (oboardreply_seq.nextval,?,?,?,?)";
            //PreparedStatement 객체 생성
            pstmt = conn.prepareStatement(sql);
            //?에 데이터 바인딩
            pstmt.setString(1, boardReply.getRe_content());
            pstmt.setString(2, boardReply.getRe_ip());
            pstmt.setInt(3, boardReply.getMem_num());
            pstmt.setInt(4, boardReply.getBoard_num());
             
            //SQL문 실행
            pstmt.executeUpdate(); 
            
          }catch(Exception e) {
             throw new Exception(e);
          }finally {
            //자원정리
            DBUtil.executeClose(null, pstmt, conn);
          }
      }
      //댓글 갯수
      public int getReplyBoardCount(int board_num)throws Exception{
          Connection conn = null;
          PreparedStatement pstmt = null;
          ResultSet rs = null;
          String sql = null;
          int count = 0;
          
          try {
            //커넥션풀로부터 커넥션 할당
             conn = DBUtil.getConnection();
             //SQL문 작성
             sql = "SELECT COUNT(*) FROM oboard_reply b "
                 + "JOIN omember m USING(mem_num) WHERE b.board_num=?";
             
             //PreparedStatement 객체 생성
             pstmt = conn.prepareStatement(sql);
             //?에 데이터 바인딩
             pstmt.setInt(1, board_num); 
            //SQL문을 실행해서 결과행을 ResultSet에 담음
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
      //댓글 목록
      public List<OBoard_ReplyVO>getListReplyBoard(int startRow, int endRow, int board_num)
                throws Exception{
         Connection conn = null;
          PreparedStatement pstmt = null;
          ResultSet rs = null;
          List<OBoard_ReplyVO> list = null;
          String sql = null;
          
          try {
             //커넥션풀로부터 커넥션 할당
             conn = DBUtil.getConnection();
             //SQL문 작성
             sql = "SELECT * FROM (SELECT a.*, rownum rnum FROM "
                + "(SELECT b.re_num, TO_CHAR(b.re_date,'YYYY-MM-DD HH24:MI:SS') re_date,"
                + "TO_CHAR(b.re_modifydate,'YYYY-MM-DD HH24:MI:SS') re_modifydate,"
                + "b.re_content,b.board_num,mem_num,m.mem_id FROM oboard_reply b "
                + "JOIN omember m USING(mem_num) WHERE b.board_num=? "
                + "ORDER BY b.re_num DESC)a) "
                + "WHERE rnum >= ? AND rnum <= ?";
             
             //PreparedStatement 객체 생성
             pstmt = conn.prepareStatement(sql);
             //?에 데이터 바인딩
             pstmt.setInt(1, board_num);
             pstmt.setInt(2, startRow);
             pstmt.setInt(3, endRow);
             
             //SQL문을 실행해서 결과행들을 ResultSet에 담음
             rs = pstmt.executeQuery(); 
             list = new ArrayList<OBoard_ReplyVO>();
             while(rs.next()) {
                OBoard_ReplyVO reply = new OBoard_ReplyVO();
                reply.setRe_num(rs.getInt("re_num"));
               //날짜 -> 1분전, 1시간전, 1일전 형식의 문자열로 변환
                reply.setRe_date(DurationFromNow.getTimeDiffLabel(rs.getString("re_date")));
                if(rs.getString("re_modifydate")!=null) {
                   reply.setRe_modifydate(DurationFromNow.getTimeDiffLabel(
                                                 rs.getString("re_modifydate")));
                }
                reply.setRe_content(StringUtil.useBrNoHtml(rs.getString("re_content")));
                reply.setBoard_num(rs.getInt("board_num"));
                reply.setMem_num(rs.getInt("mem_num"));
                reply.setId(rs.getString("mem_id"));
                
                list.add(reply); 
             }
          }catch(Exception e) {
             throw new Exception(e);
          }finally {
             //자원정리
             DBUtil.executeClose(rs, pstmt, conn);
          }
          return list;    
      }
      //댓글 삭제
      public OBoard_ReplyVO getReplyBoard(int re_num)throws Exception{
          Connection conn = null;
          PreparedStatement pstmt = null;
          ResultSet rs = null;
          OBoard_ReplyVO reply = null;
          String sql = null;
         
          try {
             //커넥션풀로부터 커넥션을 할당
             conn = DBUtil.getConnection();
             //SQL문 작성
             sql = "SELECT * FROM oboard_reply r JOIN omember USING(mem_num) WHERE re_num = ?";
             //PreparedStament 객체 생성
             pstmt = conn.prepareStatement(sql);
             //?에 데이터 바인딩
             pstmt.setInt(1, re_num);
             //SQL문을 실행해서 결과행을 ResultSet에 담음
             rs = pstmt.executeQuery();
             if(rs.next()) {
                reply = new OBoard_ReplyVO();
                reply.setRe_num(rs.getInt("re_num"));
                reply.setBoard_num(rs.getInt("board_num"));
                reply.setMem_num(rs.getInt("mem_num"));
                reply.setId(rs.getString("mem_id"));
             }
          }catch(Exception e) {
             throw new Exception(e);
          }finally{
             //자원정리
             DBUtil.executeClose(rs, pstmt, conn);
          }
          return reply;
       }
      //댓글 수정
       public void updateReplyBoard(OBoard_ReplyVO reply)throws Exception{
          Connection conn = null;
          PreparedStatement pstmt = null;
          String sql = null;
          
          try {
             //커넥션풀로부터 커넥션을 할당
             conn = DBUtil.getConnection();
             //SQL문 작성
             sql = "UPDATE oboard_reply SET re_content=?, "
                + "re_modifydate=SYSDATE, re_ip=? WHERE re_num=?";
             //PreparedStatement 객체 생성
             pstmt = conn.prepareStatement(sql);
             //?에 데이터를 바인딩
             pstmt.setString(1, reply.getRe_content());
             pstmt.setString(2, reply.getRe_ip());
             pstmt.setInt(3, reply.getRe_num());
             
             //SQL문을 실행
             pstmt.executeUpdate();
             
          }catch(Exception e) {
             throw new Exception(e);
          }finally {
             //자원 정리
             DBUtil.executeClose(null, pstmt, conn);
          }
       }
      //댓글 삭제
       public void deleteReplyBoard(int re_num)throws Exception{
          Connection conn = null;
          PreparedStatement pstmt = null;
          String sql = null;
          
          try {
             //커넥션풀로부터 커넥션 할당
             conn = DBUtil.getConnection();
             //SQL문 작성
             sql = "DELETE FROM oboard_reply WHERE re_num=?";
             //PreparedStatement 객체 생성
             pstmt = conn.prepareStatement(sql);
             //?에 데이터 바인딩
             pstmt.setInt(1, re_num);
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