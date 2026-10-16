package br.com.uedercardoso.gestaodecontatos.core.usecases;

import br.com.uedercardoso.gestaodecontatos.core.ports.PhotoServicePort;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DeletePhotoUserCase {

	private final PhotoServicePort photoServicePort;
	
	public DeletePhotoUserCase(PhotoServicePort photoServicePort) {
		this.photoServicePort = photoServicePort;
	}

	 public void execute(String fileName) {
		try {
			if(fileName == null || fileName.isEmpty()) {
				throw new IllegalArgumentException("File name is invalid.");
			}
		
			photoServicePort.delete(fileName);
	 	} catch (Exception e) {
			log.error("File is invalid", e);
        }
	}
}