package br.com.uedercardoso.gestaodecontatos.adapters.outbound.persistence.contact;

import br.com.uedercardoso.gestaodecontatos.core.domain.Contact;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-10-16T07:57:21-0300",
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

        contact.setId( entity.getId() );
        contact.setName( entity.getName() );
        contact.setSurname( entity.getSurname() );
        contact.setPhone( entity.getPhone() );
        contact.setPhotoPath( entity.getPhotoPath() );
        contact.setEmail( entity.getEmail() );
        contact.setTwitterLink( entity.getTwitterLink() );
        contact.setSkypeContact( entity.getSkypeContact() );

        return contact;
    }

    @Override
    public ContactEntity toEntity(Contact domainContact) {
        if ( domainContact == null ) {
            return null;
        }

        ContactEntity contactEntity = new ContactEntity();

        contactEntity.setId( domainContact.getId() );
        contactEntity.setName( domainContact.getName() );
        contactEntity.setSurname( domainContact.getSurname() );
        contactEntity.setPhone( domainContact.getPhone() );
        contactEntity.setPhotoPath( domainContact.getPhotoPath() );
        contactEntity.setEmail( domainContact.getEmail() );
        contactEntity.setTwitterLink( domainContact.getTwitterLink() );
        contactEntity.setSkypeContact( domainContact.getSkypeContact() );

        return contactEntity;
    }
}
