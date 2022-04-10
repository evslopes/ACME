package br.edu.infnet.acme.service;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.*;
import org.apache.commons.io.FileUtils;
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
public class AmazonService {

    private AmazonS3 s3client;

    @Value("${aws.s3.access-key}")
    private String key;
    @Value("${aws.s3.secret-key}")
    private String secret;
    @Value("${aws.s3.bucket-name}")
    private String bucket;

    @PostConstruct
    private void initAmazonS3() {
        BasicAWSCredentials basicAWSCredentials = new BasicAWSCredentials(key, secret);
        AWSStaticCredentialsProvider awsStaticCredentialsProvider = new AWSStaticCredentialsProvider(basicAWSCredentials);
        s3client = AmazonS3ClientBuilder.standard().withRegion(Regions.US_EAST_1)
                .withCredentials(awsStaticCredentialsProvider).build();
    }

    private File convertMultipartToFile(MultipartFile file) throws IOException {

        File convFile = new File(file.getOriginalFilename());
        FileOutputStream fos = new FileOutputStream(convFile);
        fos.write(file.getBytes());
        fos.close();
        return convFile;
    }

    public String uploadFile(MultipartFile multipartFile) {

        try {
            File file = convertMultipartToFile(multipartFile);
            String fileName = multipartFile.getOriginalFilename();
            s3client.putObject(new PutObjectRequest(bucket, fileName, file));
            return s3client.getUrl(bucket, fileName).toString();
        }
        catch (IOException e) {
            return e.getMessage();
        }
    }

    public String deleteFile(String file) {

        s3client.deleteObject(bucket, file);
        return "Arquivo excluido";
    }

    public String listFiles() {
        String files = "";

        ListObjectsV2Result result = s3client.listObjectsV2(bucket);
        List<S3ObjectSummary> objects = result.getObjectSummaries();
        for (S3ObjectSummary os : objects) {
            files += os.getKey() + "\n";
        }
        return files;
    }

    public String downloadFile(String fileName) {

        S3Object s3Object = s3client.getObject(bucket, fileName);
        S3ObjectInputStream inputStream = s3Object.getObjectContent();
        try {
            FileUtils.copyInputStreamToFile(inputStream, new File(fileName));
            return "Arquivo baixado para o diretório do projeto";
        } catch (IOException | AmazonServiceException ex) {
            return ex.getMessage();
        }
    }
}
