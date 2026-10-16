package br.com.uedercardoso.gestaodecontatos.adapters.inbound.web.controller;

import java.util.LinkedList;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import br.com.uedercardoso.gestaodecontatos.core.domain.Contact;
import br.com.uedercardoso.gestaodecontatos.core.usecases.CheckPhotoUserCase;
import br.com.uedercardoso.gestaodecontatos.core.usecases.CreateContactUseCase;
import br.com.uedercardoso.gestaodecontatos.core.usecases.DeleteContactUseCase;
import br.com.uedercardoso.gestaodecontatos.core.usecases.DeletePhotoUserCase;
import br.com.uedercardoso.gestaodecontatos.core.usecases.FilterContactUserCase;
import br.com.uedercardoso.gestaodecontatos.core.usecases.GetAllContactUserCase;
import br.com.uedercardoso.gestaodecontatos.core.usecases.GetContactUserCase;
import br.com.uedercardoso.gestaodecontatos.core.usecases.SavePhotoUserCase;
import br.com.uedercardoso.gestaodecontatos.core.usecases.SortContactListUseCase;
import br.com.uedercardoso.gestaodecontatos.core.usecases.UpdateContactUserCase;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/contact")
@CrossOrigin(origins = "http://localhost:3000") 
public class ContactController {

	private final GetContactUserCase getContactUserCase;
	private final GetAllContactUserCase getAllContactUserCase;
	private final SortContactListUseCase sortContactListUseCase;
	private final FilterContactUserCase filterContactUserCase;
	private final CreateContactUseCase createContactUseCase;
	private final UpdateContactUserCase updateContactUserCase;
	private final DeleteContactUseCase deleteContactUseCase;
	private final SavePhotoUserCase savePhotoUserCase;
	private final CheckPhotoUserCase checkPhotoUserCase;
	private final DeletePhotoUserCase deletePhotoUserCase;

	public ContactController(CreateContactUseCase createContactUseCase, GetContactUserCase getContactUserCase, GetAllContactUserCase getAllContactUserCase,
		SortContactListUseCase sortContactListUseCase, FilterContactUserCase filterContactUserCase, UpdateContactUserCase updateContactUserCase, SavePhotoUserCase savePhotoUserCase, 
		CheckPhotoUserCase checkPhotoUserCase, DeletePhotoUserCase deletePhotoUserCase, DeleteContactUseCase deleteContactUseCase) {
		this.createContactUseCase = createContactUseCase;
		this.getContactUserCase = getContactUserCase;
		this.getAllContactUserCase = getAllContactUserCase;
		this.sortContactListUseCase = sortContactListUseCase;
		this.filterContactUserCase = filterContactUserCase;
		this.updateContactUserCase = updateContactUserCase;
		this.deleteContactUseCase = deleteContactUseCase;
		this.savePhotoUserCase = savePhotoUserCase;
		this.checkPhotoUserCase = checkPhotoUserCase;
		this.deletePhotoUserCase = deletePhotoUserCase;
	}

	@PostMapping("/save")
	public ResponseEntity<Contact> save(@Valid @RequestBody ContactRequestDTO contactDTO) {	
		try {
				        
			final Contact contact = this.createContactUseCase.execute(contactDTO.name(), contactDTO.surname(), contactDTO.phone(), contactDTO.photoPath(), contactDTO.email(), contactDTO.twitterLink(), contactDTO.skypeContact());

			return ResponseEntity.ok(contact);
			
		} catch(Exception e) {
			log.error("Save failed", e);
			return ResponseEntity.status(500).build();
		}
	}

	@PostMapping("/upload/photo")
	public ResponseEntity<String> savePhoto(@Valid @RequestParam MultipartFile file, @RequestParam String contentType, @RequestParam String fileName) {

		final String photoUrl = this.savePhotoUserCase.execute(file, contentType, fileName);

		return ResponseEntity.ok(photoUrl);
	}

	@DeleteMapping("/delete/photo")
	public ResponseEntity<Void> deletePhoto(@RequestParam String fileName) {

		this.deletePhotoUserCase.execute(fileName);

		return ResponseEntity.ok().build();
	}
		
