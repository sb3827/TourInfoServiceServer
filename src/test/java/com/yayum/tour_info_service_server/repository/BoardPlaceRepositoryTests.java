package com.yayum.tour_info_service_server.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BoardPlaceRepositoryTests {

  @Autowired
  private BoardPlaceRepository boardPlaceRepository;

  @Test
  public void test () {
    boardPlaceRepository.deleteByBno(11L);
  }

}