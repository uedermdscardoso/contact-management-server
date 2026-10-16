package com.uedercardoso.contact_management.core.usecases;

import java.util.List;

import com.uedercardoso.contact_management.core.domain.Contact;
import com.uedercardoso.contact_management.core.ports.ContactRepositoryPort;

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