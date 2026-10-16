package com.uedercardoso.contact_management.core.ports;

import org.springframework.lang.NonNull;
import org.springframework.web.multipart.MultipartFile;

public interface PhotoServicePort {
	
	@NonNull
	String save(MultipartFile file, String contentType, String fileName);	
	
	void delete(String fileName);

	@NonNull
	Boolean exists(String fileName);
}
