package com.yayum.tour_info_service_server.repository;

import com.yayum.tour_info_service_server.entity.Board;
import com.yayum.tour_info_service_server.entity.Image;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ImageRepositoryTests {

  @Autowired
  ImageRepository imageRepository;

  @Test
  void insertImageDummies() {
    IntStream.rangeClosed(1, 3).forEach(i -> {
//            long bno = (long) (Math.random()* 3) + 10;

      Board board = Board.builder().bno(5L).build();
      Image image = Image.builder().board(board).src("test" + i).build();

      imageRepository.save(image);
    });
  }

  @Test
  void removeImageTest() {
    imageRepository.removeImage(12L);
  }

  @Transactional
  @Test
  public void inoByBno() {
    List<Image> result = imageRepository.selectImageByBno(3L);
    System.out.println(result);
  }

}