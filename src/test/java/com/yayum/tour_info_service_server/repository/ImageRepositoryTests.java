package com.yayum.tour_info_service_server.repository;

import com.yayum.tour_info_service_server.entity.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ImageRepositoryTests {
  @Autowired
  private ImageRepository imageRepository;

  @Test
  public void insert() {
    IntStream.rangeClosed(1, 1).forEach(i -> {

      Board board = Board.builder().bno(12L).build();

      Place place = Place.builder().pno(1L).build();

      Image image = Image.builder()
          .board(board)
          .ino(place.getPno())
          .src("test")
          .build();
      imageRepository.save(image);
    });
  }


}