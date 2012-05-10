package net.bdwm.api.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.bdwm.api.utils.TopicManager;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;


/**
 * 
 * @author Ruhao Yao
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
	
	@RequestMapping("/{urlpre}/{urltail}")
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response, @PathVariable String urlpre, @PathVariable String urltail)
			throws Exception {
		String message = null;
		
		topicManager.getTopicDetail(urlpre+"?"+urltail);

		response.setCharacterEncoding("UTF-8");
		return new ModelAndView("result", "message", message);
	}

}
