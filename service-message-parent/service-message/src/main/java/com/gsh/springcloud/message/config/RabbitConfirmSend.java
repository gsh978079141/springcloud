//package com.gsh.springcloud.message.config;
//
//import org.slf4j.LoggerFactory;
//
//import java.util.UUID;
//import java.util.concurrent.ConcurrentHashMap;
//import java.util.concurrent.ConcurrentMap;
//
//import javax.annotation.PostConstruct;
//
//import org.slf4j.Logger;
//import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
//import org.springframework.amqp.rabbit.core.RabbitTemplate;
//import org.springframework.amqp.rabbit.support.CorrelationData;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import com.itcloud.config.RabbitConfig;
//import com.itcloud.dao.IMsgBrokerDao;
//import com.itcloud.entity.MsgBroker;
//import com.itcloud.service.IRabbitSend;
//
///**
//	需要进行消息确认
//*/
//@Service(value = "rabbitConfirmSend")
//public class RabbitConfirmSend implements IRabbitSend {
//
//	private static Logger log = LoggerFactory.getLogger(RabbitConfirmSend.class);
//
//	/**
//	 * 用于缓存correlationDataId
//	 */
//	private ConcurrentMap<String, Object> curMap = new ConcurrentHashMap<>();
//
//	@Autowired
//	private IMsgBrokerDao msgBrokerDao;
//
//	@Autowired
//	private RabbitTemplate rabbitTemplate;
//	/***
//	 * @Description @PostConstruct和@PreConstruct。修饰一个非静态的void()方法.而且这个方法不能有抛出异常声明。
//	 * 1.@PostConstruct说明
//	 * 被@PostConstruct修饰的方法会在服务器加载Servlet的时候运行，并且只会被服务器调用一次，类似于Serclet的inti()方法。
//	 * 被@PostConstruct修饰的方法会在构造函数之后，init()方法之前运行。
//	 * 2.@PreDestroy说明
//	 * 被@PreDestroy修饰的方法会在服务器卸载Servlet的时候运行，并且只会被服务器调用一次，类似于Servlet的destroy()方法。
//	 * 被@PreDestroy修饰的方法会在destroy()方法之后运行，在Servlet被彻底卸载之前。（详见下面的程序实践）
//	 * @Date 16:38 2019-04-25
//	 * @param
//	 * @return void
//	 * @Author Gsh
//	 **/
//	@PostConstruct
//	public void init() {
//		// 消息确认机制
//		((CachingConnectionFactory) this.rabbitTemplate.getConnectionFactory()).setPublisherConfirms(true);
//		// 这是一个同步的消息确认回调，同一时刻只有一个消息才能进行消息确认
//		rabbitTemplate.setConfirmCallback((correlationData, ack, cause) -> {
//			if (ack) { // 消息投递成功
//				log.info("Message send success, correLationData:{}", correlationData.getId());
//				//对于失败的消息，投递成功之后，更改状态
//				msgBrokerDao.updateIfExit(correlationData.getId());
//				this.curMap.remove(correlationData.getId());
//			} else { // 消息投递失败, 将消息存入数据库重新消费
//				Object[] msgInfo = (Object[]) this.curMap.get(correlationData.getId());
//				log.error("Message send failure. CorrelationID:{}, cause:{}, message is:{}", correlationData.getId(),
//						cause, msgInfo[2]);
//				saveMssage(correlationData.getId(), cause, (String) msgInfo[1], msgInfo[2]);
//			}
//		});
//	}
//
//	@Override
//	public String sendDirectMsg(String routingKey, Object message) {
//		return this.sendDirectMsg(routingKey, message, UUID.randomUUID().toString());
//	}
//
//	@Override
//	public String sendDirectMsg(String routingKey, Object message, String correlationDataId) {
//		CorrelationData correlationData = new CorrelationData(correlationDataId);
//		// 缓存ID,当消息投递失败的时候，从缓存中获取出来,进行重发
//		curMap.put(correlationDataId, new Object[] { "D", routingKey, message });
//		rabbitTemplate.convertAndSend(RabbitConfig.EXCHANGE_DIRECT, routingKey, message, correlationData);
//		log.info("Message send {}, message is :{}", correlationData.getId(), message);
//		return correlationDataId;
//	}
//
//	/**
//	 * 将未能消费的消息存入数据库，使用定时任务进行重新消费
//	 */
//	private void saveMssage(String correlationDataId, String reason, String rountingKey, Object message) {
//		MsgBroker msgBroker = new MsgBroker();
//		msgBroker.setCorrId(correlationDataId);
//		msgBroker.setStatus(RabbitConfig.SENDING_STATUS);
//		// 这里讲消息体也存在数据库,如果数量有的很大，有的很小，这对数据库很是浪费，这里可以将消息静态化。
//		// 当消息发送成功之后，可以清楚静态化文件
//		msgBroker.setMessage((String) message);
//		msgBroker.setRountingKey(rountingKey);
//		msgBroker.setReason(reason);
//		msgBrokerDao.insertMsgBroker(msgBroker);
//		this.curMap.remove(correlationDataId);
//	}
//}