package br.com.uedercardoso.gestaodecontatos.core.usecases;

import java.util.List;

import br.com.uedercardoso.gestaodecontatos.core.domain.Contact;
import br.com.uedercardoso.gestaodecontatos.core.ports.ContactRepositoryPort;

public class SortContactListUseCase {
	private final ContactRepositoryPort contactRepositoryPort;

    public SortContactListUseCase(ContactRepositoryPort contactRepositoryPort) {
        this.contactRepositoryPort = contactRepositoryPort;
    }

    public List<Contact> executeSortById() {
		return this.contactRepositoryPort.findContactById();
    }
	
	public List<Contact> executeSortByName() {
		return this.contactRepositoryPort.findContactByName();
    }
	
	public List<Contact> executeSortBySurname() {
		return this.contactRepositoryPort.findContactBySurname();
    }
	
	public List<Contact> executeSortByPhone() {
		return this.contactRepositoryPort.findContactByPhone();
    }
}