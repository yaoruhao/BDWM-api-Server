package net.bdwm.api.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.bdwm.api.utils.HotTopicsManager;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;


/**
 * 
<<<<<<< HEAD
 * @author Ruhao Yao: yaoruhao@gmail.com
=======
 * @author Ruhao Yao
>>>>>>> origin/develop
 *
 */
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

	@RequestMapping("/{operation}")
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response, @PathVariable String operation)
			throws Exception {
		long startTime = System.currentTimeMillis();
		String message = null;
		if ("division".equals(operation)) {
			logger.info("division request");
			message = hotTopicsManager.getHotTopicsModel()
					.getDivisionHotJsonObject().toString();
		} else if ("topten".equals(operation)) {
			logger.info("top ten request");
			message = hotTopicsManager.getHotTopicsModel().getTopTenJsonArray()
					.toString();
		} else if ("academic".equals(operation)) {
			logger.info("academic request");
			message = hotTopicsManager.getHotTopicsModel()
					.getAcademicHotJsonArray().toString();
		} else if ("school".equals(operation)) {
			logger.info("school request");
			message = hotTopicsManager.getHotTopicsModel()
					.getSchoolHotJsonArray().toString();
		} else {
			logger.warn("HotTopicsController invalid request:" + operation);
		}
<<<<<<< HEAD
		response.setHeader("Cache-Control", "no-cache");
		response.setContentType("text/json;charset=gb2312");
=======
		response.setCharacterEncoding("UTF-8");
>>>>>>> origin/develop
		long endTime = System.currentTimeMillis();
		logger.info("HotTopicsController use:"+(endTime - startTime)+"ms for request:"+operation);
		return new ModelAndView("result", "message", message);
	}

}
