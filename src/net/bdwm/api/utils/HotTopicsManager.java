package net.bdwm.api.utils;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.bdwm.api.model.HotTopicsModel;
import net.bdwm.api.model.Topic;

import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class HotTopicsManager implements Runnable{
	private static Log logger = LogFactory.getLog(HotTopicsManager.class);
	
	private static HotTopicsManager instance = null;
	
	private HotTopicsModel hotTopicsModel;
	
	private String bbsPrefixUrl;
	
	private String bbsMainBoardUrl;
	
	private String topTenPatternStr="<span id=\"DefaultTopTen\">.+<span id='TopTenMoreButton' class='More'>";
	
	private String topTenPatternEachStr="<span class='hidden'>.*?</a><br /></span>";
	
	private String divisionPatternStr="·ÖÇø£º";
	
	private Pattern divisionPattern;
	
	private Pattern topTenEachPattern;
	
	private Pattern topTenPattern;
	
	public String getBbsPrefixUrl() {
		return bbsPrefixUrl;
	}

	public void setBbsPrefixUrl(String bbsPrefixUrl) {
		this.bbsPrefixUrl = bbsPrefixUrl;
	}

	public void init() {
		if (bbsPrefixUrl == null || bbsMainBoardUrl == null){
			logger.warn("HotTopicsManager init failed, bbsPrefixUrl is null");
		}
		logger.info("HotTopicsManager init succeed.");
		topTenPattern = Pattern.compile(topTenPatternStr);
		topTenEachPattern = Pattern.compile(topTenPatternEachStr);
		divisionPattern = Pattern.compile(divisionPatternStr);
	}
	
	public static synchronized HotTopicsManager getInstance(){
		if (instance == null){
			instance = new HotTopicsManager();
		}
		return instance;
	}
	
	private HotTopicsManager(){}

	public HotTopicsModel getHotTopicsModel() {
		return hotTopicsModel;
	}

	public void run() {
		while (true) {
			logger.info("Thread running");
			InputStream in = null;
			String resource = null;
			try {
				in = new URL(bbsMainBoardUrl).openStream();
				resource = IOUtils.toString(in);
			} catch (IOException e) {
				
				e.printStackTrace();
			} finally {
				IOUtils.closeQuietly(in);
			}
			if (resource != null){
				parseMainBoard(resource);
			}
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				logger.warn("Thread exception");
			}
			logger.info("Thread wakeup");
		}

	}
	
	private void parseMainBoard(String resource) {
		Matcher m = topTenPattern.matcher(resource);
		ArrayList<Topic> topTenTopics = new ArrayList<Topic>();
		if (m.find()){
			String topTenStr = m.group();
			parseTopTenTopics(topTenTopics, topTenStr);		
		}
		m = divisionPattern.matcher(resource);
		int startIndex = 0;
		HashMap<String, ArrayList<Topic>> divisionTopicsHashMap = new HashMap<String, ArrayList<Topic>>();
		while (resource.indexOf(divisionPatternStr, startIndex) != -1){
			startIndex = resource.indexOf(divisionPatternStr, startIndex) + divisionPatternStr.length();
			int endIndex = resource.indexOf("</td>", startIndex);
			String tempStr = resource.substring(startIndex, endIndex);
			startIndex = endIndex;
			parseDivisionTopics(divisionTopicsHashMap, tempStr);
		}
		System.out.println(divisionTopicsHashMap);
		System.out.println(topTenTopics);
		
	}
	
	private void parseDivisionTopics(HashMap<String, ArrayList<Topic>> divisionTopics, String dataStr) {
		
		String topDivision = dataStr.substring(dataStr.indexOf(">") + 1, dataStr.indexOf("<", dataStr.indexOf(">")));
		ArrayList<Topic> topicList = new ArrayList<Topic>();
		int startIndex = dataStr.indexOf("[<a href='bbsdoc.php?board=");
		while (startIndex != -1) {
			startIndex = dataStr.indexOf(">", startIndex) + ">".length();
			int endIndex = dataStr.indexOf("<", startIndex);
			String division = dataStr.substring(startIndex, endIndex);
			startIndex = dataStr.indexOf("<a href='", endIndex) + "<a href='".length();
			endIndex = dataStr.indexOf("'>", startIndex);
			String url = dataStr.substring(startIndex, endIndex);
			String threadId = url.substring(url.indexOf("threadid=") + "threadid=".length());
			String board = url.substring(url.indexOf("board=") + "board=".length(), url.indexOf("&"));
			startIndex = endIndex + "'>".length();
			endIndex = dataStr.indexOf("</a>", startIndex);
			String name = dataStr.substring(startIndex, endIndex);
			startIndex = dataStr.indexOf("[<a href='bbsdoc.php?board=", endIndex);
			Topic topic = new Topic(name, division, board, threadId, url);
			topicList.add(topic);
			
		}
		divisionTopics.put(topDivision, topicList);
		
		
	}
	
	private void parseTopTenTopics(ArrayList<Topic> topTenTopics, String topTenStr) {		
		Matcher topTenMatch = topTenEachPattern.matcher(topTenStr);
		while (topTenMatch.find()) {
			String matchStr = topTenMatch.group();
			String tempStr = "bbsdoc.php?board=";
			int startIndex = matchStr.indexOf(tempStr) + tempStr.length();
			int endIndex = matchStr.indexOf("'", startIndex);
			String board = matchStr.substring(startIndex, endIndex);
			startIndex = endIndex + ".>".length();
			endIndex = matchStr.indexOf("<", startIndex);
			String division = matchStr.substring(startIndex, endIndex);
			startIndex = matchStr.indexOf("href='", endIndex) + "href='".length();
			endIndex = matchStr.indexOf("'>", startIndex);
			String url = matchStr.substring(startIndex, endIndex);
			String threadId = url.substring(url.indexOf("threadid=") + "threadid=".length());
			startIndex = endIndex + ".>".length();
			endIndex = matchStr.indexOf("</a>", startIndex);
			String topicName = matchStr.substring(startIndex, endIndex);
			Topic topic = new Topic(topicName, division, board, threadId, url);
			topTenTopics.add(topic);				
		}	
		
	}

	public String getBbsMainBoardUrl() {
		return bbsMainBoardUrl;
	}

	public void setBbsMainBoardUrl(String bbsMainBoardUrl) {
		this.bbsMainBoardUrl = bbsMainBoardUrl;
	}
	
	

}
