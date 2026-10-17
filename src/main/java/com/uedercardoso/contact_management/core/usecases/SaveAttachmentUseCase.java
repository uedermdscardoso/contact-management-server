package com.uedercardoso.contact_management.core.usecases;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.io.FilenameUtils;
import org.springframework.web.multipart.MultipartFile;

import com.uedercardoso.contact_management.core.ports.AttachmentServicePort;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SaveAttachmentUseCase {
	
	private final AttachmentServicePort attachmentServicePort;
	private final List<String> allowedExtensions = Arrays.asList("jpg", "jpeg", "avif", "webp", "pdf");

	public SaveAttachmentUseCase(AttachmentServicePort attachmentServicePort) {
		this.attachmentServicePort = attachmentServicePort;
	}

	public String execute(MultipartFile file) {
		try {
			byte[] data = file.getBytes();
			String extension = this.attachmentServicePort.getExtension(file);

			if(data == null || data.length == 0) {
				throw new IllegalArgumentException("Attachment data is invalid.");
			}

			if(!allowedExtensions.contains(extension.toLowerCase())) {
    			throw new IllegalArgumentException("File type not allowed.");
    		}

			return this.attachmentServicePort.save(file);
	 	} catch (IOException e) {
			throw new RuntimeException("File is invalid: " + e.getMessage(), e);
        }
	}

}
