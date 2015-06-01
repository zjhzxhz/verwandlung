package com.trunkshell.voj.judger.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 评测记录的Model.
 * 对应数据库中的voj_submissions数据表.
 * 
 * @author Xie Haozhe
 */
public class Submission implements Serializable {
	/**
	 * 评测记录类的默认构造函数.
	 */
	public Submission() { }
	
	/**
	 * 评测记录类的构造函数.
	 * @param submissionId - 评测记录的唯一标识符
	 * @param problemId - 评测对应的试题的唯一标识符
	 * @param uid - 评测提交者的用户的唯一标识符
	 * @param language - 提交所使用的语言对象
	 * @param submitTime - 评测提交时间
	 * @param executeTime - 评测开始执行时间
	 * @param usedTime - 评测运行总时间
	 * @param usedMemory - 评测运行占用最大内存
	 * @param judgeResultSlug - 评测结果的唯一标识符
	 * @param judgeScore - 评测运行得分
	 * @param judgeLog - 评测运行日志
	 * @param code - 评测所执行的代码
	 */
	public Submission(long submissionId, long problemId, long uid, Language language, Date submitTime, 
			Date executeTime, int usedTime, int usedMemory, String judgeResultSlug, int judgeScore, 
			String judgeLog, String code) { 
		this.submissionId = submissionId;
		this.problemId = problemId;
		this.uid = uid;
		this.language = language;
		this.submitTime = submitTime;
		this.executeTime = executeTime;
		this.usedTime = usedTime;
		this.usedMemory = usedMemory;
		this.judgeResultSlug = judgeResultSlug;
		this.judgeScore = judgeScore;
		this.judgeLog = judgeLog;
		this.code = code;
	}
	
	/**
	 * 获取评测记录的唯一标识符.
	 * @return 评测记录的唯一标识符
	 */
	public long getSubmissionId() {
		return submissionId;
	}
	
	/**
	 * 设置评测记录的唯一标识符.
	 * @param submissionId - 评测记录的唯一标识符.
	 */
	public void setSubmissionId(long submissionId) {
		this.submissionId = submissionId;
	}
	
	/**
	 * 获取评测对应的试题的唯一标识符.
	 * @return 试题的唯一标识符
	 */
	public long getProblemId() {
		return problemId;
	}
	
	/**
	 * 设置评测对应的试题的唯一标识符.
	 * @param problemId - 试题的唯一标识符
	 */
	public void setProblemId(long problemId) {
		this.problemId = problemId;
	}
	
	/**
	 * 获取评测提交者的用户唯一标识符.
	 * @return 用户的唯一标识符
	 */
	public long getUid() {
		return uid;
	}
	
	/**
	 * 设置评测提交者的用户唯一标识符.
	 * @param uid - 用户唯一标识符
	 */
	public void setUid(long uid) {
		this.uid = uid;
	}
	
	/**
	 * 获取提交所使用的语言对象.
	 * @return 提交所使用的语言对象
	 */
	public Language getLanguage() {
		return language;
	}
	
	/**
	 * 设置提交所使用的语言对象.
	 * @param language - 提交所使用的语言对象
	 */
	public void setLanguage(Language language) {
		this.language = language;
	}
	
	/**
	 * 获取评测提交时间.
	 * @return 评测提交时间
	 */
	public Date getSubmitTime() {
		return submitTime;
	}
	
	/**
	 * 设置评测提交时间.
	 * @param submitTime - 评测提交时间
	 */
	public void setSubmitTime(Date submitTime) {
		this.submitTime = submitTime;
	}
	
	/**
	 * 获取评测开始执行时间.
	 * @return 评测开始执行时间
	 */
	public Date getExecuteTime() {
		return executeTime;
	}
	
	/**
	 * 设置评测开始执行时间.
	 * @param executeTime - 评测开始执行时间
	 */
	public void setExecuteTime(Date executeTime) {
		this.executeTime = executeTime;
	}
	
