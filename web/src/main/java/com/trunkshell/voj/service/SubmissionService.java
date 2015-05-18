package com.trunkshell.voj.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.trunkshell.voj.mapper.LanguageMapper;
import com.trunkshell.voj.mapper.ProblemMapper;
import com.trunkshell.voj.mapper.SubmissionMapper;
import com.trunkshell.voj.model.Language;
import com.trunkshell.voj.model.Problem;
import com.trunkshell.voj.model.Submission;
import com.trunkshell.voj.model.User;

/**
 * 提交类(Submission)的业务逻辑层.
 * @author Xie Haozhe
 */
@Service
@Transactional
public class SubmissionService {
	/**
	 * 根据评测记录的唯一标识符获取评测记录对象.
	 * @param submissionId - 评测记录的唯一标识符
	 * @return 评测记录对象
	 */
	public Submission getSubmission(long submissionId) {
		return submissionMapper.getSubmission(submissionId);
	}
	
	/**
	 * 获取评测记录列表.
	 * @param limit - 每次加载评测记录的数量
	 * @return 试题列表(List<Submission>对象)
	 */
	public List<Submission> getSubmissions(int limit) {
		return submissionMapper.getSubmissions(limit);
	}
	
	/**
	 * 获取评测记录列表.
	 * @param offset - 评测记录唯一标识符的起始序号
	 * @param limit - 每次加载评测记录的数量
	 * @return 试题列表(List<Submission>对象)
	 */
	public List<Submission> getSubmissions(long offset, int limit) {
		return submissionMapper.getSubmissionsUsingOffset(offset, limit);
	}
	
	/**
	 * 获取某个用户对某个试题的提交记录.
	 * @param problemId - 试题的唯一标识符
	 * @param userId - 用户的唯一标识符
	 * @param limit - 每次加载评测记录的数量
	 * @return 某个用户对某个试题的提交记录
	 */
	public List<Submission> getSubmissionUsingProblemIdAndUserId(long problemId, long userId, int limit) {
		return submissionMapper.getSubmissionUsingProblemIdAndUserId(problemId, userId, limit);
	}
	
	/**
	 * 获取某个用户在某个试题ID区间段内的评测结果.
	 * @param userId - 用户的唯一标识符
	 * @param problemIdLowerBound - 试题ID区间的下界
	 * @param problemIdUpperBound - 试题ID区间的上界
	 * @return 某个试题ID区间段内的通过的评测结果
	 */
	public Map<Long, Submission> getSubmissionOfProblems(long userId, long problemIdLowerBound, long problemIdUpperBound) {
		int hashMapSize = (int)(problemIdUpperBound - problemIdLowerBound + 1);
		Map<Long, Submission> submissionOfProblems = new HashMap<Long, Submission>(hashMapSize, 1);
		List<Submission> latestSubmission = submissionMapper.getLatestSubmissionOfProblems(userId, problemIdLowerBound, problemIdUpperBound);
		List<Submission> acceptedSubmission = submissionMapper.getAcceptedSubmissionOfProblems(userId, problemIdLowerBound, problemIdUpperBound);
		
		for ( Submission s : latestSubmission ) {
			long problemId = s.getProblem().getProblemId();
			submissionOfProblems.put(problemId, s);
		}
		// 使用已通过的评测记录更新部分值
		for ( Submission s : acceptedSubmission ) {
			long problemId = s.getProblem().getProblemId();
			submissionOfProblems.put(problemId, s);
		}
		return submissionOfProblems;
	}
	
	/**
	 * 获取用户评测记录概况.
	 * @param userId - 用户的唯一标识符
	 * @return 一个包含用户评测记录概况的HashMap
	 */
	public Map<String, Long> getUserSubmissionStats(long userId) {
		long acceptedSubmission = submissionMapper.getAcceptedSubmissionUsingUserId(userId);
		long totalSubmission = submissionMapper.getTotalSubmissionUsingUserId(userId);
		long  acRate = 0;
		if ( totalSubmission != 0 ) {
			acRate = acceptedSubmission * 100 / totalSubmission;
		}
		
		Map<String, Long> submissionStats = new HashMap<String, Long>(4, 1);
		submissionStats.put("acceptedSubmission", acceptedSubmission);
		submissionStats.put("totalSubmission", totalSubmission);
		submissionStats.put("acRate", acRate);
		return submissionStats;
	}
	
	/**
	 * 创建提交记录, 并将评测任务加入消息队列.
	 * @param user - 已登录的用户对象
	 * @param problemId - 试题的唯一标识符
	 * @param languageSlug - 编程语言的唯一英文缩写
	 * @param code - 代码
	 * @param isCsrfTokenValid - CSRF的Token是否正确
	 * @return 一个包含提交记录创建结果的Map<String, Object>对象, 并包含创建的提交记录的唯一标识符.
	 */
	public Map<String, Object> createSubmission(User user, long problemId, 
			String languageSlug, String code, boolean isCsrfTokenValid) {
		Problem problem = problemMapper.getProblem(problemId);
		Language language = languageMapper.getLanguageUsingSlug(languageSlug);
		
		Submission submission = new Submission(problem, user, language, code);
		
		@SuppressWarnings("unchecked")
		Map<String, Object> result = (Map<String, Object>) getSubmissionCreationResult(submission, isCsrfTokenValid);
		boolean isSuccessful = (Boolean)result.get("isSuccessful");
		if ( isSuccessful ) {
			submissionMapper.createSubmission(submission);
			
			long submissionId = submission.getSubmissionId();
			result.put("submissionId", submissionId);
		}
		return result;
	}
	
	/**
	 * 验证提交记录数据.
	 * @param submission - 待创建的提交记录对象
	 * @param isCsrfTokenValid - CSRF的Token是否正确
	 * @return 一个包含提交记录的验证结果的Map<String, Boolean>对象
	 */
	private Map<String, ? extends Object> getSubmissionCreationResult(Submission submission, boolean isCsrfTokenValid) {
		Map<String, Boolean> result = new HashMap<String, Boolean>();
		String code = submission.getCode();
		result.put("isUserLogined", submission.getUser() != null);
		result.put("isProblemExists", submission.getProblem() != null);
		result.put("isLanguageExists", submission.getLanguage() != null);
		result.put("isCodeEmpty", code == null || code.length() == 0);
		result.put("isCsrfTokenValid", isCsrfTokenValid);
		
		boolean isSuccessful = result.get("isUserLogined")    &&  result.get("isProblemExists") &&
							   result.get("isLanguageExists") && !result.get("isCodeEmpty")     &&
							   result.get("isCsrfTokenValid");
		result.put("isSuccessful", isSuccessful);
		return result;
	}
	
	/**
	 * 自动注入的SubmissionMapper对象.
	 */
	@Autowired
	private SubmissionMapper submissionMapper;
	
	/**
	 * 自动注入的ProblemMapper对象.
	 */
	@Autowired
	private ProblemMapper problemMapper;
	
	/**
	 * 自动注入的LanguageMapper对象.
	 */
	@Autowired
	private LanguageMapper languageMapper;
	
	/**
     * 日志记录器.
     */
	@SuppressWarnings("unused")
	private Logger logger = LogManager.getLogger(SubmissionService.class);
}
