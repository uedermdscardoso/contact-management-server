package br.com.uedercardoso.gestaodecontatos.core.usecases;

import br.com.uedercardoso.gestaodecontatos.core.domain.Contact;
import br.com.uedercardoso.gestaodecontatos.core.ports.ContactRepositoryPort;

public class GetContactUserCase {
	private final ContactRepositoryPort contactRepositoryPort;

    public GetContactUserCase(ContactRepositoryPort contactRepositoryPort) {
        this.contactRepositoryPort = contactRepositoryPort;
    }

    public Contact execute(Integer id) {

		if(id == null || id <= 0) {
			throw new IllegalArgumentException("Id is invalid.");
		}

		return this.contactRepositoryPort.findById(id);
    }
}