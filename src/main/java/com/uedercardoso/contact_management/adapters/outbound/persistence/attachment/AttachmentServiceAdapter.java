package com.uedercardoso.contact_management.adapters.outbound.persistence.attachment;

import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.uedercardoso.contact_management.core.ports.AttachmentServicePort;
import lombok.extern.slf4j.Slf4j;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.HeadObjectRequest;
import software.amazon.awssdk.services.s3.model.NoSuchKeyException;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.S3Exception;

@Slf4j
@Component
public class AttachmentServiceAdapter implements AttachmentServicePort {
	
	private final S3Client s3Client;

    @Value("${aws.s3.bucket-name}")
    private String bucketName;
    
	@Value("${aws.s3.endpoint}")
    private String endpoint;

    public AttachmentServiceAdapter(S3Client s3Client) {
        this.s3Client = s3Client;
    }
	
	@Override
	public Optional<String> save(MultipartFile file) {
		try {
			byte[] data = file.getBytes();
			
			final String id = UUID.randomUUID().toString();
			String contentType = file.getContentType();
			String fileName = file.getOriginalFilename().substring(0, file.getOriginalFilename().lastIndexOf(".")-1);
			String extension = getExtension(file);
			final String name = fileName+"-"+id+"."+extension;

            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
				.bucket(bucketName)
				.key(name)
				.contentType(contentType)
				.build();

            s3Client.putObject(putObjectRequest, RequestBody.fromBytes(data));
            
            System.out.println("File saved successfully: " + name);

			String url = endpoint + "/" + bucketName + "/" + name;

			return Optional.of(url);
        } catch (S3Exception | IOException e) {
			throw new RuntimeException("Error occurred while uploading file to S3/Garage: " + e.getMessage(), e);
        }
	}
	
	@Override
	public void delete(String fileName) {
		try {
            DeleteObjectRequest deleteObjectRequest = DeleteObjectRequest.builder()
				.bucket(bucketName)
				.key(fileName)
				.build();

            s3Client.deleteObject(deleteObjectRequest);
            
            System.out.println("File successfully deleted: " + fileName);
        } catch (S3Exception e) {
			log.error("Error occurred while deleting file to S3/Garage", e);
        }
	}

	@Override
	@NonNull
	public Boolean exists(String fileName) {
		try {
            HeadObjectRequest headObjectRequest = HeadObjectRequest.builder()
                    .bucket(bucketName)
                    .key(fileName)
                    .build();

            s3Client.headObject(headObjectRequest);
            
			return true;
        } catch(NoSuchKeyException e) {
            return false;
        } catch(S3Exception e) {
            if(e.statusCode() == 404) {
                return false;
            }
        	throw new RuntimeException("Error checking for file existence in Garage: " + e.awsErrorDetails().errorMessage(), e);
        }
	}

	public String getExtension(MultipartFile file) {
    	String nomeOriginal = file.getOriginalFilename();

    	return FilenameUtils.getExtension(nomeOriginal);
	}
}
