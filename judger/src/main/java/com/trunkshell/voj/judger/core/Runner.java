package com.trunkshell.voj.judger.core;

import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import com.trunkshell.voj.judger.util.NativeLibraryLoader;

/**
 * 本地程序执行器, 用于执行本地应用程序.
 * 包括编译器(gcc)以及用户提交的代码所编译出的程序.
 * 
 * @author Xie Haozhe
 */
@Component
public class Runner {
	/**
	 * 获取程序运行结果.
	 * @param commandLine - 待执行程序的命令行
	 * @param inputFilePath - 输入文件路径(可为NULL)
	 * @param outputFilePath - 输出文件路径(可为NULL)
	 * @param timeLimit - 时间限制(单位ms, 0表示不限制)
	 * @param memoryLimit - 内存限制(单位KB, 0表示不限制)
	 * @return 一个包含程序运行结果的Map<String, Object>对象
	 */
	public native Map<String, Object> getRuntimeResult(String commandLine, 
			String inputFilePath, String outputFilePath, int timeLimit, int memoryLimit);
	
	/**
	 * 日志记录器.
	 */
	private static final Logger logger = LogManager.getLogger(Runner.class);
	
	/**
	 * Load Native Library.
	 */
	static {
		try {
			NativeLibraryLoader.loadLibrary("JudgerCore");
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.catching(ex);
		}
	}
}
