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
		return this.repository.findByName(name);
	}

	@Override
	public List<Contact> findBySurname(String surname) {
		return this.repository.findBySurname(surname);
	}

	@Override
	public List<Contact> findByPhone(String phone) {
		return this.repository.findByPhone(phone);
	}

	@Override
	public List<Contact> findContactById() {
		return this.repository.findContactById();
	}

	@Override
	public List<Contact> findContactByName() {
		return this.repository.findContactByName();
	}

	@Override
	public List<Contact> findContactBySurname() {
		return this.repository.findContactBySurname();
	}

	@Override
	public List<Contact> findContactByPhone() {
		return this.repository.findContactByPhone();
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