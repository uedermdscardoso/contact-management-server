package com.uedercardoso.contact_management.infraestructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.uedercardoso.contact_management.adapters.outbound.persistence.contact.ContactEntityMapper;
import com.uedercardoso.contact_management.core.ports.ContactRepositoryPort;
import com.uedercardoso.contact_management.core.ports.PhotoServicePort;
import com.uedercardoso.contact_management.core.usecases.CheckPhotoUserCase;
import com.uedercardoso.contact_management.core.usecases.CreateContactUseCase;
import com.uedercardoso.contact_management.core.usecases.DeleteContactUseCase;
import com.uedercardoso.contact_management.core.usecases.DeletePhotoUserCase;
import com.uedercardoso.contact_management.core.usecases.FilterContactUserCase;
import com.uedercardoso.contact_management.core.usecases.GetAllContactUserCase;
import com.uedercardoso.contact_management.core.usecases.GetContactUserCase;
import com.uedercardoso.contact_management.core.usecases.SavePhotoUserCase;
import com.uedercardoso.contact_management.core.usecases.SortContactListUseCase;
import com.uedercardoso.contact_management.core.usecases.UpdateContactUserCase;

@Configuration
public class BeanConfig {
	
	@Bean
    public CheckPhotoUserCase checkPhotoUseCase(PhotoServicePort photoServicePort) {
        return new CheckPhotoUserCase(photoServicePort);
    }

    @Bean
    public CreateContactUseCase createContactUseCase(ContactRepositoryPort contactRepositoryPort) {
        return new CreateContactUseCase(contactRepositoryPort);
    }

	@Bean
	public DeleteContactUseCase deleteContactUseCase(ContactRepositoryPort contactRepositoryPort) {
		return new DeleteContactUseCase(contactRepositoryPort);
	}

	@Bean
	public DeletePhotoUserCase deletePhotoUserCase(PhotoServicePort photoServicePort) {
		return new DeletePhotoUserCase(photoServicePort);
	}

	@Bean
	public FilterContactUserCase filterContactUserCase(ContactRepositoryPort contactRepositoryPort) {
		return new FilterContactUserCase(contactRepositoryPort);
	}

	@Bean
	public GetAllContactUserCase getAllContactUserCase(ContactRepositoryPort contactRepositoryPort) {
		return new GetAllContactUserCase(contactRepositoryPort);
	}

	@Bean
	public GetContactUserCase getContactUserCase(ContactRepositoryPort contactRepositoryPort) {
		return new GetContactUserCase(contactRepositoryPort);
	}

	@Bean
	public SavePhotoUserCase savePhotoUserCase(PhotoServicePort photoServicePort) {
		return new SavePhotoUserCase(photoServicePort);
	}

	@Bean
	public SortContactListUseCase sortContactListUseCase(ContactRepositoryPort contactRepositoryPort) {
		return new SortContactListUseCase(contactRepositoryPort);
	}

	@Bean
	public UpdateContactUserCase updateContactUserCase(ContactRepositoryPort contactRepositoryPort) {
		return new UpdateContactUserCase(contactRepositoryPort);
	}
	
}