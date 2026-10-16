package br.com.uedercardoso.gestaodecontatos.adapters.outbound.persistence.photo;

import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.multipart.MultipartFile;

import br.com.uedercardoso.gestaodecontatos.core.ports.PhotoServicePort;
import lombok.extern.slf4j.Slf4j;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.S3Exception;

@Slf4j
public class PhotoServiceAdapter implements PhotoServicePort {
	
	private final S3Client s3Client;

    @Value("${aws.s3.bucket-name}")
    private String bucketName;
    
	@Value("${aws.s3.endpoint}")
    private String endpoint;

    public PhotoServiceAdapter(S3Client s3Client) {
        this.s3Client = s3Client;
    }
	
	@Override
	public Optional<String> save(MultipartFile file, String contentType, String fileName) {
		try {
			byte[] data = file.getBytes();

			final String id = UUID.randomUUID().toString();
			final String name = fileName+"-"+id;

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
			log.error("Error occurred while uploading file to S3/Garage: ", e);
			return Optional.empty();
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
            throw new RuntimeException("Error occurred while deleting file to S3/Garage: " + e.awsErrorDetails().errorMessage(), e);
        }
	}
}
