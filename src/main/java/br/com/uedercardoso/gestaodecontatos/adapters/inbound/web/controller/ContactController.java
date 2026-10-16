package br.com.uedercardoso.gestaodecontatos.adapters.inbound.web.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Logger;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
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

import br.com.uedercardoso.gestaodecontatos.adapters.inbound.web.controller.enums.contact.OrderType;
import br.com.uedercardoso.gestaodecontatos.core.domain.Contact;
import br.com.uedercardoso.gestaodecontatos.core.usecases.CreateContactUseCase;
import br.com.uedercardoso.gestaodecontatos.core.usecases.DeletePhotoUserCase;
import br.com.uedercardoso.gestaodecontatos.core.usecases.GetContactUserCase;
import br.com.uedercardoso.gestaodecontatos.core.usecases.SavePhotoUserCase;
import br.com.uedercardoso.gestaodecontatos.core.usecases.UpdateContactUserCase;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/contact")
@CrossOrigin(origins = "http://localhost:3000") 
public class ContactController {

	private final GetContactUserCase getContactUserCase;
	private final CreateContactUseCase createContactUseCase;
	private final UpdateContactUserCase updateContactUserCase;
	private final SavePhotoUserCase savePhotoUserCase;
	private final DeletePhotoUserCase deletePhotoUserCase;

	public ContactController(CreateContactUseCase createContactUseCase, GetContactUserCase getContactUserCase, 
		UpdateContactUserCase updateContactUserCase, SavePhotoUserCase savePhotoUserCase, DeletePhotoUserCase deletePhotoUserCase) {
		this.createContactUseCase = createContactUseCase;
		this.getContactUserCase = getContactUserCase;
		this.updateContactUserCase = updateContactUserCase;
		this.savePhotoUserCase = savePhotoUserCase;
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

		final Optional<String> data = this.savePhotoUserCase.execute(file, contentType, fileName);

		if(data.isPresent()) {
			final String photoUrl = data.get();
		
			return ResponseEntity.ok(photoUrl);

		}

		return ResponseEntity.noContent().build();
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
		

		/*Map<String, String> map = new HashMap<String,String>(); 
		
		if(contactRepository.existsById(codContato)) {
			
			Optional<Contato> op = contactRepository.findById(codContato); 
			Contato contato = op.get(); 
			
			try {
				
				//excluir foto
				
				if(contato.getPathFoto() != null) {
					File filePhoto = new File(contato.getPathFoto()); 
					if(filePhoto.isFile() && filePhoto.exists()) {
						filePhoto.delete(); //Deletando a foto
						
						File path = new File(HOST+"/fotos/contato_"+codContato+"/");
						path.delete(); //Deletando a pasta do contato específico
					}
				}
				
				contactRepository.deleteById(codContato);	
				
				map.put("result", "deleted");
				
			} catch(Exception e) {
				//e.printStackTrace();
				map.put("message", e.getMessage());
				map.put("result", "failed"); 
			}
			
		}  else {
			map.put("result", "failed"); 
		}
		
		return map;*/

		return ResponseEntity.ok().build();
	}
	
	/*@GetMapping("/list/{order_type}")
	public ResponseEntity<List<Contact>> getContacts(@PathVariable("order_type") String orderType) {
		
		List<Contact> contacts = new LinkedList<Contact>();
		
		try {
			
			switch(orderType) {
				case "ID":{
					contacts = contactRepository.findContactById();
					break;
				}
				case "NAME":{
					contacts = contactRepository.findContactByName();
					break; 
				} 
				case "SURNAME":{
					contacts = contactRepository.findContactBySurname();
					break;
				}
				case "PHONE":{
					contacts = contactRepository.findContactByPhone();
					break;
				}
				default:{
					contacts = contactRepository.findAll();
					break;
				}
			}
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return ResponseEntity.ok(contacts);
	}*/
	
	
	@GetMapping("/{id}")
	public ResponseEntity<Contact> getContact(@PathVariable("id") Integer id) {
		
		final Contact contact = this.getContactUserCase.execute(id);

		return ResponseEntity.ok(contact);
	}
	
	/*@GetMapping("/contatos/consultar/{atributo}/{valor}")
	public Map<String,Object> listarContatosPorValor(@PathVariable("atributo") String atributo, @PathVariable("valor") String valor){
		
		List<Contato> listaContato = new LinkedList<Contato>();
		
		Map<String,Object> map = new HashMap<String,Object>(); 
		
		try {
			switch(atributo) {
				case "nome":{
					listaContato = contactRepository.findContactByName(valor);
					break;
				}
				case "sobrenome":{
					listaContato = contactRepository.findContactBySobrenome(valor);
					break;
				}
				case "telefone":{
					listaContato = contactRepository.findContactByTelephone(Long.parseLong(valor));
					break;
				}
				default:{
					listaContato = null;
					break;
				}
			}
			
			map = this.criarMap(listaContato);
			
		} catch(Exception e) {
			//e.printStackTrace();
			map = this.criarMap(null);
		}
				
		return map;
	}
	
	
	public Contato criarContato(String nome, String sobrenome, long telefone, String pathFoto, String email,
			String linkTwitter, String contatoSkype){
		Contato contato = new Contato(); 
		contato.setNome(nome);
		contato.setSobrenome(sobrenome);
		contato.setTelefone(telefone);
		contato.setPathFoto(pathFoto);
		contato.setEmail(email);
		contato.setLinkTwitter(linkTwitter);
		contato.setContatoSkype(contatoSkype);
		
		return contato;
	}
	
	public Map<String,Object> criarMap(List<Contato> listaContato){
		Map<String,Object> map = new HashMap<String,Object>();
		if(listaContato != null) {
			map.put("totalItems", listaContato.size()); 
		} else {
			map.put("totalItems", 0);
		}
		map.put("items", listaContato); 
		return map;
	}
	
	public void criarPasta(String path) {
        File diretorio = new File(path);
        
        if(!diretorio.exists()) {
        	diretorio.mkdir();
        } 
	}
	
	public String absolutePath(MultipartFile arquivo, String path) {
		String extensao = arquivo.getOriginalFilename();
		int aux = extensao.lastIndexOf('.'); 
		extensao = extensao.substring(aux+1, extensao.length());
		
		return path+"foto."+extensao;
	}
	
	public void download(MultipartFile arquivo, String pathFile) throws IOException {
        byte[] bytes = arquivo.getBytes();
        Path path2 = Paths.get(pathFile);
        Files.write(path2, bytes);
	}*/

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
	
	public record ContactResponseDTO(Long id, String name, String email) {}

}
