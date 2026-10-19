package com.uedercardoso.contact_management.adapters.outbound.persistence.contact;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SpringDataContactRepository extends JpaRepository<ContactEntity, Integer> {

	@Query(value = "SELECT c.* FROM contacts c WHERE c.name LIKE CONCAT('%', :name, '%')", nativeQuery = true)
	public List<ContactEntity> findByName(@Param("name") String name);
	
	@Query(value="SELECT c.* FROM contacts c WHERE c.surname LIKE CONCAT('%', :surname, '%')",nativeQuery=true)
	public List<ContactEntity> findBySurname(@Param("surname") String surname);
	
	@Query(value="SELECT c.* FROM contacts c WHERE c.phone LIKE CONCAT(:phone, '%')",nativeQuery=true)
	public List<ContactEntity> findByPhone(@Param("phone") String phone);

	@Query(value="SELECT c.* FROM contacts c WHERE c.name LIKE CONCAT('%', :value, '%') OR c.surname LIKE CONCAT('%', :value, '%') OR c.phone LIKE CONCAT('%', :value, '%') OR c.email LIKE CONCAT('%', :value, '%') OR c.twitter_link LIKE CONCAT('%', :value, '%') OR c.skype_contact LIKE CONCAT('%', :value, '%')",nativeQuery=true)
	public List<ContactEntity> findByValue(@Param("value") String value);

	@Query(value="SELECT c.* FROM contacts c ORDER BY c.id",nativeQuery=true)
	public List<ContactEntity> findContactById();

	@Query(value="SELECT c.* FROM contacts c ORDER BY c.name",nativeQuery=true)
	public List<ContactEntity> findContactByName();
	
	@Query(value="SELECT c.* FROM contacts c ORDER BY c.surname",nativeQuery=true)
	public List<ContactEntity> findContactBySurname(); 
	
	@Query(value="SELECT c.* FROM contacts c ORDER BY c.phone",nativeQuery=true)
	public List<ContactEntity> findContactByPhone();

}