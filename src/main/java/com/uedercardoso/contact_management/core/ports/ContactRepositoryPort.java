package com.uedercardoso.contact_management.core.ports;

import java.util.List;

import org.springframework.lang.NonNull;

import com.uedercardoso.contact_management.core.domain.Contact;

public interface ContactRepositoryPort {

	List<Contact> findAll();

	Contact findById(Integer id);

	List<Contact> findByName(String name);

	List<Contact> findBySurname(String surname);

	List<Contact> findByPhone(String phone);

	List<Contact> findContactById();

	List<Contact> findContactByName();

	List<Contact> findContactBySurname();

	List<Contact> findContactByPhone();

	@NonNull
	Contact save(Contact contact);

	@NonNull
	Contact update(Integer id, Contact contact);

	void delete(Integer id);
}
