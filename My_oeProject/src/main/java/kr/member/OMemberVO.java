package kr.member;

import java.sql.Date; 

public class OMemberVO {
	private int mem_num;
	private String mem_id;
	private int mem_auth;
	
	private String mem_nick;	//mem_nick : 회원 이름 
	private String mem_pw;
	private String mem_phone;
	private String mem_photo;
	private String mem_addr;
	private String mem_addr2;
	private String mem_zipcode;
	private String mem_email;
	private Date mem_date;
	private Date mem_modifydate;
	
	//4. 다원
	// [메서드1. ID찾기 : findIdMember() ]
	public boolean findIdMember(String Input_memnick, String Input_memphone) {
		if(mem_nick.equals(Input_memnick) && mem_phone.equals(Input_memphone)) {
			return true;
				//**Q. String타입으로 return mem_id를 반환해줄까 ?
		}
		return false; 
	}
	
	
	// [메서드2. PW찾기 : findPwMember() ]
	public boolean findPwMember(String Input_memid, String Input_memnick, String Input_mememail) {
		if(mem_id.equals(Input_memid) && mem_nick.equals(Input_memnick) && mem_email.equals(Input_mememail)) {
			return true; 
				//**Q. String타입으로 return mem_pw를 반환해줄까 ?
		}
		return false; 
	}
	
	
	// [메서드3. 비밀번호 일치여부 확인 : isCheckedPassword() ]
	   //비밀번호 일치 여부 체크
	   public boolean isCheckedPassword(String UserPasswd) {
	      //회원 등급(mem_auth) : 0탈퇴회원,1일반회원,2관리자
	      if(mem_auth > 0 && mem_pw.equals(UserPasswd)) {
	         return true;
	      }
	      return false;
	   }
	
	
	
	public int getMem_num() {
		return mem_num;
	}
	public void setMem_num(int mem_num) {
		this.mem_num = mem_num;
	}
	public String getMem_id() {
		return mem_id;
	}
	public void setMem_id(String mem_id) {
		this.mem_id = mem_id;
	}
	public int getMem_auth() {
		return mem_auth;
	}
	public void setMem_auth(int mem_auth) {
		this.mem_auth = mem_auth;
	}
	public String getMem_nick() {
		return mem_nick;
	}
	public void setMem_nick(String mem_nick) {
		this.mem_nick = mem_nick;
	}
	public String getMem_pw() {
		return mem_pw;
	}
	public void setMem_pw(String mem_pw) {
		this.mem_pw = mem_pw;
	}
	public String getMem_phone() {
		return mem_phone;
	}
	public void setMem_phone(String mem_phone) {
		this.mem_phone = mem_phone;
	}
	public String getMem_photo() {
		return mem_photo;
	}
	public void setMem_photo(String mem_photo) {
		this.mem_photo = mem_photo;
	}
	public String getMem_addr() {
		return mem_addr;
	}
	public void setMem_addr(String mem_addr) {
		this.mem_addr = mem_addr;
	}
	public String getMem_addr2() {
		return mem_addr2;
	}
	public void setMem_addr2(String mem_addr2) {
		this.mem_addr2 = mem_addr2;
	}
	public String getMem_zipcode() {
		return mem_zipcode;
	}
	public void setMem_zipcode(String mem_zipcode) {
		this.mem_zipcode = mem_zipcode;
	}
	public String getMem_email() {
		return mem_email;
	}
	public void setMem_email(String mem_email) {
		this.mem_email = mem_email;
	}
	public Date getMem_date() {
		return mem_date;
	}
	public void setMem_date(Date mem_date) {
		this.mem_date = mem_date;
	}
	public Date getMem_modifydate() {
		return mem_modifydate;
	}
	public void setMem_modifydate(Date mem_modifydate) {
		this.mem_modifydate = mem_modifydate;
	}
}