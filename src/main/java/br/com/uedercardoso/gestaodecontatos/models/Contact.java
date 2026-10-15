package br.com.uedercardoso.gestaodecontatos.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Contact {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id; 
	
	@NotEmpty
	@Size(min=3, max=15)
	@Column(length=15,nullable=false)
	private String name;
	
	@NotEmpty
	@Size(min=5, max=50)
	@Column(length=50,nullable=false)
	private String surname;
	
	@NotEmpty
	@Column(length=25,nullable=false)
	private String phone;
	
	@Column(length=255,nullable=true)
	private String photoPath; 
	
	@Size(max=50)
	@Column(length=50, nullable=true)
	private String email; 
	
	@Size(max=30)
	@Column(length=30, nullable=true)
	private String twitterLink;
	
	@Size(max=30)
	@Column(length=30, nullable=true)
	private String skypeContact;
	
}
