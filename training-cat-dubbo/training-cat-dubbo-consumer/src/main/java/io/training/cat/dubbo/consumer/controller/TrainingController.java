package io.training.cat.dubbo.consumer.controller;

import io.training.cat.dubbo.api.UserService;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.config.annotation.Reference;

@RestController
public class TrainingController {
	
	@Reference(url="dubbo://127.0.0.1:20880", lazy=true)
	private UserService userService;
	
	@RequestMapping("/hello")
	public String hello(String name) {
		System.out.println("==========" +  (userService == null));
		return "Hello: " + name + userService.findAll().size();
	}

}
