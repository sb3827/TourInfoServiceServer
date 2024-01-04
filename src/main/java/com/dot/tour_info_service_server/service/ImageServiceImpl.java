package com.dot.tour_info_service_server.service;

import com.dot.tour_info_service_server.entity.Image;
import com.dot.tour_info_service_server.repository.ImageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@Log4j2
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService{

    private final ImageRepository imageRepository;
    @Override
    public void ImageUpload(String saveName) {
        Image image = Image.builder()
                .src(saveName)
                .board(null)
                .build();
        imageRepository.save(image);
    }
}
