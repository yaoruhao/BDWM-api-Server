package net.bdwm.api.model;

/**
 * 
<<<<<<< HEAD
 * @author Ruhao Yao: yaoruhao@gmail.com
=======
 * @author Ruhao Yao
>>>>>>> origin/develop
 * 
 */
public class Letter {
	private String author;
	private String board;
	private String title;
	private String time;
	private String content;
	private String replyUrl;
	private String mailUrl;

	public Letter(String author, String board, String title, String time,
			String content, String replyUrl, String mailUrl) {
		this.author = author;
		this.board = board;
		this.title = title;
		this.time = time;
		this.content = content;
		this.replyUrl = replyUrl;
		this.mailUrl = mailUrl;
	}

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

	public String getReplyUrl() {
		return replyUrl;
	}

	public void setReplyUrl(String replyUrl) {
		this.replyUrl = replyUrl;
	}

	public String getMailUrl() {
		return mailUrl;
	}

	public void setMailUrl(String mailUrl) {
		this.mailUrl = mailUrl;
	}
}