	/**
	 * 获取评测运行总时间.
	 * @return 评测运行总时间
	 */
	public int getUsedTime() {
		return usedTime;
	}
	
	/**
	 * 设置评测运行总时间.
	 * @param usedTime - 评测运行总时间
	 */
	public void setUsedTime(int usedTime) {
		this.usedTime = usedTime;
	}
	
	/**
	 * 获取评测运行占用最大内存.
	 * @return 评测运行占用最大内存
	 */
	public int getUsedMemory() {
		return usedMemory;
	}
	
	/**
	 * 设置评测运行占用最大内存.
	 * @param usedMemory - 评测运行占用最大内存
	 */
	public void setUsedMemory(int usedMemory) {
		this.usedMemory = usedMemory;
	}
	
	/**
	 * 获取评测结果的唯一英文缩写.
	 * @return 评测结果的唯一英文缩写
	 */
	public String getJudgeResultSlug() {
		return judgeResultSlug;
	}
	
	/**
	 * 设置评测结果.
	 * @param judgeResultSlug - 评测结果的唯一英文缩写
	 */
	public void setJudgeResultSlug(String judgeResultSlug) {
		this.judgeResultSlug = judgeResultSlug;
	}
	
	/**
	 * 获取评测运行得分.
	 * @return 评测运行得分
	 */
	public int getJudgeScore() {
		return judgeScore;
	}
	
	/**
	 * 设置评测运行得分.
	 * @param judgeScore - 评测运行得分
	 */
	public void setJudgeScore(int judgeScore) {
		this.judgeScore = judgeScore;
	}
	
	/**
	 * 获取评测运行日志.
	 * @return 评测运行日志
	 */
	public String getJudgeLog() {
		return judgeLog;
	}
	
	/**
	 * 设置评测运行日志
	 * @param judgeLog - 评测运行日志
	 */
	public void setJudgeLog(String judgeLog) {
		this.judgeLog = judgeLog;
	}
	
	/**
	 * 设置评测所执行的代码.
	 * @return 评测所执行的代码
	 */
	public String getCode() {
		return code;
	}
	
	/**
	 * 设置评测所执行的代码.
	 * @param code - 评测所执行的代码
	 */
	public void setCode(String code) {
		this.code = code;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return String.format("Submission [ID=%d, ProblemId={%s}, Uid={%s}, Language={%s}, SubmitTime={%s}, "
				+ "ExecuteTime={%s}, UsedTime=%d, UsedMemory=%d, JudgeResultSlug={%s}, JudgeScore=%d, "
				+ "JudgeLog=%s, Code=%s]",
                new Object[] { submissionId, problemId, uid, language, submitTime, executeTime, usedTime, usedMemory,
						judgeResultSlug, judgeScore, judgeLog, code});
	}
	
	/**
	 * 评测记录的唯一标识符. 
	 */
	private long submissionId;
	
	/**
	 * 评测对应的试题对象.
	 */
	private long problemId;
	
	/**
	 * 评测提交者用户的唯一标识符. 
	 */
	private long uid;
	
	/**
	 * 提交所使用的语言对象.
	 */
	private Language language;
	
	/**
	 * 评测提交时间.
	 */
	private Date submitTime;
	
	/**
	 * 评测开始执行时间.
	 */
	private Date executeTime;
	
	/**
	 * 评测运行总时间.
	 */
	private int usedTime;
	
	/**
	 * 评测运行占用最大内存.
	 */
	private int usedMemory;
	
	/**
	 * 评测结果.
	 */
	private String judgeResultSlug;
	
	/**
	 * 评测运行得分.
	 */
	private int judgeScore;
	
	/**
	 * 评测运行日志.
	 */
	private String judgeLog;
	
	/**
	 * 评测所执行的代码.
	 */
	private String code;
	
	/**
	 * 唯一的序列化标识符.
	 */
	private static final long serialVersionUID = -6017296127470986766L;
}
