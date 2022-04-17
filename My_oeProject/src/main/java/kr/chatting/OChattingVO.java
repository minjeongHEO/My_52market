package kr.chatting;

import java.sql.Date;

public class OChattingVO { 
	private int mem_num; //회원번호
	private int chat_num; //체팅번호
	private int to_num; //메시지수신번호(판매자회원번호)
	private int from_num; //메시지발신번호(구매자회원번호)
	private int chatstate_num; //읽기상태(0읽지않음, 1읽음)
	private String content; //읽기상태
	private int item_num; //상품번호
	private Date reg_date; //채팅등록시간
	private String title;

	private String mem_id;

	public String getMem_id() {
		return mem_id;
	}
	public void setMem_id(String mem_id) {
		this.mem_id = mem_id;
	}
	public int getMem_num() {
		return mem_num;
	}
	public void setMem_num(int mem_num) {
		this.mem_num = mem_num;
	}
	public int getChat_num() {
		return chat_num;
	}
	public void setChat_num(int chat_num) {
		this.chat_num = chat_num;
	}
	public int getTo_num() {
		return to_num;
	}
	public void setTo_num(int to_num) {
		this.to_num = to_num;
	}
	public int getFrom_num() {
		return from_num;
	}
	public void setFrom_num(int from_num) {
		this.from_num = from_num;
	}
	public int getChatstate_num() {
		return chatstate_num;
	}
	public void setChatstate_num(int chatstate_num) {
		this.chatstate_num = chatstate_num;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public int getItem_num() {
		return item_num;
	}
	public void setItem_num(int item_num) {
		this.item_num = item_num;
	}
	public Date getReg_date() {
		return reg_date;
	}
	public void setReg_date(Date reg_date) {
		this.reg_date = reg_date;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
}