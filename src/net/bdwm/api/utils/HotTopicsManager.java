package net.bdwm.api.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.bdwm.api.model.HotTopicsModel;
import net.bdwm.api.model.Topic;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 
 * @author Ruhao Yao: yaoruhao@gmail.com
 *
 */
public class HotTopicsManager implements Runnable {
	private static Log logger = LogFactory.getLog(HotTopicsManager.class);

	private static HotTopicsManager instance = null;

	private HotTopicsModel hotTopicsModel;

	private String bbsPrefixUrl;

	private String bbsMainBoardUrl;

	private String topTenPatternStr = "<span id=\"DefaultTopTen\">.+<span id='TopTenMoreButton' class='More'>";

	private String topTenPatternEachStr = "<span class='hidden'>\\[<a[^>]+'>(.*?)</a>\\] </span><a href='([^']+)'>(.*?)</a><br /></span>";

	private String divisionStartStr = "<b><span style=\"color: #008080\">分 区 热 门 话 题</span></b>";

	private String divisionPatternStr = "分区：<a href='bbsxboa.php\\?group=\\d+'>([^<]+)</a>\\s+<br />(.*?)<br /></td>";

	private String schoolHotTopicPatternStr = "◇ <a href=\"([^\"]+)\">([^<]+)</a></td></tr>";

	private String divisionEachPatterStr = "\\[<a href='bbsdoc.php\\?board=[^']+'>(.*?)</a>\\] </span><a href='([^']+)'>(.*?)</a><br /></span>";

	private String urlPatternStr = "bbstcon.php\\?board=([^&]+)&threadid=(.+)";

	private int sleepSecond = 0;

	public int getSleepSecond() {
		return sleepSecond;
	}

	public void setSleepSecond(int sleepSecond) {
		this.sleepSecond = sleepSecond;
	}

	private Pattern urlPattern;

	private Pattern divisionEachPattern;

