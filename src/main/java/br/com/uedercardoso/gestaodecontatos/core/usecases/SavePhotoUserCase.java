package br.com.uedercardoso.gestaodecontatos.core.usecases;

import java.io.IOException;

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

	public String execute(MultipartFile file, String contentType, String fileName) {
		try {
			byte[] data = file.getBytes();
			
			if(data == null || data.length == 0) {
				throw new IllegalArgumentException("Photo data is invalid.");
			}
		
			return this.photoServicePort.save(file, contentType, fileName);
	 	} catch (IOException e) {
			throw new RuntimeException("File is invalid: " + e.getMessage(), e);
        }
	}
}
