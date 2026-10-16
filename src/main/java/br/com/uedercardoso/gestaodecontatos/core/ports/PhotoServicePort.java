package br.com.uedercardoso.gestaodecontatos.core.ports;

import java.util.Optional;

import org.springframework.lang.NonNull;
import org.springframework.web.multipart.MultipartFile;

public interface PhotoServicePort {
	@NonNull
	Optional<String> save(MultipartFile file, String contentType, String fileName);	
	
	void delete(String fileName);
}
