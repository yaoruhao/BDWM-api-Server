package net.bdwm.api.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.bdwm.api.utils.TopicManager;
import net.sf.json.JSONArray;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * 
 * @author Ruhao Yao: yaoruhao@gmail.com
 * 
 */

@Controller
@RequestMapping("/topic")
public class TopicController {

	private static Log logger = LogFactory.getLog(TopicController.class);

	private static TopicManager topicManager;

	public static TopicManager getTopicManager() {
		return topicManager;
	}

	public static void setTopicManager(TopicManager topicManager) {
		TopicController.topicManager = topicManager;
	}

	public void init() {
		topicManager = TopicManager.getInstance();
	}

	@RequestMapping("/bbstcon.php/{urltail}")
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response, @PathVariable String urltail)
			throws Exception {
		long startTime = System.currentTimeMillis();
		String message = null;

		JSONArray jsonArray = JSONArray.fromObject(topicManager
				.getTopicDetail("bbstcon.php?" + urltail));

		message = jsonArray.toString();

		response.setHeader("Cache-Control", "no-cache");
		response.setContentType("text/json;charset=gb2312");
		long endTime = System.currentTimeMillis();
		logger.info("TopicController use:" + (endTime - startTime)
				+ "ms for request: bbstcon.pho?" + urltail);
		return new ModelAndView("result", "message", message);
	}

	@RequestMapping("/bbscon.php/{urltail}")
	public ModelAndView handleTopTopicRequest(HttpServletRequest request,
			HttpServletResponse response, @PathVariable String urltail)
			throws Exception {
		long startTime = System.currentTimeMillis();
		String message = null;

		JSONArray jsonArray = JSONArray.fromObject(topicManager
				.getTopTopicDetail("bbscon.php?" + urltail));
		message = jsonArray.toString();
		response.setHeader("Cache-Control", "no-cache");
		response.setContentType("text/json;charset=gb2312");
		long endTime = System.currentTimeMillis();
		logger.info("TopicController use:" + (endTime - startTime)
				+ "ms for request: bbscon.php?" + urltail);
		return new ModelAndView("result", "message", message);
	}

}
