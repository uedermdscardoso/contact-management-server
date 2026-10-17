package com.uedercardoso.contact_management.core.usecases;

import org.springframework.lang.NonNull;

import com.uedercardoso.contact_management.core.domain.Contact;
import com.uedercardoso.contact_management.core.ports.ContactRepositoryPort;

public class CreateContactUseCase {
	private final ContactRepositoryPort contactRepositoryPort;

    public CreateContactUseCase(ContactRepositoryPort contactRepositoryPort) {
        this.contactRepositoryPort = contactRepositoryPort;
    }

	@NonNull
    public Contact execute(String name, String surname, String phone, String attachmentPath, String email,
		String twitterLink, String skypeContact
	) {
        if(!email.contains("@")) {
            throw new IllegalArgumentException("Email is invalid.");
        }

		if(!twitterLink.startsWith("https://www.twitter.com/")) {
			throw new IllegalArgumentException("Twitter link is invalid.");
		}

		if(!skypeContact.startsWith("skype:")) {
			throw new IllegalArgumentException("Skype contact is invalid.");
		}
        
		Contact contact = new Contact(name, surname, phone, attachmentPath, email, twitterLink, skypeContact);
		
		return this.contactRepositoryPort.save(contact);
    }
}