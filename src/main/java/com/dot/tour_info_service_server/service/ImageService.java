package com.dot.tour_info_service_server.service;

import com.dot.tour_info_service_server.dto.FileDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ImageService {

    // 이미지 업로드할때 bno와 연결하는 로직
    void ImageUpload(String saveName);

    List<FileDTO> uploadFile(List<MultipartFile> multipartFiles);
    Object deleteFile(String fileName);
}
