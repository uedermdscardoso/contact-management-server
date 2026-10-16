package com.uedercardoso.contact_management.core.usecases;

import com.uedercardoso.contact_management.core.ports.ContactRepositoryPort;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DeleteContactUseCase {

	private final ContactRepositoryPort contactRepositoryPort;
	
	public DeleteContactUseCase(ContactRepositoryPort contactRepositoryPort) {
		this.contactRepositoryPort = contactRepositoryPort;
	}

	 public void execute(Integer id) {
		try {
			if(id == null || id <= 0) {
				throw new IllegalArgumentException("Contact ID is invalid.");
			}
		
			this.contactRepositoryPort.delete(id);
	 	} catch (Exception e) {
			log.error("ID is invalid", e);
        }
	}
}