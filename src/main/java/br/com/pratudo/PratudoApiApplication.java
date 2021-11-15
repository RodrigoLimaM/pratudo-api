package br.com.pratudo;

import br.com.pratudo.config.properties.ApplicationProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

@SpringBootApplication
@EnableFeignClients
@EnableElasticsearchRepositories
@EnableConfigurationProperties(ApplicationProperties.class)
public class PratudoApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(PratudoApiApplication.class, args);
	}

}
