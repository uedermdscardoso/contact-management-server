package com.uedercardoso.contact_management.core.domain;

import com.uedercardoso.contact_management.adapters.outbound.persistence.contact.ContactEntity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Contact {

	private Integer id; 
	private String name;
	private String surname;
	private String phone;
	private String attachmentPath; 
	private String email; 
	private String twitterLink;
	private String skypeContact;
	
	public Contact(int id, String name, String email) {
		this.id = id;
		this.name = name;
		this.email = email;
	}

	public Contact(String name, String surname, String phone, String attachmentPath, String email, String twitterLink, String skypeContact) {
        if (name == null || name.length() < 3 || name.length() > 15) {
            throw new IllegalArgumentException("The name must be between 3 and 15 characters.");
        }
        this.name = name;
        this.surname = surname;
        this.phone = phone;
        this.attachmentPath = attachmentPath;
        this.email = email;
        this.twitterLink = twitterLink;
        this.skypeContact = skypeContact;
    }

	public Contact(ContactEntity contactEntity) {
		this.id = contactEntity.getId();
		this.name = contactEntity.getName();
		this.surname = contactEntity.getSurname();
		this.phone = contactEntity.getPhone();
		this.attachmentPath = contactEntity.getAttachmentPath();
		this.email = contactEntity.getEmail();
		this.twitterLink = contactEntity.getTwitterLink();
		this.skypeContact = contactEntity.getSkypeContact();
	}
}
