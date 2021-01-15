package com.javaex.vo;

public class BoardVo extends UserVo {

	private int no;
	private String title;
	private String content;
	private int hit;
	private String regDate;
	private int userNo;

	public BoardVo() {
	}

	public BoardVo(String title, String content, int no) {
		super();
		this.title = title;
		this.content = content;
		super.no = no;
	}

	public BoardVo(String name, int hit, String regDate, String title, String content) {
		super();
		super.name = name;
		this.title = title;
		this.content = content;
		this.hit = hit;
		this.regDate = regDate;
	}

	public BoardVo(int no, String title, String name, int hit, String regDate) {
		super();
		this.no = no;
		this.title = title;
		this.hit = hit;
		this.regDate = regDate;
		super.name = name;
	}

	public int getNo() {
		return no;
	}

	public void setNo(int no) {
		this.no = no;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getHit() {
		return hit;
	}

	public void setHit(int hit) {
		this.hit = hit;
	}

	public String getRegDate() {
		return regDate;
	}

	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}

	public int getUserNo() {
		return userNo;
	}

	public void setUserNo(int userNo) {
		this.userNo = userNo;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "BoardVo [no=" + no + ", title=" + title + ", content=" + content + ", hit=" + hit + ", regDate="
				+ regDate + ", userNo=" + userNo + ", name=" + name + "]";
	}

}
