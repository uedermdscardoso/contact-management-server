package com.uedercardoso.contact_management.adapters.outbound.persistence.contact;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.uedercardoso.contact_management.core.domain.Contact;

public interface SpringDataContactRepository extends JpaRepository<ContactEntity, Integer> {

	@Query(value = "SELECT c.* FROM Contact c WHERE c.name LIKE CONCAT('%', :name, '%')", nativeQuery = true)
	public List<Contact> findByName(@Param("name") String name);
	
	@Query(value="SELECT c.* FROM Contact c WHERE c.surname LIKE CONCAT('%', :surname, '%')",nativeQuery=true)
	public List<Contact> findBySurname(@Param("surname") String surname);
	
	@Query(value="SELECT c.* FROM Contact c WHERE c.phone LIKE CONCAT(:phone, '%')",nativeQuery=true)
	public List<Contact> findByPhone(@Param("phone") String phone);

	@Query(value="SELECT c.* FROM Contact c ORDER BY c.id",nativeQuery=true)
	public List<Contact> findContactById();

	@Query(value="SELECT c.* FROM Contact c ORDER BY c.name",nativeQuery=true)
	public List<Contact> findContactByName();
	
	@Query(value="SELECT c.* FROM Contact c ORDER BY c.surname",nativeQuery=true)
	public List<Contact> findContactBySurname(); 
	
	@Query(value="SELECT c.* FROM Contact c ORDER BY c.phone",nativeQuery=true)
	public List<Contact> findContactByPhone();

}