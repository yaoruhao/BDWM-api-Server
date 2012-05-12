package net.bdwm.api.utils;

import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.bdwm.api.model.Topic;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 
 * @author Ruhao Yao: yaoruhao@gmail.com
 * 
 */
public class BoardManager {

	private static Log logger = LogFactory.getLog(BoardManager.class);

	private static BoardManager instance = null;

	private static String errorPageStr = "<table class=title width=90%><tr><th class=title align=center>北大未名站 -- 错误信息</th></table>";

	private static String boardUrlPrefix;

	public String getBoardUrlPrefix() {
		return boardUrlPrefix;
	}

	public void setBoardUrlPrefix(String boardUrlPrefix) {
		BoardManager.boardUrlPrefix = boardUrlPrefix;
	}

	private static String urlPatternStr = "bbstcon.php\\?board=([^&]+)&threadid=(.+)";

	private static Pattern urlPattern;

	private static String topicPatternStr = "target='_blank'>\\d+</a></td><td class=body\\d><a href=\"bbsqry.php\\?name=[^>]+>(.*?)</a> </td>\\s*<td class=body\\d+><span class=col\\d*>(.*?)</span></td><td class=body\\d><a href=[\"]?([^\"]+)\"> ● (.*?)</a></td><td class=body\\d* align=right>(.*?)</td>\\s*<td class=body\\d* align=right>(.*?)</td>";

	private static Pattern topicPattern;

	private static String topTopicPatternStr = "<a href=\"bbsqry.php\\?name=[^>]+>(.*?)</a>.*?<span class=col\\d+>(.*?)</span></td><td class=body\\d><a href=[\"]?([^\"]+)\"> ● (.*?)</span></a></td>\\s*<td class=body\\d align=right>(.*?)</td>\\s*<td class=body\\d align=right>(.*?)</td>";

	private static Pattern topTopicPattern;

	public void init() {
		if (boardUrlPrefix == null) {
			logger.error("Board Manager init failed.");
		} else {
			topTopicPattern = Pattern.compile(topTopicPatternStr);
			topicPattern = Pattern.compile(topicPatternStr);
			urlPattern = Pattern.compile(urlPatternStr);
		}

	}

	public static synchronized BoardManager getInstance() {
		if (instance == null) {
			instance = new BoardManager();
		}
		return instance;
	}

	public LinkedList<Topic> getBoardData(String boardName, String skip) {
		LinkedList<Topic> resultList = new LinkedList<Topic>();
		String urlStr = boardUrlPrefix + "board=" + boardName + "&skip=" + skip;
		long startTime = System.currentTimeMillis();
		String resource = IOUtil.readUrl(urlStr);		
		if (resource == null) {
			logger.warn("BoardManager getBoard data failed:" + urlStr);
			return resultList;
		}
		logger.info("Board Manager use:"
				+ (System.currentTimeMillis() - startTime)
				+ "ms to read board:" + boardName);
		startTime = System.currentTimeMillis();
		// the board not exist.
		if (resource.indexOf(errorPageStr) != -1) {
			logger.warn("board not exists:" + urlStr);
			return resultList;
		}
		int tempIndex = resource.indexOf("本版在线");
		if (tempIndex != -1) {
			resource = resource.substring(tempIndex);
		}
		int skipNum = 0;
		try {
			skipNum = Integer.parseInt(skip);
		} catch (NumberFormatException e) {
			logger.warn("visit board request contains illegal skip:" + skip);
		}
		Matcher matcher = null;
		// Only parse top if skipNum == 0
		if (skipNum == 0) {
			matcher = topTopicPattern.matcher(resource);
			while (matcher.find()) {
				String author = matcher.group(1);
				String time = matcher.group(2);
				String url = matcher.group(3);
				String name = matcher.group(4);
				String replyCount = matcher.group(5).trim();
				String wordCount = matcher.group(6);
				Topic topic = new Topic(name, null, boardName, null, url, true,
						author, replyCount, wordCount);
				// top topic is added in order.
				resultList.addLast(topic);
				logger.info("board:" + boardName + "get top topic\tauthor:"
						+ author + "\ttime:" + time + "\turl:" + url
						+ "\tname:" + name + "\treply:" + replyCount
						+ "\twordCount:" + wordCount);
			}
		}
		int topTopicNum = resultList.size();
		matcher = topicPattern.matcher(resource);
		while (matcher.find()) {
			String author = matcher.group(1);
			String time = matcher.group(2);
			String url = matcher.group(3);
			String name = matcher.group(4);
			String replyCount = matcher.group(5).trim();
			String wordCount = matcher.group(6);
			String board = null;
			String threadId = null;
			Matcher urlMatcher = urlPattern.matcher(url);
			if (urlMatcher.find()) {
				board = urlMatcher.group(1);
				threadId = urlMatcher.group(2);
			}
			Topic topic = new Topic(name, null, board, threadId, url, false,
					author, replyCount, wordCount);
			// regular topic is added after top topic by its publish time. The
			// newest regular topic is at the bottom of the board page.
			resultList.add(topTopicNum, topic);
			logger.info("board:" + boardName + "get regular topic\tauthor:"
					+ author + "\ttime:" + time + "\turl:" + url + "\tname:"
					+ name + "\treply:" + replyCount + "\twordCount:"
					+ wordCount);
		}
		logger.info("Board Manager use:"
				+ (System.currentTimeMillis() - startTime)
				+ "ms to extract board:" + boardName);
		return resultList;
	}

	private BoardManager() {
	}

}
