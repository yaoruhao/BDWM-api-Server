package net.bdwm.api.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.bdwm.api.utils.DivisionManager;
import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * 
 * @author Ruhao Yao: yaoruhao@gmail.com
 * 
 */
@Controller
public class DivisionController {

	private static Log logger = LogFactory.getLog(DivisionController.class);

	@RequestMapping("/division")
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		long startTime = System.currentTimeMillis();
		String message = null;
		JSONObject object = JSONObject.fromObject(DivisionManager
				.getDivisionMap());
		message = object.toString();
		response.setHeader("Cache-Control", "no-cache");
		response.setContentType("text/json;charset=utf-8");
		long endTime = System.currentTimeMillis();
		logger.info("DivisionController use:" + (endTime - startTime)
				+ "ms for division request");
		return new ModelAndView("result", "message", message);
	}

}
