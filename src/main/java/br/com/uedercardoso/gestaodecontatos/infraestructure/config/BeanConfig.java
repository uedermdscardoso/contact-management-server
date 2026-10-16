package br.com.uedercardoso.gestaodecontatos.infraestructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import br.com.uedercardoso.gestaodecontatos.core.ports.ContactRepositoryPort;
import br.com.uedercardoso.gestaodecontatos.core.usecases.CreateContactUseCase;

@Configuration
public class BeanConfig {

    @Bean
    public CreateContactUseCase createContactUseCase(ContactRepositoryPort contactRepositoryPort) {
        return new CreateContactUseCase(contactRepositoryPort);
    }
}