package com.dot.tour_info_service_server.service;

import com.dot.tour_info_service_server.dto.request.folder.CartAllRequestDTO;
import com.dot.tour_info_service_server.service.cart.CartService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CartSeviceImplTest {

    @Autowired
    CartService cartService;

    @Test
    void addCart() {
        System.out.println(cartService.addCart(CartAllRequestDTO.builder().mno(3l).fno(6l).pno(1l).build()));
    }

    @Test
    void deleteCart() {
    }
}