package br.com.uedercardoso.gestaodecontatos.core.usecases;

import java.io.IOException;
import java.util.Optional;

import org.springframework.lang.NonNull;
import org.springframework.web.multipart.MultipartFile;

import br.com.uedercardoso.gestaodecontatos.core.ports.PhotoServicePort;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SavePhotoUserCase {
	
	private final PhotoServicePort photoServicePort;
	
	public SavePhotoUserCase(PhotoServicePort photoServicePort) {
		this.photoServicePort = photoServicePort;
	}

	public Optional<String> execute(MultipartFile file, String contentType, String fileName) {
		try {
			byte[] data = file.getBytes();
			
			if(data == null || data.length == 0) {
				throw new IllegalArgumentException("Photo data is invalid.");
			}
		
			return photoServicePort.save(file, contentType, fileName);
	 	} catch (IOException e) {
			log.error("File is invalid", e);
			return Optional.empty();
        }
	}
}
