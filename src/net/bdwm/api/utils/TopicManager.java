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
	
	private static String letterPatternStr = "<pre>(.*?)</pre>";
	
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
			System.out.println(matcher.group(1));
			
		}
		
		
		return resultList;
	}
	

	public static synchronized TopicManager getInstance() {
		if (instance == null) {
			instance = new TopicManager();
		}
		return instance;
	}


}
