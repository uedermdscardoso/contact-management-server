package br.com.uedercardoso.gestaodecontatos.core.usecases;

import br.com.uedercardoso.gestaodecontatos.core.ports.PhotoServicePort;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CheckPhotoUserCase {
	
	private final PhotoServicePort photoServicePort;
	
	public CheckPhotoUserCase(PhotoServicePort photoServicePort) {
		this.photoServicePort = photoServicePort;
	}

	public Boolean execute(String fileName) {
		try {
			if(fileName == null || fileName.isEmpty()) {
				throw new IllegalArgumentException("File name is invalid.");
			}
		
			return this.photoServicePort.exists(fileName);
	 	} catch (Exception e) {
			log.error("File does not exist", e);

			return false;
        }
	}
}
