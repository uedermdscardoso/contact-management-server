package br.com.uedercardoso.gestaodecontatos.controller;

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

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import br.com.uedercardoso.gestaodecontatos.enums.contact.OrderType;
import br.com.uedercardoso.gestaodecontatos.models.Contact;
import br.com.uedercardoso.gestaodecontatos.repositories.ContactRepository;

@RestController
public class ContactController {

	private static String HOST = "C:/Users/User/Desktop";
	
	@Autowired
	private ContactRepository contactRepository;
	
	//@CrossOrigin(origins = "http://localhost:4200")
	@PostMapping("/save")
	public ResponseEntity<Contact> save(@Valid @RequestBody Contact contact) {
	
		try {
			String path = HOST+"/fotos/";
			this.criarPasta(path); //Criar pasta "fotos"
				        
			Contact contato = this.criarContato(nome, sobrenome, telefone, null, email, linkTwitter, contatoSkype);
			contato = contactRepository.save(contato);
			
			path += "contato_"+contato.getCodigo()+"/";
			this.criarPasta(path);
			String pathFile = this.absolutePath(foto, path);
			contato.setPathFoto(pathFile);
			
	        this.download(foto, pathFile); //Faz download da foto
	        
	        contactRepository.save(contato); 
	        
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return ResponseEntity.ok(contact);
	}

	@PostMapping("/save")
	public ResponseEntity<Void> savePhoto(@Valid @RequestParam MultipartFile foto) {
		return ResponseEntity.ok().build();
	}
		
	@PutMapping("/{id}/update")
	public Map<String,String> update(@PathVariable("id") Integer id, @RequestBody @Valid Contact contact) {
		
		Map<String,String> map =  new HashMap<String,String>(); 
		
		if(contactRepository.existsById(codContato)) {
			
			String path = HOST+"/fotos/contato_"+codContato+"/";
			String pathFile = null;
			
			Optional<Contato> op = contactRepository.findById(codContato);
			Contato contato = op.get();
			
			try {
				if(contato.getPathFoto() != null) {
					File antigo = new File(contato.getPathFoto()); 
					
					if(antigo.isFile() && antigo.exists()) {
						antigo.delete(); //Excluir a foto antiga
								
						this.criarPasta(path);
						pathFile = this.absolutePath(foto, path);
						this.download(foto, pathFile);
					}
				} else {
					this.criarPasta(path);
					pathFile = this.absolutePath(foto, path);
					this.download(foto, pathFile);
				}
				
				contato = this.criarContato(nome, sobrenome, telefone, pathFile, email, linkTwitter, contatoSkype);
				contato.setCodigo(codContato);
				
				contactRepository.save(contato);
				
				map.put("result", "ok"); 
				
			} catch(Exception e) {
				//e.printStackTrace();
				map.put("message", e.getMessage());
			}
		} else {
			map.put("result", "failed");
		}
		
		return map;
		
	}
	
	@DeleteMapping("/delete/{id}")
	public Map<String, String> delete(@PathVariable("id") Integer id) {
		
		Map<String, String> map = new HashMap<String,String>(); 
		
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
		
		return map;
		
	}
	
	@GetMapping("/list/{order_type}")
	public Map<String,Object> getContacts(@PathVariable("order_type") String orderType) {
		
		Map<String,Object> map = new HashMap<String,Object>();
		List<Contact> listaContato = new LinkedList<Contact>();
		
		try {
			
			switch(orderType) {
				case "ID":{
					listaContato = contactRepository.findContactsOrderById();
					break;
				}
				case "NAME":{
					listaContato = contactRepository.findContactsOrderByName();
					break; 
				} 
				case "SURNAME":{
					listaContato = contactRepository.findContactsOrderBySobrenome();
					break;
				}
				case "PHONE":{
					listaContato = contactRepository.findContactsOrderByTelephone();
					break;
				}
				default:{
					listaContato = contactRepository.findAll();
					break;
				}
			}
			
			map = this.criarMap(listaContato);
					
		} catch(Exception e) {
			map = this.criarMap(null);
		}
		
		return map;
		
	}
	
	
	@GetMapping("/buscar/contato/{codContato}")
	public Map<String,Object> getContact(@PathVariable("codContato") int codContato) {
		
		Map<String, Object> map = new HashMap<String,Object>();
		
		if(contactRepository.existsById(codContato)) {
			
			try {
				Optional<Contato> op = contactRepository.findById(codContato); 
				Contato contato = op.get();
				
				List<Contato> listaContato = new LinkedList<Contato>();
				listaContato.add(contato);
				
				map = this.criarMap(listaContato); 
			} catch(Exception e) {
				//e.printStackTrace();
				map = this.criarMap(null);
			}
			
		} else {
			map = this.criarMap(null);
		}
		
		return map;
	}
	
	@GetMapping("/contatos")
	public Map<String,Object> listarContatos(){
		
		Map<String,Object> map = new HashMap<String,Object>();
		 
		try {
			
			List<Contato> listaContato = contactRepository.findAll();
			
			map = this.criarMap(listaContato);
			
		} catch(Exception e) {
			//e.printStackTrace();
			map = this.criarMap(null);
		}
		
		return map;
		
	}
	
	@GetMapping("/contatos/consultar/{atributo}/{valor}")
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
	}
	
}
