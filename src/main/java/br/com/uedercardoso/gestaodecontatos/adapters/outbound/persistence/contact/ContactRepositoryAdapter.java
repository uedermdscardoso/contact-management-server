package br.com.uedercardoso.gestaodecontatos.adapters.outbound.persistence.contact;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import br.com.uedercardoso.gestaodecontatos.core.domain.Contact;
import br.com.uedercardoso.gestaodecontatos.core.ports.ContactRepositoryPort;

@Component
public class ContactRepositoryAdapter implements ContactRepositoryPort {

    private final SpringDataContactRepository repository;
	private final ContactEntityMapper mapper;

    public ContactRepositoryAdapter(SpringDataContactRepository repository, ContactEntityMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

	@Override
	@NonNull
	public Contact findById(Integer id) {
		
		final ContactEntity contactEntity = this.repository.findById(id).orElse(null);

		if(contactEntity == null) {
			throw new RuntimeException("Contact not found with id: " + id);
		}

		return mapper.toDomain(contactEntity);
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