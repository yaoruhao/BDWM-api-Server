package net.bdwm.api.utils;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.bdwm.api.model.Letter;

import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 
 * @author Ruhao Yao
 *
 */
public class TopicManager {

	private static Log logger = LogFactory.getLog(TopicManager.class);

	private static TopicManager instance = null;

	private static String letterPatternStr = "<table class=doc>[\\s\\S]*?<pre>([\\s\\S]*?)</table></tr>";

	private static String eachLetterPatternStr = "发信人: ([^\\s]*) .*?, 信区: ([^\n]*)\n标  题: ([^\n]*)\n发信站: 北大未名站 \\((.*?)\\), .*?\n\n([\\s\\S]*)--";

	private static String replyUrlPatternStr = "<th class=foot><a href=\"([^\"]*?)\">回文章</a></th></tr>";

	private static String mailUrlPatternStr = "<th class=foot><a href=\"([^\"]*?)\">.信给作者</a></th></tr>";

	private static Pattern replyUrlPattern;

	private static Pattern mailUrlPattern;

	private static Pattern eachLetterPattern;

	private static String bbsPrefixUrl;

	public static String getBbsPrefixUrl() {
		return bbsPrefixUrl;
	}

	public static void setBbsPrefixUrl(String bbsPrefixUrl) {
		TopicManager.bbsPrefixUrl = bbsPrefixUrl;
	}

	private static Pattern letterPattern;

	public void init() {
		if (bbsPrefixUrl == null) {
			logger.error("TopicManager init failed.");
		} else {
			letterPattern = Pattern.compile(letterPatternStr);
			eachLetterPattern = Pattern.compile(eachLetterPatternStr);
			replyUrlPattern = Pattern.compile(replyUrlPatternStr);
			mailUrlPattern = Pattern.compile(mailUrlPatternStr);
		}

	}
	
	private void parseEachLetter(String resource, LinkedList<Letter> resultList){
		Matcher m = eachLetterPattern.matcher(resource);
		//Parse each topic, if not match, then return immediately.
		if (m.find()) {
			String author = m.group(1);
			String board = m.group(2);
			String title = m.group(3);
			String time = m.group(4);
			String content = m.group(5).trim();
			String replyUrl = null;
			String mailUrl = null;
			Matcher tempMatcher = replyUrlPattern.matcher(resource);
			if (tempMatcher.find()) {
				replyUrl = tempMatcher.group(1);
			}
			tempMatcher = mailUrlPattern.matcher(resource);
			if (tempMatcher.find()) {
				mailUrl = tempMatcher.group(1);
			}
			Letter letter = new Letter(author, board, title, time, content, replyUrl, mailUrl);
			resultList.add(letter);
		}
	}
	
	public LinkedList<Letter> getTopTopicDetail(String url) {
		url = bbsPrefixUrl + url;
		LinkedList<Letter> resultList = new LinkedList<Letter>();
		InputStream in = null;
		String resource = null;
		long startTime = System.currentTimeMillis();
		try {
			in = new URL(url).openStream();
			resource = IOUtils.toString(in);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			IOUtils.closeQuietly(in);
		}

		if (resource == null) {
			return resultList;
		}
		logger.info("Topic Manager use:"+(System.currentTimeMillis() - startTime)+"ms to read url:"+url);
		startTime = System.currentTimeMillis();
		parseEachLetter(resource, resultList);
		logger.info("Topic Manager use:"+(System.currentTimeMillis() - startTime)+"ms to exctract data for url:"+url);
		return resultList;
		
	}

	public LinkedList<Letter> getTopicDetail(String url) {
		url = bbsPrefixUrl + url;
		LinkedList<Letter> resultList = new LinkedList<Letter>();
		InputStream in = null;
		String resource = null;
		long startTime = System.currentTimeMillis();
		try {
			in = new URL(url).openStream();
			resource = IOUtils.toString(in);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			IOUtils.closeQuietly(in);
		}

		if (resource == null) {
			return resultList;
		}
		logger.info("Topic Manager use:"+(System.currentTimeMillis() - startTime)+"ms to read url:"+url);
		startTime = System.currentTimeMillis();
		Matcher matcher = letterPattern.matcher(resource);
		while (matcher.find()) {
			String dataStr = matcher.group(1);
			parseEachLetter(dataStr, resultList);
			if (resultList.isEmpty()) {
				break;
			}
		}
		logger.info("Topic Manager use:"+(System.currentTimeMillis() - startTime)+"ms to exctract data for url:"+url);
		return resultList;
	}

	public static synchronized TopicManager getInstance() {
		if (instance == null) {
			instance = new TopicManager();
		}
		return instance;
	}

}
