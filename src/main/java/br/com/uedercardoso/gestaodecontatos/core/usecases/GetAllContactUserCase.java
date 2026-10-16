package br.com.uedercardoso.gestaodecontatos.core.usecases;

import java.util.List;

import br.com.uedercardoso.gestaodecontatos.core.domain.Contact;
import br.com.uedercardoso.gestaodecontatos.core.ports.ContactRepositoryPort;

public class GetAllContactUserCase {
	private final ContactRepositoryPort contactRepositoryPort;

    public GetAllContactUserCase(ContactRepositoryPort contactRepositoryPort) {
        this.contactRepositoryPort = contactRepositoryPort;
    }

    public List<Contact> execute() {
		return this.contactRepositoryPort.findAll();
    }
}