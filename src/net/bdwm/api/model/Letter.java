package net.bdwm.api.model;

/**
 * 
 * @author Ruhao Yao
 * 
 */
public class Letter {
	private String author;
	private String board;
	private String title;
	private String time;
	private String content;
	private String replyContent;

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getBoard() {
		return board;
	}

	public void setBoard(String board) {
		this.board = board;
	}

	public String getTitle() {
		return title;
	}

	public Letter(String author, String board, String title, String time,
			String content, String replyContent) {
		super();
		this.author = author;
		this.board = board;
		this.title = title;
		this.time = time;
		this.content = content;
		this.replyContent = replyContent;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getReplyContent() {
		return replyContent;
	}

	public void setReplyContent(String replyContent) {
		this.replyContent = replyContent;
	}

}
