package pu.employee.config.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.web.filter.HiddenHttpMethodFilter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@EnableWebMvc
@SpringBootApplication(scanBasePackages={"pu.employee.web","pu.employee.domain"})
@EnableMongoRepositories(basePackages="pu.employee.domain")
@Import(SecurityConfig.class)
public class SimpleSpringWebMvcConfig implements WebMvcConfigurer {
	
	public static void main(String[] args) {
		SpringApplication.run(SimpleSpringWebMvcConfig.class, args);
	}
	
	
    @Bean
    @SuppressWarnings({"rawtypes", "unchecked"})
    public FilterRegistrationBean hiddenHttpMethodFilter(){
		FilterRegistrationBean hiddenHttpMethodFilter = new FilterRegistrationBean(new HiddenHttpMethodFilter());
        hiddenHttpMethodFilter.addUrlPatterns("/*");
        return hiddenHttpMethodFilter;
    }
    

    


	

}