	private Pattern schoolHotPattern;

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
		if (bbsPrefixUrl == null || bbsMainBoardUrl == null || sleepSecond == 0) {
			logger.warn("HotTopicsManager init failed, plz check the init file.");
		} else {
			hotTopicsModel = new HotTopicsModel();
			topTenPattern = Pattern.compile(topTenPatternStr);
			topTenEachPattern = Pattern.compile(topTenPatternEachStr);
			divisionPattern = Pattern.compile(divisionPatternStr);
			schoolHotPattern = Pattern.compile(schoolHotTopicPatternStr);
			divisionEachPattern = Pattern.compile(divisionEachPatterStr);
			urlPattern = Pattern.compile(urlPatternStr);
			logger.info("HotTopicsManager init succeed.");
		}

	}

	public static synchronized HotTopicsManager getInstance() {
		if (instance == null) {
			instance = new HotTopicsManager();
		}
		return instance;
	}

	private HotTopicsManager() {
	}

	public HotTopicsModel getHotTopicsModel() {
		return hotTopicsModel;
	}

	public void run() {
		while (true) {
			logger.info("HotTopics update thread running");
			String resource = IOUtil.readUrl(bbsMainBoardUrl);
			if (resource != null) {
				parseMainBoard(resource);
			}
			try {
				logger.info("HotTopics update sleep :" + sleepSecond
						+ " seconds.");
				Thread.sleep(1000 * sleepSecond);
			} catch (InterruptedException e) {
				logger.warn("Thread exception");
			}
			logger.info("HotTopics update thread wakeup");
		}

	}

	private void parseMainBoard(String resource) {
		Matcher m = topTenPattern.matcher(resource);
		ArrayList<Topic> topTenTopics = new ArrayList<Topic>();
		if (m.find()) {
			String topTenStr = m.group();
			parseTopTenTopics(topTenTopics, topTenStr);
		}
		int startIndex = resource.indexOf(divisionStartStr);
		HashMap<String, ArrayList<Topic>> divisionTopicsHashMap = new HashMap<String, ArrayList<Topic>>();
		if (startIndex != -1) {
			int endIndex = resource.indexOf("</table>", startIndex);
			String tempStr = resource.substring(startIndex, endIndex);
			parseDivisionTopics(divisionTopicsHashMap, tempStr);
		}
		startIndex = resource.indexOf("校 园 热 点");
		ArrayList<Topic> schoolTopics = new ArrayList<Topic>();
		if (startIndex != -1) {
			int endIndex = resource.indexOf("</table>", startIndex);
			String tempStr = resource.substring(startIndex, endIndex);
			parseSchoolHotTopics(schoolTopics, tempStr);
		}
		startIndex = resource
				.indexOf("<b><span style=\"color: #008080\">学 术 动 态</span></b>");
		ArrayList<Topic> academicTopics = new ArrayList<Topic>();
		if (startIndex != -1) {
			int endIndex = resource.indexOf("</table>", startIndex);
			String tempStr = resource.substring(startIndex, endIndex);
			parseAcademicHotTopics(academicTopics, tempStr);
		}
		if (!divisionTopicsHashMap.isEmpty()) {
			hotTopicsModel.setDivisionTopTopics(divisionTopicsHashMap);
			hotTopicsModel.setDivisionHotJsonObject(JSONObject
					.fromObject(divisionTopicsHashMap));
			logger.info("division topics update succeed.");
		} else {
			logger.warn("division topics update failed.");
		}
		if (!topTenTopics.isEmpty()) {
			hotTopicsModel.setAllTopTopics(topTenTopics);
			hotTopicsModel.setTopTenJsonArray(JSONArray
					.fromObject(topTenTopics));
			logger.info("topten topics update succeed.");
		} else {
			logger.warn("topten topics update failed.");
		}
		if (!schoolTopics.isEmpty()) {
			hotTopicsModel.setSchoolHotTopics(schoolTopics);
			hotTopicsModel.setSchoolHotJsonArray(JSONArray
					.fromObject(schoolTopics));
			logger.info("school topics update succeed.");
		} else {
			logger.warn("school topics update failed.");
		}
		if (!academicTopics.isEmpty()) {
			hotTopicsModel.setAcademicHotTopics(academicTopics);
			hotTopicsModel.setAcademicHotJsonArray(JSONArray
					.fromObject(academicTopics));
			logger.info("academic topics update succeed.");
		} else {
			logger.warn("academic topics update failed.");
		}
	}

	private void parseAcademicHotTopics(ArrayList<Topic> academicTopics,
			String dataStr) {

		// Academic and School hot share the same pattern.
		Matcher matcher = schoolHotPattern.matcher(dataStr);
		while (matcher.find()) {
			String url = matcher.group(1);
			String name = matcher.group(2);
			Topic topic = new Topic(name, "学术动态", "AcademicInfo", null, url);
			academicTopics.add(topic);
			logger.info("Academic hot topics add:" + topic);
		}

	}

	private void parseSchoolHotTopics(ArrayList<Topic> schoolTopics,
			String dataStr) {
		Matcher matcher = schoolHotPattern.matcher(dataStr);
		while (matcher.find()) {
			String url = matcher.group(1);
			String name = matcher.group(2);
			Topic topic = new Topic(name, "校园热点", "CampusInfo", null, url);
			schoolTopics.add(topic);
			logger.info("School hot topics add:" + topic);
		}
	}

	private void parseDivisionTopics(
			HashMap<String, ArrayList<Topic>> divisionTopics, String dataStr) {
		Matcher matcher = divisionPattern.matcher(dataStr);
		while (matcher.find()) {
			String topDivisionStr = matcher.group(1);
			String tempStr = matcher.group(2);
			Matcher m = divisionEachPattern.matcher(tempStr);
			ArrayList<Topic> topicList = new ArrayList<Topic>();
			while (m.find()) {
				String division = m.group(1);
				String url = m.group(2);
				String name = m.group(3);
				String board = null;
				String threadId = null;
				Matcher urlMatcher = urlPattern.matcher(url);
				if (urlMatcher.find()) {
					board = urlMatcher.group(1);
					threadId = urlMatcher.group(2);
				}
				Topic topic = new Topic(name, division, board, threadId, url);
				topicList.add(topic);
			}
			divisionTopics.put(topDivisionStr, topicList);
			logger.info("Top Division:" + topDivisionStr + " added "
					+ topicList.size() + " topics.");
		}
	}

	private void parseTopTenTopics(ArrayList<Topic> topTenTopics,
			String topTenStr) {
		Matcher topTenMatch = topTenEachPattern.matcher(topTenStr);
		while (topTenMatch.find()) {
			String division = topTenMatch.group(1);
			String url = topTenMatch.group(2);
			String name = topTenMatch.group(3);
			String board = null;
			String threadId = null;
			Matcher m = urlPattern.matcher(url);
			if (m.find()) {
				board = m.group(1);
				threadId = m.group(2);
			}
			Topic topic = new Topic(name, division, board, threadId, url);
			topTenTopics.add(topic);
			logger.info("Top ten topics add:" + topic.toString());
		}

	}

	public String getBbsMainBoardUrl() {
		return bbsMainBoardUrl;
	}

	public void setBbsMainBoardUrl(String bbsMainBoardUrl) {
		this.bbsMainBoardUrl = bbsMainBoardUrl;
	}

}
