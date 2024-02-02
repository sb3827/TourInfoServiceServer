package com.dot.tour_info_service_server.repository;

import com.dot.tour_info_service_server.entity.*;
import com.dot.tour_info_service_server.entity.cart.Cart;
import com.dot.tour_info_service_server.entity.cart.CartPK;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CartRepositoryTest {

    @Autowired
    private CartRepository cartRepository;

    //스팟 추가 테스트
    @Test
    public void testSpotAdd(){
        CartPK cartPK= CartPK.builder()
                .member(Member.builder().mno(2l).build())
                .folder(Folder.builder().fno(2l).build())
                .place(Place.builder().pno(2l).build())
                .build();

        Cart cart= Cart.builder()
                .cartPK(cartPK)
                .build();
        cartRepository.save(cart);
    }

    //스팟 제거 테스트
    @Test
    public void testSpotDelete(){
        CartPK cartPK=CartPK.builder()
                .member(Member.builder().mno(2l).build())
                .folder(Folder.builder().fno(2l).build())
                .place(Place.builder().pno(2l).build())
                .build();
        Cart cart=Cart.builder()
                .cartPK(cartPK)
                .build();

        cartRepository.delete(cart);
    }

    @Test
    public void deleteAll(){
        Folder folder=Folder.builder().fno(583l).build();
        cartRepository.deleteAllByCartPK_Folder(folder);
    }
}