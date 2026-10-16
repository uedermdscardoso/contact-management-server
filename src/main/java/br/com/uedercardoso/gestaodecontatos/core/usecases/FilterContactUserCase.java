package br.com.uedercardoso.gestaodecontatos.core.usecases;

import java.util.List;

import br.com.uedercardoso.gestaodecontatos.core.domain.Contact;
import br.com.uedercardoso.gestaodecontatos.core.ports.ContactRepositoryPort;

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