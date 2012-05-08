package net.bdwm.api.model;

public class Topic {
	
	private String name;
	private String division;
	private String board;
	private String threadId;
	private String url;
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDivision() {
		return division;
	}
	public void setDivision(String division) {
		this.division = division;
	}
	public String getBoard() {
		return board;
	}
	public void setBoard(String board) {
		this.board = board;
	}
	public String getThreadId() {
		return threadId;
	}
	public void setThreadId(String threadId) {
		this.threadId = threadId;
	}
	public Topic(String name, String division, String board, String threadId, String url) {
		this.name = name;
		this.division = division;
		this.board = board;
		this.threadId = threadId;
		this.url = url;
	}
	
	

}
