package com.dot.tour_info_service_server.controller;


import com.dot.tour_info_service_server.service.ImageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@RestController
@Log4j2
@RequiredArgsConstructor
@RequestMapping("/board")
public class ImageController {

    private final ImageService imageService;

    @Value("${org.zerock.upload.path}")
    private String uploadPath;

    //이미지파일 업로드
    @PostMapping(value = {"/uploadImage"}, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)

    public ResponseEntity<List<Map<String, String>>> uploadImage(@RequestParam MultipartFile[] imageFiles){
        List<Map<String, String>> resultList = new ArrayList<>();

        for (MultipartFile imageFile: imageFiles) {
            String originalName = imageFile.getOriginalFilename();
            String fileName = originalName.substring(originalName.lastIndexOf("\\")+1);
            log.info("fileName: "+ fileName );
            String folderPath = makeFolder();
            String uuid = UUID.randomUUID().toString();
            String saveName = uploadPath + File.separator + folderPath + File.separator +
                    uuid + "_" + fileName;

            Path savePath = Paths.get(saveName); // Path : 파일 올리는 지정된 경로를 가리킬 때 사용

            try {
                imageFile.transferTo(savePath);
                Files.write(savePath, imageFile.getBytes());
                log.info("savePath: "+ savePath);

                Map<String, String> imageInfo = new HashMap<>();
                imageInfo.put("saveName", saveName);
                resultList.add(imageInfo);

            } catch (IOException e) {
                e.printStackTrace();
            }
            imageService.ImageUpload(saveName);
        }//forEnd
        return new ResponseEntity<>(resultList, HttpStatus.OK);
    }

    private String makeFolder() {
        String str = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
        String folderPath = str.replace("/", File.separator);

        File uploadPathFolder = new File(uploadPath, folderPath);
        if (uploadPathFolder.exists() == false) {
            uploadPathFolder.mkdirs();
        }
        return folderPath;
    }
}
