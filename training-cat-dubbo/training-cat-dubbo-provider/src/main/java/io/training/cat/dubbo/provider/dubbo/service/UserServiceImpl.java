package io.training.cat.dubbo.provider.dubbo.service;

import io.training.cat.dubbo.api.User;
import io.training.cat.dubbo.api.UserService;

import java.util.List;

import com.alibaba.dubbo.config.annotation.Service;

@Service(protocol = { "dubbo" })
public class UserServiceImpl implements UserService {
	@Override
	public List<User> findAll() {
		return null;
	}

	@Override
	public String create(User user) {
		System.out.println(user.getName());
		return "ksadjfsdf";
	}

}
