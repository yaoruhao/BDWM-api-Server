package net.bdwm.api.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.bdwm.api.utils.HotTopicsManager;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/hottopics")
public class HotTopicsController {

	private static Log logger = LogFactory.getLog(HotTopicsController.class);
	private static HotTopicsManager hotTopicsManager;
	
	public void init() {
		
		if (hotTopicsManager == null) {
			logger.error("HotTopicsController init failed, hotTopicsManager is null.");
		}
		new Thread(hotTopicsManager).start();
		
		
	}
	
	public static HotTopicsManager getHotTopicsManager() {
		return hotTopicsManager;
	}

	public static void setHotTopicsManager(HotTopicsManager hotTopicsManager) {
		HotTopicsController.hotTopicsManager = hotTopicsManager;
	}

	@RequestMapping("/hello")
	public ModelAndView handleRequest(HttpServletRequest arg0,
			HttpServletResponse arg1) throws Exception {
		String message = "Hot topics, Spring 3.0!";
		System.out.println(message);
		return new ModelAndView("hello", "message", message);
	}
	
	@RequestMapping(value = "/division", method = RequestMethod.GET)
	@ResponseBody
	public String divisionHotTopics()  {
		logger.info("divisioin");
		String result = "division hottopics";

		return result;
	}
	
	@RequestMapping(value = "/all", method = RequestMethod.GET)
	@ResponseBody
	public String allHotTopics()  {
		logger.warn("all");
		return "all hottopics";
	}

}
