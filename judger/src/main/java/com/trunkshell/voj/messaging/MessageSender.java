package com.trunkshell.voj.messaging;

import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

/**
 * 消息发送服务.
 * @author Xie Haozhe
 */
@Component
public class MessageSender {
	/**
	 * 发送消息至消息队列.
	 * @param mapMessage - Key-Value格式的消息
	 */
	public void sendMessage(final Map<String, Object> mapMessage) {
		jmsTemplate.convertAndSend(mapMessage);
	}

	/**
	 * 自动注入的JmsTemplate对象.
	 * 用于发送消息至消息队列.
	 */
	@Autowired
	private JmsTemplate jmsTemplate;
	
	/**
	 * 日志记录器.
	 */
	@SuppressWarnings("unused")
	private static final Logger logger = LogManager.getLogger(MessageSender.class);
}