package io.training.cat.simple;

import com.dianping.cat.Cat;
import com.dianping.cat.message.Transaction;

public class Training {
	
	public static void main(String[] args) {
		while(true) {
			Transaction t = null;
			try {
				t = Cat.getProducer().newTransaction("URL", "/helloworld");
				System.out.println("======================");
				t.setStatus(Transaction.SUCCESS);
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				t.complete();
			}
			
		}
		
	}

}
