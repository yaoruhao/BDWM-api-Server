package net.bdwm.api.model;

/**
 * 
 * @author Ruhao Yao
 *
 */
public class Topic {

	private String name;
	private String division;
	private String board;
	private String threadId;
	private String url;
	private Boolean isTop;
	private String author;
	private String replyNum;
	private String wordCount;



	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getReplyNum() {
		return replyNum;
	}

	public void setReplyNum(String replyNum) {
		this.replyNum = replyNum;
	}

	public String getWordCount() {
		return wordCount;
	}

	public void setWordCount(String wordCount) {
		this.wordCount = wordCount;
	}

	public Boolean getIsTop() {
		return isTop;
	}

	public void setIsTop(Boolean isTop) {
		this.isTop = isTop;
	}

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
	
	public Topic(String name, String division, String board, String threadId,
			String url, Boolean isTop, String author, String replyNum,
			String wordCount) {
		this.name = name;
		this.division = division;
		this.board = board;
		this.threadId = threadId;
		this.url = url;
		this.isTop = isTop;
		this.author = author;
		this.replyNum = replyNum;
		this.wordCount = wordCount;
	}

	public Topic(String name, String division, String board, String threadId,
			String url) {
		this.name = name;
		this.division = division;
		this.board = board;
		this.threadId = threadId;
		this.url = url;
		if (threadId == null) {
			this.isTop = true;
		} else {
			this.isTop = false;
		}
	}

	@Override
	public String toString() {
		return name + "\t" + division + "\t" + board + "\t" + threadId + "\t"
				+ url;
	}

}
