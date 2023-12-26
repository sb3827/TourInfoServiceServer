package com.yayum.tour_info_service_server.repository;

import com.yayum.tour_info_service_server.entity.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.IntStream;

@SpringBootTest
class ImageRepositoryTests {
  @Autowired
  private ImageRepository imageRepository;

  @Test
  public void insert() {
    IntStream.rangeClosed(1, 3).forEach(i -> {

      Board board = Board.builder().bno(3L).build();

      Place place = Place.builder().pno(1L).build();

      Image image = Image.builder()
          .board(board)
          .ino(place.getPno())
          .src("test2")
          .build();
      imageRepository.save(image);
    });
  }

  @Transactional
  @Test
  public void inoByBno() {
    List<Image> result = imageRepository.selectImageByBno(3L);
      System.out.println(result);
  }

}