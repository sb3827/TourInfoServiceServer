package com.dot.tour_info_service_server.repository;

import com.dot.tour_info_service_server.entity.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.stream.IntStream;

@SpringBootTest
class CartRepositoryTests {
    @Autowired
    CartRepository cartRepository;

    @Test
    void insertCartDummies(){
        IntStream.rangeClosed(1,2).forEach(i -> {

//            long mno = (long)(Math.random()*10)+ 1;
//            long pno = (long)(Math.random()*10)+ 10;
//            long fno = (long)(Math.random()*5)+ 1;

            Member member = Member.builder().mno(3L).build();
            Place place = Place.builder().pno(9L).build();
            Folder folder = Folder.builder().fno(2L).build();

            CartPK cartPK = CartPK.builder().member(member).place(place).folder(folder).build();
            Cart cart = Cart.builder().cartPK(cartPK).build();

            cartRepository.save(cart);
        });
    }

    @Test
    void removeCartTest(){
        cartRepository.removeCart(16L);
    }

}