package com.uedercardoso.contact_management.adapters.outbound.persistence.contact;

import org.springframework.stereotype.Component;

import com.uedercardoso.contact_management.core.domain.Contact;

@Component
public class ContactEntityMapper {

    public Contact toDomain(ContactEntity entity) {
        if(entity == null) {
            return null;
        }

        return new Contact(entity);
    }

    public ContactEntity toEntity(Contact domain) {
        if(domain == null) {
            return null;
        }

        final ContactEntity entity = new ContactEntity(domain);
        
        return entity;
    }
}
