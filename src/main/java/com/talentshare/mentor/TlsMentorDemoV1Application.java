package com.talentshare.mentor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "Mentor Rest Service", version = "1.0", description = "Talent Share Portal - Mentor Rest Service"))
public class TlsMentorDemoV1Application {

	public static void main(String[] args) {
		SpringApplication.run(TlsMentorDemoV1Application.class, args);
	}

}
