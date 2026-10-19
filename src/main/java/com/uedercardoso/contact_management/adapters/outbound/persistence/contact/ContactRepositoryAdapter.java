package com.uedercardoso.contact_management.adapters.outbound.persistence.contact;

import java.util.List;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import com.uedercardoso.contact_management.core.domain.Contact;
import com.uedercardoso.contact_management.core.ports.ContactRepositoryPort;

@Component
public class ContactRepositoryAdapter implements ContactRepositoryPort {

    private final SpringDataContactRepository repository;
	private final ContactEntityMapper mapper;

    public ContactRepositoryAdapter(SpringDataContactRepository repository, ContactEntityMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

	@Override
	public List<Contact> findAll() {
		
		final List<ContactEntity> contactEntities = this.repository.findAll();
		
		final List<Contact> contacts = contactEntities.stream()
			.map(items -> mapper.toDomain(items)) 
			.toList();

		return contacts;
	}

	@Override
	public Contact findById(Integer id) {
		
		final ContactEntity contactEntity = this.repository.findById(id).orElse(null);

		if(contactEntity == null) {
			throw new RuntimeException("Contact not found with id: " + id);
		}

		return mapper.toDomain(contactEntity);
	}

	@Override
	public List<Contact> findByName(String name) {
		List<ContactEntity> contactEntities = this.repository.findByName(name);
		
		return contactEntities.stream().map(mapper::toDomain).toList();
	}

	@Override
	public List<Contact> findBySurname(String surname) {
		List<ContactEntity> contactEntities = this.repository.findBySurname(surname);
		
		return contactEntities.stream()
			.map(mapper::toDomain)
			.toList();
	}

	@Override
	public List<Contact> findByPhone(String phone) {
		List<ContactEntity> contactEntities = this.repository.findByPhone(phone);
		
		return contactEntities.stream()
			.map(mapper::toDomain)
			.toList();
	}

	@Override
	public List<Contact> findByValue(String value) {
		List<ContactEntity> contacts = this.repository.findByValue(value);
		
		return contacts.stream()
			.map(mapper::toDomain)
			.toList();	
	}

	@Override
	public List<Contact> findContactById() {
		List<ContactEntity> contactEntities = this.repository.findContactById();
		
		return contactEntities.stream()
			.map(mapper::toDomain)
			.toList();
	}

	@Override
	public List<Contact> findContactByName() {
		List<ContactEntity> contactEntities = this.repository.findContactByName();
		
		return contactEntities.stream()
			.map(mapper::toDomain)
			.toList();
	}

	@Override
	public List<Contact> findContactBySurname() {
		List<ContactEntity> contactEntities = this.repository.findContactBySurname();
		
		return contactEntities.stream()
			.map(mapper::toDomain)
			.toList();
	}

	@Override
	public List<Contact> findContactByPhone() {
		List<ContactEntity> contactEntities = this.repository.findContactByPhone();
		
		return contactEntities.stream()
			.map(mapper::toDomain)
			.toList();
	}

    @Override
	@NonNull
    public Contact save(Contact contact) {
		ContactEntity entity = new ContactEntity(contact);
        
        ContactEntity savedEntity = repository.save(entity);

        return mapper.toDomain(savedEntity);
    }

	@Override
	@NonNull
	public Contact update(Integer id, Contact contact) {
		ContactEntity entity = new ContactEntity(id, contact);
        
        ContactEntity updatedEntity = repository.save(entity);

        return mapper.toDomain(updatedEntity);
	}

	@Override
	public void delete(Integer id) {
		repository.deleteById(id);	
	}
}