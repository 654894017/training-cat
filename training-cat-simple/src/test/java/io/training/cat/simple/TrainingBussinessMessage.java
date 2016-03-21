package io.training.cat.simple;

import org.codehaus.plexus.logging.Logger;
import org.junit.Test;
import org.unidal.lookup.logger.LoggerFactory;

import com.dianping.cat.Cat;
import com.dianping.cat.message.Event;
import com.dianping.cat.message.Transaction;
import com.dianping.cat.message.spi.MessageTree;

public class TrainingBussinessMessage {
	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	/**
	 * 测试一个最简单的示例
	 */
	@Test
	public void testBasicBussiness() {
		log.debug("测试最基本的事务");
		
		for (int i = 0; i < 1000; i++) {
			Transaction t = Cat.newTransaction("统计消息类型：URL/SQL/Bussiness。。。一级纬度", "具体消息分类，属于一个二级纬度：/index；Select * from ...");
			// 增加事件记录
			Cat.logEvent("RemoteLink", "sina", Event.SUCCESS, "http://sina.com.cn/");
			// 增加记录变量
			t.addData("channel=channel" + i % 5);
			
			Cat.logMetricForCount("Receipt Verify Success");
			Cat.logMetricForCount("Receipt Verify Success 2", 2);
			Cat.logMetricForDuration("Receipt Verify Druation", 10);
			Cat.logMetricForSum("sum Value", 20);
			Cat.logMetricForSum("sum Value2", 20, 2);
			
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			t.complete();
		}
	}
	
	@Test
	public void testMessageTree() {
		for (int i = 0; i < 500; i++) {
			Transaction t = Cat.newTransaction("URL", "/order/submitOrder");
			MessageTree tree = (MessageTree) Cat.getManager().getThreadLocalMessageTree();
			// 这里可以设置不同的域服务器，相当于app.properties那个配置文件
			tree.setDomain("PayOrder");
			Cat.logMetric("order", "quantity", 1, "channel", "channel" + i % 5);
			Cat.logMetric("payment.pending", "amount", i, "channel", "channel" + i % 5);
			Cat.logMetric("payment.success", "amount", i, "channel", "channel" + i % 5);
			t.addData("channel=channel" + i % 5);
			
			try {
				Thread.sleep(300);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			t.complete();
		}
	}

}
