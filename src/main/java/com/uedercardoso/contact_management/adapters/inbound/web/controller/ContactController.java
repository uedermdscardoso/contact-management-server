package com.uedercardoso.contact_management.adapters.inbound.web.controller;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import org.springframework.http.MediaType;
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
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.uedercardoso.contact_management.core.domain.Contact;
import com.uedercardoso.contact_management.core.usecases.CheckAttachmentUseCase;
import com.uedercardoso.contact_management.core.usecases.CreateContactUseCase;
import com.uedercardoso.contact_management.core.usecases.DeleteAttachmentUseCase;
import com.uedercardoso.contact_management.core.usecases.DeleteContactUseCase;
import com.uedercardoso.contact_management.core.usecases.GetAllContactUseCase;
import com.uedercardoso.contact_management.core.usecases.GetContactUseCase;
import com.uedercardoso.contact_management.core.usecases.SaveAttachmentUseCase;
import com.uedercardoso.contact_management.core.usecases.SortContactListUseCase;
import com.uedercardoso.contact_management.core.usecases.UpdateContactUserCase;
import com.uedercardoso.contact_management.core.usecases.FilterContactUseCase;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/contact")
@CrossOrigin(origins = "http://localhost:4200") 
public class ContactController {

	private final GetContactUseCase getContactUseCase;
	private final GetAllContactUseCase getAllContactUseCase;
	private final SortContactListUseCase sortContactListUseCase;
	private final FilterContactUseCase filterContactUseCase;
	private final CreateContactUseCase createContactUseCase;
	private final UpdateContactUserCase updateContactUseCase;
	private final DeleteContactUseCase deleteContactUseCase;
	private final SaveAttachmentUseCase saveAttachmentUseCase;
	private final CheckAttachmentUseCase checkAttachmentUseCase;
	private final DeleteAttachmentUseCase deleteAttachmentUseCase;

	public ContactController(CreateContactUseCase createContactUseCase, GetContactUseCase getContactUseCase, GetAllContactUseCase getAllContactUseCase,
		SortContactListUseCase sortContactListUseCase, FilterContactUseCase filterContactUseCase, UpdateContactUserCase updateContactUseCase, 
		SaveAttachmentUseCase saveAttachmentUseCase, CheckAttachmentUseCase checkAttachmentUseCase, DeleteAttachmentUseCase deleteAttachmentUseCase, 
		DeleteContactUseCase deleteContactUseCase) {
		this.createContactUseCase = createContactUseCase;
		this.getContactUseCase = getContactUseCase;
		this.getAllContactUseCase = getAllContactUseCase;
		this.sortContactListUseCase = sortContactListUseCase;
		this.filterContactUseCase = filterContactUseCase;
		this.updateContactUseCase = updateContactUseCase;
		this.deleteContactUseCase = deleteContactUseCase;
		this.saveAttachmentUseCase = saveAttachmentUseCase;
		this.checkAttachmentUseCase = checkAttachmentUseCase;
		this.deleteAttachmentUseCase = deleteAttachmentUseCase;
	}

	@PostMapping(value = "/save", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<Contact> save(
		@RequestPart("contact") @Valid ContactRequestDTO contactDTO,
		@RequestPart("file") MultipartFile file) {	
		try {

			final Optional<String> data = this.saveAttachmentUseCase.execute(file);
			
			String attachmentPath = null;
			if(data.isPresent()) {
				attachmentPath = data.get();
			}
			
			final Contact contact = this.createContactUseCase.execute(contactDTO.name(), contactDTO.surname(), contactDTO.phone(), attachmentPath, contactDTO.email(), contactDTO.twitterLink(), contactDTO.skypeContact());

			return ResponseEntity.ok(contact);
			
		} catch(Exception e) {
			log.error("Save failed", e);
			return ResponseEntity.status(500).build();
		}
	}

	@PostMapping("/upload/attachment")
	public ResponseEntity<String> saveAttachment(@Valid @RequestParam MultipartFile file) {
		
		final Optional<String> data = this.saveAttachmentUseCase.execute(file);
		
		if(data.isPresent()) {
			final String attachmentPath = data.get();
		
			return ResponseEntity.ok(attachmentPath);
		}
		
		return ResponseEntity.noContent().build();
	}

	@DeleteMapping("/delete/attachment")
	public ResponseEntity<Void> deleteAttachment(@RequestParam String fileName) {

		this.deleteAttachmentUseCase.execute(fileName);

		return ResponseEntity.ok().build();
	}
		
	@PutMapping("/{id}/update")
	public ResponseEntity<Contact> update(@PathVariable("id") Integer id, @RequestBody @Valid Contact contact) {
		
		final Contact contactEntity = this.updateContactUseCase.execute(id, contact.getName(), contact.getSurname(), contact.getPhone(), contact.getAttachmentPath(), contact.getEmail(), contact.getTwitterLink(), contact.getSkypeContact());
		
		return ResponseEntity.ok(contactEntity);		
	}
	
	@DeleteMapping("/{id}/delete")
	public ResponseEntity<Void> delete(@PathVariable("id") Integer id) {
		
		final Contact contact = this.getContactUseCase.execute(id);

		if(contact == null) {
			return ResponseEntity.notFound().build();
		}

		if(contact.getAttachmentPath() != null && !contact.getAttachmentPath().isEmpty()) {
			final String filename = this.getFilename(contact.getAttachmentPath());

			final Boolean hasFile = this.checkAttachmentUseCase.execute(filename);
			if(hasFile) {
				this.deleteAttachmentUseCase.execute(filename);
			}	
		}
		
		this.deleteContactUseCase.execute(id);

		return ResponseEntity.ok().build();
	}
	
	@GetMapping("/all/{order_type}")
	public ResponseEntity<List<Contact>> getContacts(@PathVariable("order_type") String orderType) {
		
		List<Contact> contacts = new LinkedList<Contact>();
			
		switch(orderType.toUpperCase()) {
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
				contacts = this.getAllContactUseCase.execute();
				break;
			}
		}
		
		return ResponseEntity.ok(contacts);
	}
	
	
	@GetMapping("/{id}")
	public ResponseEntity<Contact> getContact(@PathVariable("id") Integer id) {
		
		final Contact contact = this.getContactUseCase.execute(id);

		return ResponseEntity.ok(contact);
	}
	
	@GetMapping("/search")
	public ResponseEntity<List<Contact>> findContactsByValue(@RequestBody @Valid AttributeRequestDTO attributes){
		
		List<Contact> contacts = new LinkedList<Contact>();
		
		try {
			switch(attributes.attribute.toUpperCase()) {
				case "NAME":{
					contacts = this.filterContactUseCase.executeFindByName(attributes.value);
					break;
				}
				case "SURNAME":{
					contacts = this.filterContactUseCase.executeFindBySurname(attributes.value);
					break;
				}
				case "PHONE":{
					contacts = this.filterContactUseCase.executeFindByPhone(attributes.value);
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
		int aux = path.lastIndexOf('/'); 
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
		String attachmentPath,
		@NotEmpty(message = "The twitter link cannot be empty")
		@Size(max = 50, message = "The twitter link must have a maximum of 50 characters")
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
