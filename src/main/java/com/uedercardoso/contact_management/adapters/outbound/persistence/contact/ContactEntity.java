package com.uedercardoso.contact_management.adapters.outbound.persistence.contact;


import com.uedercardoso.contact_management.core.domain.Contact;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="contacts")
@NoArgsConstructor
@AllArgsConstructor
public class ContactEntity {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id; 
	
	@Column(length=15,nullable=false)
	private String name;
	
	@Column(length=50,nullable=false)
	private String surname;
	
	@Column(length=25,nullable=false)
	private String phone;
	
	@Column(length=255,nullable=true)
	private String attachmentPath; 
	
	@Column(length=50, nullable=false)
	private String email; 
	
	@Column(name="twitter_link",length=50, nullable=true)
	private String twitterLink;
	
	@Column(name="skype_contact", length=30, nullable=true)
	private String skypeContact;

	public ContactEntity(Contact contact) {
		this.name = contact.getName();
		this.surname = contact.getSurname();
		this.phone = contact.getPhone();
		this.attachmentPath = contact.getAttachmentPath();
		this.email = contact.getEmail();
		this.twitterLink = contact.getTwitterLink();
		this.skypeContact = contact.getSkypeContact();
	}
	
	public ContactEntity(Integer id, Contact contact) {
		this.id = id;
		this.name = contact.getName();
		this.surname = contact.getSurname();
		this.phone = contact.getPhone();
		this.attachmentPath = contact.getAttachmentPath();
		this.email = contact.getEmail();
		this.twitterLink = contact.getTwitterLink();
		this.skypeContact = contact.getSkypeContact();
	}
}
