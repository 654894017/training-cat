package io.training.cat.dubbo.provider.dubbo.service;

import io.training.cat.dubbo.api.User;
import io.training.cat.dubbo.api.UserService;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.dubbo.rpc.RpcContext;
import com.dianping.cat.Cat;
import com.dianping.cat.CatConstants;
import com.dianping.cat.message.Event;
import com.dianping.cat.message.Transaction;
import com.dianping.cat.message.spi.MessageTree;

@Service(protocol = { "dubbo" })
public class UserServiceImpl implements UserService {
	@Override
	public List<User> findAll() {
		String rootMessageId = RpcContext.getContext().getAttachment(CatConstants.PIGEON_ROOT_MESSAGE_ID);
		String currentMessageId = RpcContext.getContext().getAttachment(CatConstants.PIGEON_CURRENT_MESSAGE_ID);
		String serverMessageId = RpcContext.getContext().getAttachment(CatConstants.PIGEON_SERVER_MESSAGE_ID);
		Transaction t = Cat.newTransaction("RemoteCall", "findAll");
		
		MessageTree tree = Cat.getManager().getThreadLocalMessageTree();
		if (null == tree) {
			Cat.setup(null);
			tree = Cat.getManager().getThreadLocalMessageTree();
		}
		
		tree.setRootMessageId(rootMessageId);
		tree.setParentMessageId(serverMessageId);
		tree.setMessageId(currentMessageId);
		
		List<User> users = new ArrayList<User>();
		int i = 0;
		
		while (i ++ < 10) {
			User u = new User();
			u.setId(i);
			u.setName("Percy00" + i);
			users.add(u);
		}
		
		Cat.logEvent("Call", "Params", Event.SUCCESS, "parentId:" + serverMessageId + "|msgId:" + currentMessageId + "End...");
		t.setStatus(Transaction.SUCCESS);
		t.complete();
		return users;
	}

	@Override
	public String create(User user) {
		System.out.println(user.getName());
		return "ksadjfsdf";
	}

}
