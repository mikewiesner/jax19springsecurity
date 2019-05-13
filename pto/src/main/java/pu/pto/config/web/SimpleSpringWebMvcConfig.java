package pu.pto.config.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.filter.HiddenHttpMethodFilter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import pu.pto.config.core.MethodSecurityConfig;

@EnableWebMvc
@SpringBootApplication(scanBasePackages = { "pu.pto.web", "pu.pto.domain", "pu.pto.backend" })
@EnableMongoRepositories(basePackages = "pu.pto.domain")
@Import({SecurityConfig.class, MethodSecurityConfig.class})
public class SimpleSpringWebMvcConfig implements WebMvcConfigurer {

	public static void main(String[] args) {
		SpringApplication.run(SimpleSpringWebMvcConfig.class, args);
	}

	@Bean
	public FilterRegistrationBean hiddenHttpMethodFilter() {
		FilterRegistrationBean hiddenHttpMethodFilter = new FilterRegistrationBean(new HiddenHttpMethodFilter());
		hiddenHttpMethodFilter.addUrlPatterns("/*");
		return hiddenHttpMethodFilter;
	}
	
	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}




}
