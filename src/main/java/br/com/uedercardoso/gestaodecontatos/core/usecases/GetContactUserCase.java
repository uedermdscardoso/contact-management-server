package br.com.uedercardoso.gestaodecontatos.core.usecases;

import org.springframework.lang.NonNull;

import br.com.uedercardoso.gestaodecontatos.core.domain.Contact;
import br.com.uedercardoso.gestaodecontatos.core.ports.ContactRepositoryPort;

public class GetContactUserCase {
	private final ContactRepositoryPort contactRepositoryPort;

    public GetContactUserCase(ContactRepositoryPort contactRepositoryPort) {
        this.contactRepositoryPort = contactRepositoryPort;
    }

	@NonNull
    public Contact execute(Integer id) {

		if(id == null || id <= 0) {
			throw new IllegalArgumentException("Id is invalid.");
		}

		return contactRepositoryPort.findById(id);
    }
}