package com.lmsapp.main.config;

import java.util.Collections;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
	
	@Bean
	public Docket postsApi() {
		return new Docket(DocumentationType.SWAGGER_2).select()
				.apis(RequestHandlerSelectors.basePackage("com.lmsapp.main")).build().apiInfo(apiInfo())
				.useDefaultResponseMessages(false);
	}
	
//	private ApiInfo apiInfo() {
//		return new ApiInfoBuilder().title("User Service - LMS APP")
//				.description("Use Case Project of Java - Spring Boot - Angular - AWS")
//				.license("This is designed and devloped by Thrushna")
//				.licenseUrl("https://github.com/Thrush9")
//				.version("1.0").build();
//	}
	
//	private ApiKey apiKey() { 
//	    return new ApiKey("JWT", "Authorization", "header"); 
//	}
//	
//	private SecurityContext securityContext() { 
//	    return SecurityContext.builder().securityReferences(defaultAuth()).build(); 
//	} 
//
//	private List<SecurityReference> defaultAuth() { 
//	    AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything"); 
//	    AuthorizationScope[] authorizationScopes = new AuthorizationScope[1]; 
//	    authorizationScopes[0] = authorizationScope; 
//	    return Arrays.asList(new SecurityReference("JWT", authorizationScopes)); 
//	}
//	
//	@Bean
//	public Docket api() {
//	    return new Docket(DocumentationType.SWAGGER_2)
//	      .apiInfo(apiInfo())
//	      .securityContexts(Arrays.asList(securityContext()))
//	      .securitySchemes(Arrays.asList(apiKey()))
//	      .select()
//	      .apis(RequestHandlerSelectors.basePackage("com.lmsapp.main"))
//	      .paths(PathSelectors.any())
//	      .build();
//	}

	private ApiInfo apiInfo() {
	    return new ApiInfo(
	      "User Service - LMS APP",
	      "Use Case Project of Java - Spring Boot - Angular - AWS",
	      "Version - 1.0",
	      "Terms of service",
	      new Contact("D Thrushna Reddy", "https://github.com/Thrush9", "thrush9@gmail.com"),
	      "License of API",
	      "API license URL",
	      Collections.emptyList());
	}
	
}


