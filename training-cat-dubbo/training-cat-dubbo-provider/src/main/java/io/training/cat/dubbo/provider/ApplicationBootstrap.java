package io.training.cat.dubbo.provider;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.dianping.cat.Cat;
import com.dianping.cat.message.Event;
import com.dianping.cat.message.Transaction;

@SpringBootApplication
public class ApplicationBootstrap {

	public static void main(String[] args) {
		SpringApplication.run(ApplicationBootstrap.class, args);

		for (int i = 0; i < 1000; i++) {
			Transaction t = Cat.newTransaction(
					"统计消息类型：URL/SQL/Bussiness。。。一级纬度",
					"具体消息分类，属于一个二级纬度：/index；Select * from ...");
			// 增加事件记录
			Cat.logEvent("RemoteLink", "sina", Event.SUCCESS,
					"http://sina.com.cn/");
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

			// try {
			// System.in.read();
			// } catch (IOException e) {
			// e.printStackTrace();
			// }
			
		}

	}
}
