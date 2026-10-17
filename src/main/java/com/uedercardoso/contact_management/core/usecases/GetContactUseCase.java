package com.uedercardoso.contact_management.core.usecases;

import com.uedercardoso.contact_management.core.domain.Contact;
import com.uedercardoso.contact_management.core.ports.ContactRepositoryPort;

public class GetContactUseCase {
	private final ContactRepositoryPort contactRepositoryPort;

    public GetContactUseCase(ContactRepositoryPort contactRepositoryPort) {
        this.contactRepositoryPort = contactRepositoryPort;
    }

    public Contact execute(Integer id) {

		if(id == null || id <= 0) {
			throw new IllegalArgumentException("Id is invalid.");
		}

		return this.contactRepositoryPort.findById(id);
    }
}