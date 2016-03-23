package io.training.cat.dubbo.consumer.controller;

import io.training.cat.dubbo.api.UserService;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.rpc.RpcContext;
import com.dianping.cat.Cat;
import com.dianping.cat.CatConstants;
import com.dianping.cat.message.Event;
import com.dianping.cat.message.Transaction;
import com.dianping.cat.message.spi.MessageTree;

@RestController
public class TrainingController {
	
	@Reference(url="dubbo://127.0.0.1:20880", lazy=true)
	private UserService userService;
	
	@RequestMapping("/hello")
	public String hello(String name) {
		Transaction t = Cat.newTransaction("URL", "/hello");
		String serverMessageId = Cat.getProducer().createMessageId();
		MessageTree tree = Cat.getManager().getThreadLocalMessageTree();
		if (null == tree) {
			Cat.setup(null);
			tree = Cat.getManager().getThreadLocalMessageTree();
		}
		
		String rootMessageId = tree.getRootMessageId() == null ? tree.getMessageId() : tree.getRootMessageId();
		String currentMessageId = tree.getMessageId();
		
		// 传递上下文
		RpcContext.getContext().setAttachment(CatConstants.PIGEON_ROOT_MESSAGE_ID, rootMessageId);
		RpcContext.getContext().setAttachment(CatConstants.PIGEON_CURRENT_MESSAGE_ID, currentMessageId);
		RpcContext.getContext().setAttachment(CatConstants.PIGEON_SERVER_MESSAGE_ID, serverMessageId);
		Cat.logEvent(CatConstants.TYPE_REMOTE_CALL, CatConstants.NAME_REQUEST, Transaction.SUCCESS, serverMessageId);
		Transaction t1 = Cat.newTransaction("调用远程Dubbo服务", "findAll");
		System.out.println("==========" +  (userService == null));
		String result = "Hello: " + name + userService.findAll().size();
		t1.setStatus(Transaction.SUCCESS);
		t1.complete();
		Cat.logEvent("Call", "DubboServer", Event.SUCCESS, "parentId:" + serverMessageId+ "|msgId:" +  serverMessageId + " End...");
		t.setStatus(Transaction.SUCCESS);
		t.complete();
		return result;
	}

}
