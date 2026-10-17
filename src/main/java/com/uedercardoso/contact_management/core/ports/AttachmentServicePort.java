package com.uedercardoso.contact_management.core.ports;

import org.springframework.lang.NonNull;
import org.springframework.web.multipart.MultipartFile;

public interface AttachmentServicePort {
	
	@NonNull
	String save(MultipartFile file);	
	
	void delete(String fileName);

	@NonNull
	Boolean exists(String fileName);

	String getExtension(MultipartFile file);
}
