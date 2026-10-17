package com.uedercardoso.contact_management.core.usecases;

import com.uedercardoso.contact_management.core.ports.AttachmentServicePort;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CheckAttachmentUseCase {
	
	private final AttachmentServicePort attachmentServicePort;
	
	public CheckAttachmentUseCase(AttachmentServicePort attachmentServicePort) {
		this.attachmentServicePort = attachmentServicePort;
	}

	public Boolean execute(String fileName) {
		try {
			if(fileName == null || fileName.isEmpty()) {
				throw new IllegalArgumentException("File name is invalid.");
			}
		
			return this.attachmentServicePort.exists(fileName);
	 	} catch (Exception e) {
			log.error("File does not exist", e);

			return false;
        }
	}
}
