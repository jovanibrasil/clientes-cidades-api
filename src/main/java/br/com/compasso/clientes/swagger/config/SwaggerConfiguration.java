package br.com.compasso.clientes.swagger.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfiguration {

	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2)
				.select()
				.apis(RequestHandlerSelectors.basePackage("br.com.compasso.clientes"))
				.build()
				.apiInfo(getApiInfo())
				.useDefaultResponseMessages(false)
				.directModelSubstitute(Object.class, java.lang.Void.class); // para respostas vazias
	}

	private ApiInfo getApiInfo() {
		return new ApiInfoBuilder()
				.title("Clientes API")
				.description("Esta API faz gerenciamento de usuários")
				.contact(new Contact("Jovani Brasil", "http://jovanibrasil.com", "jovanibrasil.com"))
				.version("1.0.0")
				.build();
	}
	
}
