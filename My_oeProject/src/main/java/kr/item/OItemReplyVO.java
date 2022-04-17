package kr.item;
/*댓글은 전부 ajax통신. ui에서 제이쿼리 호출*/

public class OItemReplyVO { 

	private int re_num;			//댓글번호
	private int item_num;		//상품번호
	private int mem_num;		//회원번호
	private String re_content;	//댓글 내용
	private String re_date;		//댓글 등록일
	private String re_modifydate;	//댓글 수정일	
	private String re_ip;		//ip
	private String id;			//테이블에는 없지만 추가
	public int getRe_num() {
		return re_num;
	}
	public void setRe_num(int re_num) {
		this.re_num = re_num;
	}
	public int getItem_num() {
		return item_num;
	}
	public void setItem_num(int item_num) {
		this.item_num = item_num;
	}
	public int getMem_num() {
		return mem_num;
	}
	public void setMem_num(int mem_num) {
		this.mem_num = mem_num;
	}
	public String getRe_content() {
		return re_content;
	}
	public void setRe_content(String re_content) {
		this.re_content = re_content;
	}
	public String getRe_date() {
		return re_date;
	}
	public void setRe_date(String re_date) {
		this.re_date = re_date;
	}
	public String getRe_modifydate() {
		return re_modifydate;
	}
	public void setRe_modifydate(String re_modifydate) {
		this.re_modifydate = re_modifydate;
	}
	public String getRe_ip() {
		return re_ip;
	}
	public void setRe_ip(String re_ip) {
		this.re_ip = re_ip;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
}
