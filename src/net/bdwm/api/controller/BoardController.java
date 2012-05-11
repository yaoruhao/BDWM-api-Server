package net.bdwm.api.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.bdwm.api.utils.BoardManager;
import net.sf.json.JSONArray;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;


/**
 * 
 * @author Ruhao Yao
 *
 */
@Controller
@RequestMapping("/board")
public class BoardController {

	private static Log logger = LogFactory.getLog(BoardController.class);

	private static BoardManager boardManager;

	public static BoardManager getBoardManager() {
		return boardManager;
	}

	public static void setBoardManager(BoardManager boardManager) {
		BoardController.boardManager = boardManager;
	}

	public void init() {
		boardManager = BoardManager.getInstance();
	}

	@RequestMapping("/{boardId}/skip/{skipId}")
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response, @PathVariable String boardId,
			@PathVariable String skipId) throws Exception {
		long startTime = System.currentTimeMillis();
		String message = null;

		JSONArray array = JSONArray.fromObject(boardManager.getBoardData(
				boardId, skipId));
		message = array.toString();

		response.setCharacterEncoding("UTF-8");
		long endTime = System.currentTimeMillis();
		logger.info("BoardController use:"+(endTime - startTime)+"ms for visit board:"+boardId+" skipId:"+skipId);
		return new ModelAndView("result", "message", message);
	}

}
