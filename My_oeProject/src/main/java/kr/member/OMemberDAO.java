package kr.member;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import kr.member.OMemberDAO;
import kr.member.OMemberVO;
import kr.util.DBUtil;

public class OMemberDAO {
//test
	//싱글턴 패턴
	private static OMemberDAO instance = new OMemberDAO();
	public static OMemberDAO getInstance() {
		return instance;
	}
	private OMemberDAO() {}	
	
		// 찬미
		// [메서드1. 회원가입 : inseretMember() : 로그인 + 아이디 중복체크]
		public void insertMember(OMemberVO member)throws Exception{
			Connection conn = null;
			PreparedStatement pstmt = null;
			PreparedStatement pstmt2 = null;
			PreparedStatement pstmt3 = null;
			ResultSet rs = null;
			String sql = null;
			int num = 0; //시퀀스 번호 저장
			
			try {
				//커넥션풀로부터 커넥션 할당
				conn = DBUtil.getConnection();
				//★ 오토 커밋 해제(sql문이 2개 이상일때)
				conn.setAutoCommit(false);
				
				//회원번호(mem_num) 생성
				sql = "SELECT omember_seq.nextval FROM dual"; //동일한 시퀀스를 구하기 위해 별도의 sql문
				//PreparedStatement객체 생성
				pstmt = conn.prepareStatement(sql);
				//sql문을 실행시켜 결과행을 rs담음
				rs = pstmt.executeQuery();
				if(rs.next()) {
					num = rs.getInt(1);
				}
				 
				//omember테이블에 데이터 저장(auth값은 디폴트값 1로 들어가므로 생략o)
				sql = "INSERT INTO omember (mem_num,mem_id) VALUES (?,?)";
				//PreparedStatement객체 생성
				pstmt2 = conn.prepareStatement(sql);
				//?에 데이터 바인딩
				pstmt2.setInt(1, num);
				pstmt2.setString(2, member.getMem_id());
				//sql문 실행
				pstmt2.executeUpdate();
				
				//omember_detail 테이블에 데이터 저장
				sql = "INSERT INTO omember_detail (mem_num,mem_nick,mem_pw,mem_phone,mem_email,"
						+ "mem_zipcode,mem_addr,mem_addr2) VALUES (?,?,?,?,?,?,?,?)";
				//PreparedStatement객체 생성
				pstmt3 = conn.prepareStatement(sql);
				//?에 데이터 바인딩
				pstmt3.setInt(1, num);
				pstmt3.setString(2, member.getMem_nick());
				pstmt3.setString(3, member.getMem_pw());
				pstmt3.setString(4, member.getMem_phone());
				pstmt3.setString(5, member.getMem_email());
				pstmt3.setString(6, member.getMem_zipcode());
				pstmt3.setString(7, member.getMem_addr());
				pstmt3.setString(8, member.getMem_addr2());
				
				//sql문 실행
				pstmt3.executeUpdate();
				
				//★ sql문 실행시 모두 성공하면 commit
				conn.commit();
				
			}catch(Exception e) {
				//★ sql문이 하나라도 실패하면 롤백(rollback)작업
				conn.rollback();
				
				throw new Exception(e);
			}finally {
				//자원정리
				DBUtil.executeClose(null, pstmt3, null);
				DBUtil.executeClose(null, pstmt2, null);
				DBUtil.executeClose(rs, pstmt, conn);
			}
		}
		
		// [메서드2. ID중복체크 및 로그인 처리 :  checkMember() : 로그인 + 아이디 중복체크]
		public OMemberVO checkMember(String mem_id)throws Exception{
			Connection conn = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			OMemberVO member = null;
			String sql = null;
			
			try {
				//1.커넥션풀로부터 커넥션 할당
				conn = DBUtil.getConnection();
				
				//2.sql문 작성
				//omember와 omember_detail테이블을 조인. omember의 누락된 데이터가 보여야
				//id중복 체크 가능
				sql = "SELECT * FROM omember m LEFT OUTER JOIN omember_detail d "
						+ "ON m.mem_num=d.mem_num WHERE m.mem_id=?";
				
				//3.PreparedStatement객체 생성
				pstmt = conn.prepareStatement(sql);
				
				//4.?에 데이터 바인딩
				pstmt.setString(1, mem_id);
				
				//5.sql문을 실행시켜 결과행을 rs에 담아 반환
				rs = pstmt.executeQuery();
				
				if(rs.next()) {
					member = new OMemberVO();
					
					member.setMem_num(rs.getInt("mem_num"));
					member.setMem_id(rs.getString("mem_id"));
					member.setMem_nick(rs.getString("mem_nick")); //nick = name이름
					member.setMem_auth(rs.getInt("mem_auth"));
					member.setMem_pw(rs.getString("mem_pw"));
					member.setMem_photo(rs.getString("mem_photo"));
				}
				
			}catch(Exception e) {
				throw new Exception(e);
			}finally {
				//자원정리
				DBUtil.executeClose(rs, pstmt, conn);
			}
			return member;
		}
	
