package com.uedercardoso.contact_management.infraestructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.uedercardoso.contact_management.adapters.outbound.persistence.contact.ContactEntityMapper;
import com.uedercardoso.contact_management.core.ports.AttachmentServicePort;
import com.uedercardoso.contact_management.core.ports.ContactRepositoryPort;
import com.uedercardoso.contact_management.core.usecases.CheckAttachmentUseCase;
import com.uedercardoso.contact_management.core.usecases.CreateContactUseCase;
import com.uedercardoso.contact_management.core.usecases.DeleteAttachmentUseCase;
import com.uedercardoso.contact_management.core.usecases.DeleteContactUseCase;
import com.uedercardoso.contact_management.core.usecases.FilterContactUseCase;
import com.uedercardoso.contact_management.core.usecases.GetAllContactUseCase;
import com.uedercardoso.contact_management.core.usecases.GetContactUseCase;
import com.uedercardoso.contact_management.core.usecases.SaveAttachmentUseCase;
import com.uedercardoso.contact_management.core.usecases.SortContactListUseCase;
import com.uedercardoso.contact_management.core.usecases.UpdateContactUserCase;

@Configuration
public class BeanConfig {
	
	@Bean
    public CheckAttachmentUseCase checkAttachmentUseCase(AttachmentServicePort attachmentServicePort) {
        return new CheckAttachmentUseCase(attachmentServicePort);
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
	public DeleteAttachmentUseCase deleteAttachmentUserCase(AttachmentServicePort attachmentServicePort) {
		return new DeleteAttachmentUseCase(attachmentServicePort);
	}

	@Bean
	public FilterContactUseCase filterContactUserCase(ContactRepositoryPort contactRepositoryPort) {
		return new FilterContactUseCase(contactRepositoryPort);
	}

	@Bean
	public GetAllContactUseCase getAllContactUserCase(ContactRepositoryPort contactRepositoryPort) {
		return new GetAllContactUseCase(contactRepositoryPort);
	}

	@Bean
	public GetContactUseCase getContactUserCase(ContactRepositoryPort contactRepositoryPort) {
		return new GetContactUseCase(contactRepositoryPort);
	}

	@Bean
	public SaveAttachmentUseCase saveAttachmentUserCase(AttachmentServicePort attachmentServicePort) {
		return new SaveAttachmentUseCase(attachmentServicePort);
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