package net.bdwm.api.utils;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.bdwm.api.model.HotTopicsModel;

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
		if (m.find()){
			logger.info(m.group());
		}
		
	}

	public String getBbsMainBoardUrl() {
		return bbsMainBoardUrl;
	}

	public void setBbsMainBoardUrl(String bbsMainBoardUrl) {
		this.bbsMainBoardUrl = bbsMainBoardUrl;
	}
	
	

}