		// 다원
		// [메서드3. ID찾기 : checkName(String Name) // name = mem_nick]	
		public OMemberVO checkName(String name)throws Exception{
				Connection conn = null;
				PreparedStatement pstmt = null;
				ResultSet rs = null;
				OMemberVO member = null;
				String sql = null;
				
				try {
					//1.커넥션풀로부터 커넥션 할당
					conn = DBUtil.getConnection();
					
					//2.sql문 작성
					//omember와 omember_detail테이블을 조인. omember의 누락된 데이터가 보여야
					//id중복 체크 가능
					sql = "SELECT * FROM omember m LEFT OUTER JOIN omember_detail d "
							+ "ON m.mem_num=d.mem_num WHERE d.mem_nick=?";
					
					//3.PreparedStatement객체 생성
					pstmt = conn.prepareStatement(sql);
					
					//4.?에 데이터 바인딩
					pstmt.setString(1, member.getMem_nick());
					
					//5.sql문을 실행시켜 결과행을 rs에 담아 반환
					rs = pstmt.executeQuery();				
					if(rs.next()) {
						member = new OMemberVO();
						member.setMem_nick(rs.getString("mem_nick")); //nick = name이름
						member.setMem_id(rs.getString("mem_id"));
						member.setMem_phone(rs.getString("mem_phone"));
						member.setMem_auth(rs.getInt("mem_auth"));
					}			
				}catch(Exception e) {
					throw new Exception(e);
				}finally {
					//자원정리
					DBUtil.executeClose(rs, pstmt, conn);
				}
				return member;	//member객체에 nick(이름), id(id), phone(phone), auth 데이터 담김
			}
			

		// [메서드4. PW찾기 : findPwMember() ]
	

	// [메서드5. 회원 상세정보 : getMember() ]	
	public OMemberVO getMember(int mem_num)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		OMemberVO member = null;
		String sql = null;
		
		try {
			//1. 커넥션풀로부터 커넥션 할당
			conn = DBUtil.getConnection();
			//2. SQL문 작성 (테이블 2개를 JOIN해서 데이터를 읽어와야한다.)
			sql = "SELECT * FROM omember m JOIN omember_detail d ON m.mem_num=d.mem_num"
					+ " WHERE m.mem_num=?";
			//3. pstmt객체생성 + ?에 데이터바인딩
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, mem_num);
			
