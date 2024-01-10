package com.dot.tour_info_service_server.service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.dot.tour_info_service_server.dto.FileDTO;
import com.dot.tour_info_service_server.entity.Image;
import com.dot.tour_info_service_server.repository.ImageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

@Service
@Log4j2
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService{
    private final AmazonS3Client amazonS3Client;
    private final ImageRepository imageRepository;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;
    @Override
    public void ImageUpload(String saveName) {
        Image image = Image.builder()
                .src(saveName)
                .board(null)
                .build();
        imageRepository.save(image);
    }

    @Override
    public List<FileDTO> uploadFile(List<MultipartFile> multipartFiles) {
        List<FileDTO> files = new ArrayList<>();
        int count = 1;

        for (MultipartFile multipartFile : multipartFiles) {
            String originalFileName = multipartFile.getOriginalFilename();

            //String fileUrl = "http://" + bucket + "/upload_" + fileName;
            String renamedFileName = getRenamedFileName(originalFileName, count);
            count++;
            String fileUrl = "";

            log.info("renamedFileName = {}", renamedFileName);
            try {
                ObjectMetadata metadata = new ObjectMetadata();
                metadata.setContentType(multipartFile.getContentType());
                metadata.setContentLength(multipartFile.getSize());
                amazonS3Client.putObject(bucket, renamedFileName, multipartFile.getInputStream(), metadata);

                fileUrl = amazonS3Client.getUrl(bucket, renamedFileName).toString();
                log.info("fileUrl = {}", fileUrl);
            } catch (IOException e) {
                e.fillInStackTrace();
                log.error("file upload에 실패하였습니다.");
            }

            files.add(
                    FileDTO.builder()
                            .originalFileName(originalFileName)
                            .renamedFileName(renamedFileName)
                            .fileUrl(fileUrl)
                            .build());
        }
        return files;
    }

    @Override
    public Object deleteFile(String fileName) {
        boolean isObjectExist = amazonS3Client.doesObjectExist(bucket, fileName);

        if(isObjectExist){
            amazonS3Client.deleteObject(bucket, fileName);
        } else {
            return "file not found";
        }
        return "delete file";
    }

    private String getRenamedFileName(String originalFileName, int count) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault());
        Date date = new Date();
        String str = sdf.format(date);

        String ext = originalFileName.substring(originalFileName.indexOf(".") +1);
        String renamedFileName = originalFileName.replaceAll(originalFileName, str) + "_"
                + String.format("%02d", count) + "." +ext;

        return renamedFileName;
    }
}