	@PutMapping("/{id}/update")
	public ResponseEntity<Contact> update(@PathVariable("id") Integer id, @RequestBody @Valid Contact contact) {
		
		final Contact contactEntity = this.updateContactUserCase.execute(id, contact.getName(), contact.getSurname(), contact.getPhone(), contact.getPhotoPath(), contact.getEmail(), contact.getTwitterLink(), contact.getSkypeContact());
		
		return ResponseEntity.ok(contactEntity);		
	}
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<Void> delete(@PathVariable("id") Integer id) {
		
		final Contact contact = this.getContactUserCase.execute(id);

		if(contact == null) {
			return ResponseEntity.notFound().build();
		}

		final String filename = this.getFilename(contact.getPhotoPath());

		final Boolean hasFile = this.checkPhotoUserCase.execute(filename);
		if(hasFile) {
			this.deletePhotoUserCase.execute(filename);
		}

		this.deleteContactUseCase.execute(id);

		return ResponseEntity.ok().build();
	}
	
	@GetMapping("/all/{order_type}")
	public ResponseEntity<List<Contact>> getContacts(@PathVariable("order_type") String orderType) {
		
		List<Contact> contacts = new LinkedList<Contact>();
			
		switch(orderType) {
			case "ID":{
				contacts = this.sortContactListUseCase.executeSortById();
				break;
			}
			case "NAME":{
				contacts = this.sortContactListUseCase.executeSortByName();
				break; 
			} 
			case "SURNAME":{
				contacts = this.sortContactListUseCase.executeSortBySurname();
				break;
			}
			case "PHONE":{
				contacts = this.sortContactListUseCase.executeSortByPhone();
				break;
			}
			default:{
				contacts = this.getAllContactUserCase.execute();
				break;
			}
		}
		
		return ResponseEntity.ok(contacts);
	}
	
	
	@GetMapping("/{id}")
	public ResponseEntity<Contact> getContact(@PathVariable("id") Integer id) {
		
		final Contact contact = this.getContactUserCase.execute(id);

		return ResponseEntity.ok(contact);
	}
	
	@GetMapping("/search/{attribute}/{value}")
	public ResponseEntity<List<Contact>> findContactsByValue(@RequestBody @Valid AttributeRequestDTO attributes){
		
		List<Contact> contacts = new LinkedList<Contact>();
		
		try {
			switch(attributes.attribute) {
				case "NAME":{
					contacts = this.filterContactUserCase.executeFindByName(attributes.value);
					break;
				}
				case "SURNAME":{
					contacts = this.filterContactUserCase.executeFindBySurname(attributes.value);
					break;
				}
				case "PHONE":{
					contacts = this.filterContactUserCase.executeFindByPhone(attributes.value);
					break;
				}
				default:
					break;
			}

			return ResponseEntity.ok(contacts);
					
		} catch(Exception e) {
			log.error("Unable to filter contacts", e);

			return ResponseEntity.status(500).build();
		}
	}
	
	public String getFilename(String path) {
		int aux = path.lastIndexOf('.'); 
		String fileName = path.substring(aux+1, path.length());
		
		return fileName;
	}
	
	public record ContactRequestDTO(
		@NotEmpty(message = "The name cannot be empty")
		String name, 
		@NotEmpty(message = "The surname cannot be empty")
		@Size(min = 5, max = 50, message = "The surname must be between 5 and 50 characters")
		String surname,
		@NotEmpty(message = "The email cannot be empty")
		@Size(max = 50, message = "The email must have a maximum of 50 characters")
		String email,
		@NotEmpty(message = "The phone cannot be empty")
		String phone,
		String photoPath,
		@NotEmpty(message = "The twitter link cannot be empty")
		@Size(max = 30, message = "The twitter link must have a maximum of 30 characters")
		String twitterLink,
		@NotEmpty(message = "The skype contact cannot be empty")
		@Size(max = 30, message = "The skype contact must have a maximum of 30 characters")
		String skypeContact
	) {}

	public record AttributeRequestDTO(
		@NotEmpty(message = "The attribute cannot be empty")
		String attribute, 
		@NotEmpty(message = "The value cannot be empty")
		String value
	) {}

}
