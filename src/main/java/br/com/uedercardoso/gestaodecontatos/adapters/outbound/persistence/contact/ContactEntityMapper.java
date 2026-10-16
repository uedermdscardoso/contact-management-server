package br.com.uedercardoso.gestaodecontatos.adapters.outbound.persistence.contact;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.springframework.lang.NonNull;

import br.com.uedercardoso.gestaodecontatos.core.domain.Contact;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ContactEntityMapper {
   
	@NonNull Contact toDomain(ContactEntity entity);

    @NonNull ContactEntity toEntity(Contact domainContact);

}