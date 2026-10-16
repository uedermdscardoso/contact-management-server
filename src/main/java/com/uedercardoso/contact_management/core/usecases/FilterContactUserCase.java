package com.uedercardoso.contact_management.core.usecases;

import java.util.List;

import com.uedercardoso.contact_management.core.domain.Contact;
import com.uedercardoso.contact_management.core.ports.ContactRepositoryPort;

public class FilterContactUserCase {
	private final ContactRepositoryPort contactRepositoryPort;

    public FilterContactUserCase(ContactRepositoryPort contactRepositoryPort) {
        this.contactRepositoryPort = contactRepositoryPort;
    }

    public List<Contact> executeFindByName(String name) {
		return this.contactRepositoryPort.findByName(name);
    }

	public List<Contact> executeFindBySurname(String surname) {
		return this.contactRepositoryPort.findBySurname(surname);
    }

	public List<Contact> executeFindByPhone(String phone) {
		return this.contactRepositoryPort.findByPhone(phone);
    }
}