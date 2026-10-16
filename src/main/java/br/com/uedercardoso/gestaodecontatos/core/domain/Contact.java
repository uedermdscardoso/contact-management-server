package br.com.uedercardoso.gestaodecontatos.core.domain;

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
	private String photoPath; 
	private String email; 
	private String twitterLink;
	private String skypeContact;
	
	public Contact(int id, String name, String email) {
		this.id = id;
		this.name = name;
		this.email = email;
	}

	public Contact(String name, String surname, String phone, String photoPath, String email, String twitterLink, String skypeContact) {
        if (name == null || name.length() < 3 || name.length() > 15) {
            throw new IllegalArgumentException("The name must be between 3 and 15 characters.");
        }
        this.name = name;
        this.surname = surname;
        this.phone = phone;
        this.photoPath = photoPath;
        this.email = email;
        this.twitterLink = twitterLink;
        this.skypeContact = skypeContact;
    }
}
