package io.training.cat.dubbo.consumer.config;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@SpringBootApplication
@ComponentScan(basePackages = "io.training.cat.dubbo")
public class WebConfig extends WebMvcConfigurerAdapter {

}
