package br.com.uedercardoso.gestaodecontatos.adapters.outbound.persistence;

import br.com.uedercardoso.gestaodecontatos.adapters.outbound.persistence.contact.ContactEntity;
import br.com.uedercardoso.gestaodecontatos.adapters.outbound.persistence.contact.ContactEntityMapper;
import br.com.uedercardoso.gestaodecontatos.core.domain.Contact;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-10-16T07:11:00-0300",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.46.100.v20260624-0231, environment: Java 21.0.11 (Eclipse Adoptium)"
)
@Component
public class ContactEntityMapperImpl implements ContactEntityMapper {

    @Override
    public Contact toDomain(ContactEntity entity) {
        if ( entity == null ) {
            return null;
        }

        Contact contact = new Contact();

        contact.setEmail( entity.getEmail() );
        contact.setId( entity.getId() );
        contact.setName( entity.getName() );
        contact.setPhone( entity.getPhone() );
        contact.setPhotoPath( entity.getPhotoPath() );
        contact.setSkypeContact( entity.getSkypeContact() );
        contact.setSurname( entity.getSurname() );
        contact.setTwitterLink( entity.getTwitterLink() );

        return contact;
    }

    @Override
    public ContactEntity toEntity(Contact domainContact) {
        if ( domainContact == null ) {
            return null;
        }

        ContactEntity contactEntity = new ContactEntity();

        contactEntity.setEmail( domainContact.getEmail() );
        contactEntity.setId( domainContact.getId() );
        contactEntity.setName( domainContact.getName() );
        contactEntity.setPhone( domainContact.getPhone() );
        contactEntity.setPhotoPath( domainContact.getPhotoPath() );
        contactEntity.setSkypeContact( domainContact.getSkypeContact() );
        contactEntity.setSurname( domainContact.getSurname() );
        contactEntity.setTwitterLink( domainContact.getTwitterLink() );

        return contactEntity;
    }
}
