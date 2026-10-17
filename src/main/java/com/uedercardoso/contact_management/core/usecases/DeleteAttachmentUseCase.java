package com.uedercardoso.contact_management.core.usecases;

import com.uedercardoso.contact_management.core.ports.AttachmentServicePort;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DeleteAttachmentUseCase {

	private final AttachmentServicePort attachmentServicePort;
	
	public DeleteAttachmentUseCase(AttachmentServicePort attachmentServicePort) {
		this.attachmentServicePort = attachmentServicePort;
	}

	 public void execute(String fileName) {
		try {
			if(fileName == null || fileName.isEmpty()) {
				throw new IllegalArgumentException("File name is invalid.");
			}
		
			this.attachmentServicePort.delete(fileName);
	 	} catch (Exception e) {
			log.error("File is invalid", e);
        }
	}
}