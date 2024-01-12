package com.dot.tour_info_service_server.service;

import com.dot.tour_info_service_server.dto.FileDTO;
import com.dot.tour_info_service_server.dto.ResponseUploadDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ImageService {
    ResponseUploadDTO ImageUpload(String saveName);
    void deleteImage(String fileName);
    List<FileDTO> uploadFile(List<MultipartFile> multipartFiles);
    Object deleteFile(String fileName);
}