			//4. SQL문을 실행해서 결과행을 ResultSet에 담음 + VO객체 생성 후 rs에 담긴 데이터를 VO에 넣어주기
			rs = pstmt.executeQuery();
			if(rs.next()) {	//mem_num의 행은 1개 => if
				member = new OMemberVO();
				member.setMem_num(rs.getInt("mem_num"));			//회원번호
				member.setMem_id(rs.getString("mem_id"));			//아이디
				member.setMem_auth(rs.getInt("mem_auth"));			//등급
				member.setMem_nick(rs.getString("mem_nick"));		//이름
				member.setMem_pw(rs.getString("mem_pw"));			//비밀번호
				member.setMem_phone(rs.getString("mem_phone"));		//연락처
				member.setMem_photo(rs.getString("mem_photo"));		//사진파일명
				member.setMem_addr(rs.getString("mem_addr"));		//주소
				member.setMem_addr2(rs.getString("mem_addr2"));		//나머지주소
				member.setMem_zipcode(rs.getString("mem_zipcode"));	//우편번호
				member.setMem_email(rs.getString("mem_email"));		//이메일
				member.setMem_date(rs.getDate("mem_date"));			//가입일
				member.setMem_modifydate(rs.getDate("mem_modifydate"));	//변경일
			}		
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}		
		return member;
	}
	
	// 진주
	// [메서드6. 회원정보 수정 : updateMember()]
	public void updateMember(OMemberVO member)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;

		try {
			//커넥션풀로부터 커넥션을 할당
			conn = DBUtil.getConnection();
			sql = "UPDATE omember_detail SET mem_nick=?, mem_phone=?, mem_email=?,"
					+ "mem_zipcode=?, mem_addr=?, mem_addr2=? ,mem_modifydate=SYSDATE "
					+ "WHERE mem_num=?";
			//PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			//?에 데이터 바인딩
			pstmt.setString(1, member.getMem_nick());
			pstmt.setString(2, member.getMem_phone());
			pstmt.setString(3, member.getMem_email());
			pstmt.setString(4, member.getMem_zipcode());
			pstmt.setString(5, member.getMem_addr());
			pstmt.setString(6, member.getMem_addr2());
			pstmt.setInt(7, member.getMem_num());

			//SQL문 실행
			pstmt.executeUpdate();

		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			//자원정리
			DBUtil.executeClose(null, pstmt, conn);
		}
	}

	// 민정
	// [메서드7. 비밀번호 수정 : ] by.민정
		public void updatePassword(String mem_pw, int mem_num)throws Exception{
			Connection conn = null;
			PreparedStatement pstmt = null;
			String sql = null;
			
			try {
				//커넥션 풀로부터 커넥션할당
				conn = DBUtil.getConnection();
				//SQL문 생성
				sql = "UPDATE omember_detail SET mem_pw=? WHERE mem_num=?";
				//PreparedStatement객체 생성
				pstmt = conn.prepareStatement(sql);
				//?에 데이터 할당
				pstmt.setString(1, mem_pw); //새 비밀번호
				pstmt.setInt(2, mem_num); //회원번호
				//SQL문 실행
				pstmt.executeUpdate();
				
			} catch (Exception e) {
				throw new Exception(e);
			}finally{
				DBUtil.executeClose(null, pstmt, conn);
				
			}
		}
		
	// [메서드8. 프로필사진 수정 : ] by.민정
	public void updateMyPhoto(String mem_photo, int mem_num)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		
		try {
			//커넥션 풀로부터 커넥션할당
			conn = DBUtil.getConnection(); //커넥션 풀로부터 커넥션을 할당
			//SQL문 생성
			sql = "UPDATE omember_detail SET mem_photo=? WHERE mem_num=?";
			//PreparedStatement객체 생성
			pstmt = conn.prepareStatement(sql); 
			//?에 데이터 할당
			pstmt.setString(1, mem_photo);
			pstmt.setInt(2, mem_num);
			//SQL문 실행
			pstmt.executeUpdate(); 
			
		} catch (Exception e) {
			throw new Exception(e);
		}finally {
			//자원정리
			DBUtil.executeClose(null, pstmt, conn);
		}
	}
	
	// 진주
	// [메서드9. 회원탈퇴(회원정보 삭제) : ]
	public void deleteMember(int mem_num)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		PreparedStatement pstmt2 = null;
		String sql = null;

		try {
			//커넥션 풀로부터 커넥션할당
			conn = DBUtil.getConnection();
			//auto commit 해제 
			conn.setAutoCommit(false);

			//omember의 auth 값 변경
			sql ="UPDATE omember SET mem_auth=0 WHERE mem_num=?";
			//PreparedStatement객체 생성
			pstmt = conn.prepareStatement(sql);
			//?에 데이터 바인딩
			pstmt.setInt(1, mem_num);
			//sql 실행
			pstmt.executeUpdate();

			//omember_detail의 레코드 삭제
			sql = "DELETE FROM omember_detail WHERE mem_num=?";
			//PreparedStatement객체 생성
			pstmt2 = conn.prepareStatement(sql);
			//?에 데이터 바인딩
			pstmt2.setInt(1, mem_num);
			//SQL문 실행
			pstmt2.executeUpdate();

			//모든 SQL문의 실행이 성공하면 commit
			conn.commit();

		}catch(Exception e) {
			//SQL문이 하나라도 실패하면 rollback
			conn.rollback();
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(null, pstmt2, conn);
			DBUtil.executeClose(null, pstmt, conn);
		}
	}		

	// [관리자-메서드8. 총 회원 수 : ]
	public int getMemberCountByAdmin(String keyfield, String keyword)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		String sub_sql = "";
		int count = 0;
		
		try {
			//커넥션 풀로부터 커넥션할당
			conn = DBUtil.getConnection();
			
			if(keyword != null && !"".equals(keyword)) {
				//검색글 처리
				if(keyfield.equals("1")) sub_sql = "WHERE mem_id LIKE ?";
				else if(keyfield.equals("2")) sub_sql = "WHERE mem_nick LIKE ?"; //mem_nick -> 이름
				else if(keyfield.equals("3")) sub_sql = "WHERE mem_auth LIKE ?";
			}
			
			//전체 또는 검색 레코드 갯수
			sql = "SELECT COUNT(*) FROM omember m "
				+ "LEFT OUTER JOIN omember_detail d USING(mem_num) " + sub_sql;
			//PreparedStatement객체 생성
			pstmt = conn.prepareStatement(sql);
			if(keyword != null && !"".equals(keyword)) {
				pstmt.setString(1, "%" + keyword + "%");
			}
			//SQL문을 실행시켜 결과행을 ResultSet에 담음
			rs = pstmt.executeQuery();
			if(rs.next()) {
				count = rs.getInt(1);
			}
			
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}	
		return count;
	}
	// [관리자-메서드9. 회원 목록 : ]
	public List<OMemberVO> getListMemberByAdmin(int startRow, int endRow, 
			String keyfield, String keyword)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<OMemberVO> list = null;
		String sql = null;
		String sub_sql = "";
		int cnt = 0;

		try {
			//커넥션 풀로부터 커넥션할당
			conn = DBUtil.getConnection();

			if(keyword != null && !"".equals(keyword)) {
				//검색글 처리
				if(keyfield.equals("1")) sub_sql = "WHERE mem_id LIKE ?";
				else if(keyfield.equals("2")) sub_sql = "WHERE mem_nick LIKE ?"; //mem_nick -> 이름
				else if(keyfield.equals("3")) sub_sql = "WHERE mem_auth LIKE ?";
			}

			//SQL문 작성
			sql = "SELECT * FROM (SELECT a.*, rownum rnum FROM "
					+ "(SELECT * FROM omember m LEFT OUTER JOIN omember_detail d "
					+ "USING(mem_num) " + sub_sql + " ORDER BY mem_date DESC NULLS LAST)a) "
					+ "WHERE rnum >= ? AND rnum <= ?";
			//PreparedStatement객체 생성
			pstmt = conn.prepareStatement(sql);
			if(keyword != null && !"".equals(keyword)) {
				pstmt.setString(++cnt, "%" + keyword + "%");
			}
			pstmt.setInt(++cnt, startRow);
			pstmt.setInt(++cnt, endRow);

			//SQL문을 실행시켜 결과행을 ResultSet에 담음
			rs = pstmt.executeQuery();
			list = new ArrayList<OMemberVO>();
			while(rs.next()) {
				OMemberVO member = new OMemberVO();
				member.setMem_num(rs.getInt("mem_num"));
				member.setMem_id(rs.getString("mem_id"));
				member.setMem_auth(rs.getInt("mem_auth"));
				member.setMem_nick(rs.getString("mem_nick")); //이름
				member.setMem_pw(rs.getString("mem_pw"));
				member.setMem_phone(rs.getString("mem_phone"));
				member.setMem_email(rs.getString("mem_email"));
				member.setMem_zipcode(rs.getString("mem_zipcode"));
				member.setMem_addr(rs.getString("mem_addr"));
				member.setMem_addr2(rs.getString("mem_addr2"));
				member.setMem_photo(rs.getString("mem_photo"));
				member.setMem_date(rs.getDate("mem_date"));
				member.setMem_modifydate(rs.getDate("mem_modifydate"));

				//자바빈(vo) arraylist에 저장
				list.add(member);
			}

		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
		return list;		
	}
	// [관리자-메서드10. 회원정보 수정 : ]
	public void updateMemberByAdmin(OMemberVO member)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		PreparedStatement pstmt2 = null;
		String sql = null; 
		
		try {
			//커넥션 풀로부터 커넥션할당
			conn = DBUtil.getConnection();
			//오토커밋 해제
			conn.setAutoCommit(false);
			
			//SQL문 작성
			sql = "UPDATE omember SET mem_auth=? WHERE mem_num=?";
			//PreparedStatement객체 생성
			pstmt = conn.prepareStatement(sql);
			//?에 데이터 바인딩
			pstmt.setInt(1, member.getMem_auth());
			pstmt.setInt(2, member.getMem_num());
			//SQL문 실행
			pstmt.executeUpdate();
			
			//SQL문 작성
			sql ="UPDATE omember_detail SET mem_nick=?, mem_phone=?, mem_email=?, "
				+ "mem_zipcode=?, mem_addr=?, mem_addr2=?, mem_modifydate=SYSDATE "
				+ "WHERE mem_num=?";
			//PreparedStatement2 객체 생성
			pstmt2 = conn.prepareStatement(sql);
			//?에 데이터 바인딩
			pstmt2.setString(1, member.getMem_nick());
			pstmt2.setString(2, member.getMem_phone());
			pstmt2.setString(3, member.getMem_email());
			pstmt2.setString(4, member.getMem_zipcode());
			pstmt2.setString(5, member.getMem_addr());
			pstmt2.setString(6, member.getMem_addr2());
			pstmt2.setInt(7, member.getMem_num());
			//SQL문 실행
			pstmt2.executeUpdate();
			
			//모든 SQL문 정상적으로 실행(commit) 
			conn.commit();
		
		}catch(Exception e) {
			//SQL문 하나라도 실패하면 rollback
			conn.rollback();
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(null, pstmt2, null);
			DBUtil.executeClose(null, pstmt, conn);			
		}
	}
	// [관리자-메서드11. 회원정보 삭제 : ]	
}
