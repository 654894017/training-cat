package io.training.cat.dubbo.provider.dubbo.service;

import io.training.cat.dubbo.api.User;
import io.training.cat.dubbo.api.UserService;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.dubbo.config.annotation.Service;

@Service(protocol = { "dubbo" })
public class UserServiceImpl implements UserService {
	@Override
	public List<User> findAll() {
		List<User> users = new ArrayList<User>();
		int i = 0;
		
		while (i ++ < 10) {
			User u = new User();
			u.setId(i);
			u.setName("Percy00" + i);
			users.add(u);
		}
		
		return users;
	}

	@Override
	public String create(User user) {
		System.out.println(user.getName());
		return "ksadjfsdf";
	}

}
