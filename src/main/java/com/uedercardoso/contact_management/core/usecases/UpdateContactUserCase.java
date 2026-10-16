package com.uedercardoso.contact_management.core.usecases;

import org.springframework.lang.NonNull;

import com.uedercardoso.contact_management.core.domain.Contact;
import com.uedercardoso.contact_management.core.ports.ContactRepositoryPort;

public class UpdateContactUserCase {
	private final ContactRepositoryPort contactRepositoryPort;

    public UpdateContactUserCase(ContactRepositoryPort contactRepositoryPort) {
        this.contactRepositoryPort = contactRepositoryPort;
    }

	@NonNull
    public Contact execute(Integer id,String name, String surname, String phone, String photoPath, String email,
		String twitterLink, String skypeContact
	) {
        if(!email.contains("@")) {
            throw new IllegalArgumentException("Email is invalid.");
        }

		if(!twitterLink.startsWith("https://twitter.com/")) {
			throw new IllegalArgumentException("Twitter link is invalid.");
		}

		if(!skypeContact.startsWith("skype:")) {
			throw new IllegalArgumentException("Skype contact is invalid.");
		}
        
		Contact contact = new Contact(name, surname, phone, photoPath, email, twitterLink, skypeContact);
		
		return this.contactRepositoryPort.update(id, contact);
    }
}