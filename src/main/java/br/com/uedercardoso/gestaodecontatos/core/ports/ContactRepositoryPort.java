package br.com.uedercardoso.gestaodecontatos.core.ports;

import org.springframework.lang.NonNull;

import br.com.uedercardoso.gestaodecontatos.core.domain.Contact;

public interface ContactRepositoryPort {

	@NonNull
	Contact findById(Integer id);

	@NonNull
	Contact save(Contact contact);

	@NonNull
	Contact update(Integer id, Contact contact);

	void delete(Integer id);
}
