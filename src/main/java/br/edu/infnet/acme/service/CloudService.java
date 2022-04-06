package br.edu.infnet.acme.service;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class CloudService {

    private AmazonS3 s3client;

    @Value("${aws.key}")
    private String key;
    @Value("${aws.secret}")
    private String secret;
    @Value("${aws.region}")
    private String region;
    @Value("${aws.bucketName}")
    private String bucket;

    @PostConstruct
    private void initAmazonS3() {
        BasicAWSCredentials basicAWSCredentials = new BasicAWSCredentials(key, secret);
        AWSStaticCredentialsProvider awsStaticCredentialsProvider = new AWSStaticCredentialsProvider(basicAWSCredentials);
        s3client = AmazonS3ClientBuilder.standard().withRegion(region)
                .withCredentials(awsStaticCredentialsProvider).build();
    }

    public List<String> listar() {
        List<String> nomeArquivos = new ArrayList<>();
        ObjectListing objectListing = s3client.listObjects(bucket);
        List<S3ObjectSummary> objectSummaries = objectListing.getObjectSummaries();
        if (objectSummaries != null) {
            for (S3ObjectSummary obj : objectSummaries) {
                String nomeArquivo = obj.getKey();
                nomeArquivos.add(nomeArquivo);
            }
        }
        return nomeArquivos;
    }

    public void save(File file) {
        String fileName = file.getName();
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucket, fileName, file);
        s3client.putObject(putObjectRequest);
    }

    public void save(MultipartFile multipartFile) {
        try {
            File file = convert(multipartFile);
            save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private File convert(MultipartFile multipartFile) throws IOException {
        File convFile = new File(multipartFile.getOriginalFilename());
        FileOutputStream fos = new FileOutputStream(convFile);
        fos.write(multipartFile.getBytes());
        fos.close();
        return convFile;
    }

    public void delete(String fileName) {
        s3client.deleteObject(bucket, fileName);
    }

    public File download(String fileName) throws IOException {
        S3Object object = s3client.getObject(bucket, fileName);
        S3ObjectInputStream objectContent = object.getObjectContent();
        byte[] bytes = objectContent.readAllBytes();

        File file = File.createTempFile("temp", fileName);
        FileOutputStream fos = new FileOutputStream(file);
        fos.write(bytes);
        objectContent.close();
        fos.close();
        return file;
    }
}
