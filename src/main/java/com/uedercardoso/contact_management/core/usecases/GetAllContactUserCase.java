package com.uedercardoso.contact_management.core.usecases;

import java.util.List;

import com.uedercardoso.contact_management.core.domain.Contact;
import com.uedercardoso.contact_management.core.ports.ContactRepositoryPort;

public class GetAllContactUserCase {
	private final ContactRepositoryPort contactRepositoryPort;

    public GetAllContactUserCase(ContactRepositoryPort contactRepositoryPort) {
        this.contactRepositoryPort = contactRepositoryPort;
    }

    public List<Contact> execute() {
		return this.contactRepositoryPort.findAll();
    }
}