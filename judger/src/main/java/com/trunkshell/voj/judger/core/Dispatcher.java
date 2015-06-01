package com.trunkshell.voj.judger.core;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.trunkshell.voj.judger.application.ApplicationDispatcher;
import com.trunkshell.voj.judger.exception.IllgealSubmissionException;
import com.trunkshell.voj.judger.mapper.SubmissionMapper;
import com.trunkshell.voj.judger.model.Submission;
import com.trunkshell.voj.judger.util.RandomStringGenerator;

/**
 * 评测机调度器.
 * 用于完成评测机的评测流程.
 * 每个阶段结束后推送消息至消息队列; 评测结束后写入数据库.
 * 
 * @author Xie Haozhe
 */
@Component
public class Dispatcher {
	/**
	 * 创建新的评测任务.
	 * 每次只运行一个评测任务.
	 * @param submissionId - 提交记录的唯一标识符
	 * @throws IllgealSubmissionException 
	 */
	public void createNewTask(long submissionId) throws IllgealSubmissionException {
		synchronized(this) {
			String baseDirectory = String.format("%s/voj-%s", new Object[] {workBaseDirectory, submissionId});
			String baseFileName = RandomStringGenerator.getRandomString(12, RandomStringGenerator.Mode.ALPHA);
			Submission submission = submissionMapper.getSubmission(submissionId);
			
			if ( submission == null ) {
				throw new IllgealSubmissionException(
						String.format("Illegal submission #%s", 
						new Object[] { submissionId }));
			}
			
			preprocess(submission, baseDirectory, baseFileName);
			compile(submission, baseDirectory, baseFileName);
			runProgram(baseDirectory, baseFileName);
			compareOutput(baseDirectory);
			cleanUp(baseDirectory);
		}
	}
	
	/**
	 * 完成评测前的预处理工作.
	 * 说明: 随机文件名用于防止应用程序自身递归调用.
	 * 
	 * @param submission - 评测记录对象
	 * @param workDirectory - 用于产生编译输出的目录
	 * @param baseFileName - 随机文件名(不包含后缀)
	 */
	private void preprocess(Submission submission, 
			String workDirectory, String baseFileName) {
		try {
			long problemId = submission.getProblemId();
			preprocessor.createTestCode(submission, workDirectory, baseFileName);
			preprocessor.fetchTestPoints(problemId);
		} catch (Exception ex) {
			logger.catching(ex);
			
			long submissionId = submission.getSubmissionId();
			applicationDispatcher.onErrorOccurred(submissionId);
		}
	}
	
	/**
	 * 创建编译任务.
	 * 说明: 随机文件名用于防止应用程序自身递归调用.
	 * 
	 * @param submission - 评测记录对象
	 * @param workDirectory - 用于产生编译输出的目录
	 * @param baseFileName - 随机文件名(不包含后缀)
	 */
	private void compile(Submission submission, 
			String workDirectory, String baseFileName) {
		long submissionId = submission.getSubmissionId();
		Map<String, Object> result = 
				compiler.getCompileResult(submission, workDirectory, baseFileName);
		
		if ( (Boolean)result.get("isSuccessful") ) {
			runProgram(workDirectory, baseFileName);
		}
		applicationDispatcher.onCompileFinished(submissionId, result);
	}

	/**
	 * 执行程序.
	 * @param workDirectory - 编译生成结果的目录以及程序输出的目录
	 * @param baseFileName - 待执行的应用程序文件名(不包含文件后缀)
	 */
	private void runProgram(String workDirectory, String baseFileName) {
		
	}
	
	/**
	 * 比对输出结果.
	 * @param workDirectory
	 */
	private void compareOutput(String workDirectory) {
		
	}
	
	/**
	 * 评测完成后, 清理所生成的文件.
	 * @param baseDirectory - 用于产生输出结果目录
	 */
	private void cleanUp(String baseDirectory) {
		File baseDirFile = new File(baseDirectory);
		if ( baseDirFile.exists() ) {
			try {
				FileUtils.deleteDirectory(baseDirFile);
			} catch (IOException ex) {
				logger.catching(ex);
			}
		}
	}
	
	/**
	 * 自动注入的SubmissionMapper对象.
	 */
	@Autowired
	private SubmissionMapper submissionMapper;
	
	/**
	 * 自动注入的ApplicationDispatcher对象.
	 * 完成每个阶段的任务后推送消息至消息队列.
	 */
	@Autowired
	private ApplicationDispatcher applicationDispatcher;
	
	/**
	 * 自动注入的Preprocessor对象.
	 * 完成编译前的准备工作.
	 */
	@Autowired
	private Preprocessor preprocessor;
	
	/**
	 * 自动注入的Compiler对象.
	 * 完成编译工作.
	 */
	@Autowired
	private Compiler compiler;
	
	/**
	 * 评测机的工作目录.
	 * 用于存储编译结果以及程序输出结果.
	 */
	@Value("${judger.workDir}")
    private String workBaseDirectory;
	
	/**
	 * 日志记录器.
	 */
	private static final Logger logger = LogManager.getLogger(Dispatcher.class);
}
