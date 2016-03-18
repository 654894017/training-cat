package io.training.cat.simple;

import com.dianping.cat.Cat;
import com.dianping.cat.message.Message;
import com.dianping.cat.message.Transaction;

public class Training {
	
	public static void main(String[] args) {
		int i = 0;
		while(true) {
			Transaction t = null;
			i = i++ % 10;
			try {
				t = Cat.getProducer().newTransaction("URL====", "/helloworld"+i);
				System.out.println("======================");
				t.setStatus(Message.SUCCESS);
				t.addData("============vadf" + i);
				Cat.getProducer().newEvent("Training", i++ + "======");
				Thread.sleep(2000);
				t.complete();
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				
			}
			
		}
		
	}

}
