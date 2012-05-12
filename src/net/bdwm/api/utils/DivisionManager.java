package net.bdwm.api.utils;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.bdwm.api.model.Board;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 
 * @author Ruhao Yao: yaoruhao@gmail.com
 *
 */
public class DivisionManager {

	private static Log logger = LogFactory.getLog(DivisionManager.class);
	
	private DivisionManager(){}
	
	private static LinkedHashMap<String, ArrayList<Board>> divisionMap = new LinkedHashMap<String, ArrayList<Board>>();
	
	public static LinkedHashMap<String, ArrayList<Board>> getDivisionMap() {
		return divisionMap;
	}

	private static String groupUrlPatternStr = "<a href=\"(.*?)\">(.*?)</a><td class=body.>\\[Ŀ¼\\]";
	
	private static String boardUrlPatternStr = "<a href=\"bbstop.php\\?board=(.*?)\">(.*?)</a>";
	
	private static Pattern groupUrlPattern;
	
	private static Pattern boardUrlPattern;
	
	private static String bbsPrefixUrl;
	
	private static String mainDivisionUrl;
	

	public static String getBbsPrefixUrl() {
		return bbsPrefixUrl;
	}

	public static void setBbsPrefixUrl(String bbsPrefixUrl) {
		DivisionManager.bbsPrefixUrl = bbsPrefixUrl;
	}

	public static String getMainDivisionUrl() {
		return mainDivisionUrl;
	}

	public static void setMainDivisionUrl(String mainDivisionUrl) {
		DivisionManager.mainDivisionUrl = mainDivisionUrl;
	}
	
	public void init() {
		long startTime = System.currentTimeMillis();
		logger.info("Division Manager init start.");
		int boardNum = 0;
		if (bbsPrefixUrl == null || mainDivisionUrl == null) {
			logger.warn("DivisionManager init failed");
		} else {
			groupUrlPattern = Pattern.compile(groupUrlPatternStr);
			boardUrlPattern = Pattern.compile(boardUrlPatternStr);

			String resource = IOUtil.readUrl(mainDivisionUrl);
			if (resource == null) {
				logger.warn("DivisionManager get data failed:" + mainDivisionUrl);
				return;
			}
			Matcher matcher = groupUrlPattern.matcher(resource);
			while (matcher.find()) {
				String urlSuffix = matcher.group(1);
				String groupName = matcher.group(2);
				ArrayList<Board> tempList = new ArrayList<Board>();
				parseDivisionPage(bbsPrefixUrl+urlSuffix, tempList);
				boardNum += tempList.size();
				divisionMap.put(groupName, tempList);
			}
		}
		logger.info("DivisionManager init succeed, used time:"+(System.currentTimeMillis() - startTime)+"ms, contains "+boardNum+" boards.");
	}
	
	private void parseDivisionPage(String urlStr, ArrayList<Board> boardList) {
		String resource = IOUtil.readUrl(urlStr);
		if (resource == null) {
			logger.warn("DivisionManager get data failed:" + urlStr);
			return;
		}
		Matcher matcher = boardUrlPattern.matcher(resource);
		while (matcher.find()) {
			String boardId = matcher.group(1);
			String boardName = matcher.group(2);
			boardList.add(new Board(boardId, boardName));
		}
		matcher = groupUrlPattern.matcher(resource);
		while (matcher.find()) {
			String urlSuffix = matcher.group(1);
			//String groupName = matcher.group(2);
			parseDivisionPage(bbsPrefixUrl+urlSuffix, boardList);
		}
	}
	
}
