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

public class TopicManager {
	
	private static Log logger = LogFactory.getLog(TopicManager.class);

	private static TopicManager instance = null;
	
	private static String letterPatternStr = "<table class=doc><tr><td class=doc width=710 valign=top><pre>([\\s\\S]*?)</table></tr>";
	
	private static String bbsPrefixUrl;
	
	public static String getBbsPrefixUrl() {
		return bbsPrefixUrl;
	}

	public static void setBbsPrefixUrl(String bbsPrefixUrl) {
		TopicManager.bbsPrefixUrl = bbsPrefixUrl;
	}


	private static Pattern letterPattern;
	
	public void init() {
		if (bbsPrefixUrl == null ) {
			logger.error("TopicManager init failed.");
		}
		letterPattern = Pattern.compile(letterPatternStr);
	}
	
	public LinkedList<Letter> getTopicDetail(String url) {
		url = bbsPrefixUrl + url;
		LinkedList<Letter> resultList = new LinkedList<Letter>();
		InputStream in = null;
		String resource = null;
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
		Matcher matcher = letterPattern.matcher(resource);
		
		while (matcher.find()) {
			String tempStr = matcher.group(1);
			System.out.println(matcher.group(1));
			parseEachTopic(tempStr, resultList);
		}
		
		return resultList;
	}
	
	private void parseEachTopic(String dataStr, LinkedList<Letter> resultList) {
		Matcher m = Pattern.compile("发信人: ([^\\s]*) .*?, 信区: ([^\n]*)\n标  题: ([^\n]*)\n发信站: 北大未名站 \\((.*?)\\), .*?\n\n([\\s\\S]*?)--").matcher(dataStr);
		if (m.find()) {
			String author = m.group(1);
			String board = m.group(2);
			String title = m.group(3);
			String time = m.group(4);
			String content = m.group(5).trim();
			System.out.println(author + board + title + time);
			System.out.println(content);
		}
	}
	

	public static synchronized TopicManager getInstance() {
		if (instance == null) {
			instance = new TopicManager();
		}
		return instance;
	}


}
