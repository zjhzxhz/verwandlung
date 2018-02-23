package org.verwandlung.voj.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.verwandlung.voj.web.model.Contest;
import org.verwandlung.voj.web.service.ContestService;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 处理竞赛的相关请求.
 * 
 * @author Haozhe Xie
 */
@Controller
@RequestMapping(value="/contests")
public class ContestsController {
	/**
	 * 显示竞赛列表页面.
	 * @param keyword - 竞赛的关键词
	 * @param request - HttpRequest对象
	 * @param response - HttpResponse对象
	 * @return 一个包含竞赛列表页面内容的ModelAndView对象
	 */
	@RequestMapping(value="", method=RequestMethod.GET)
	public ModelAndView contestsView(
			@RequestParam(value="keyword", required = false) String keyword,
			HttpServletRequest request, HttpServletResponse response) {
		List<Contest> contests = contestService.getContests(keyword, 0, NUMBER_OF_CONTESTS_PER_PAGE);

		ModelAndView view = new ModelAndView("contests/contests");
		view.addObject("contests", contests);
		view.addObject("currentTime", new Date());
		return view;
	}
	
	/**
	 * 获取竞赛的列表.
	 * @param keyword - 竞赛的关键词
	 * @param startIndex - 当前加载的最后一条记录的索引值 (Index)
	 * @param request - HttpRequest对象
	 * @return 一个包含竞赛列表的HashMap对象
	 */
	@RequestMapping(value="/getContests.action", method=RequestMethod.GET)
	public @ResponseBody Map<String, Object> getContestsAction(
			@RequestParam(value="keyword", required=false) String keyword,
			@RequestParam(value="startIndex") long startIndex,
			HttpServletRequest request) {
		Map<String, Object> result = new HashMap<>(3, 1);

		List<Contest> contests = contestService.getContests(keyword, startIndex, NUMBER_OF_CONTESTS_PER_PAGE);
		result.put("isSuccessful", contests != null && !contests.isEmpty());
		result.put("contests", contests);

		return result;
	}

	/**
	 * 每次查询需要加载竞赛的数量.
	 */
	private static final int NUMBER_OF_CONTESTS_PER_PAGE = 20;

	/**
	 * 自动注入的ContestService对象.
	 */
	@Autowired
	private ContestService contestService;

	/**
	 * 日志记录器.
	 */
	private static final Logger LOGGER = LogManager.getLogger(ContestsController.class);
}
