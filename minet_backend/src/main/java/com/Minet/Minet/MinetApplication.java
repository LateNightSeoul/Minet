package com.Minet.Minet;

import com.Minet.Minet.service.FilePath;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
@EnableConfigurationProperties({FilePath.class })
public class MinetApplication {

	public static void main(String[] args) {
		SpringApplication.run(MinetApplication.class, args);
	}

}
