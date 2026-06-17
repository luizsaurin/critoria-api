package com.example.critoria.api.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

@Configuration
public class SwaggerConfig {

	@Value("${app.name}")
	private String appName;

	@Value("${app.description}")
	private String appDescription;

	@Value("${app.version}")
	private String appVersion;

	@Bean
	OpenAPI openApiConfig() {
		return new OpenAPI()
				.info(new Info()
						.title(appName)
						.version(appVersion)
						.description(appDescription)
						.license(
								new License()
										.name("MIT License")
										.url("https://opensource.org/license/mit/")));
	}

}
