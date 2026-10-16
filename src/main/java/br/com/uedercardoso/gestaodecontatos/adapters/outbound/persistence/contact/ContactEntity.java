package br.com.uedercardoso.gestaodecontatos.adapters.outbound.persistence.contact;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import br.com.uedercardoso.gestaodecontatos.core.domain.Contact;
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
	private String photoPath; 
	
	@Column(length=50, nullable=true)
	private String email; 
	
	@Column(length=30, nullable=true)
	private String twitterLink;
	
	@Column(length=30, nullable=true)
	private String skypeContact;

	public ContactEntity(Contact contact) {
		this.name = contact.getName();
		this.surname = contact.getSurname();
		this.phone = contact.getPhone();
		this.photoPath = contact.getPhotoPath();
		this.email = contact.getEmail();
		this.twitterLink = contact.getTwitterLink();
		this.skypeContact = contact.getSkypeContact();
	}
	
	public ContactEntity(Integer id, Contact contact) {
		this.id = id;
		this.name = contact.getName();
		this.surname = contact.getSurname();
		this.phone = contact.getPhone();
		this.photoPath = contact.getPhotoPath();
		this.email = contact.getEmail();
		this.twitterLink = contact.getTwitterLink();
		this.skypeContact = contact.getSkypeContact();
	}
}
