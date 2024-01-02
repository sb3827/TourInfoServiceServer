package com.dot.tour_info_service_server.service;

import com.dot.tour_info_service_server.dto.CartDTO;
import com.dot.tour_info_service_server.entity.*;
import com.yayum.tour_info_service_server.entity.*;

public interface CartService {

    //장바구니 추가 - 추후 진행
    Long addCart(CartDTO cartDTO);

    //장바구니 삭제 - 추후 진행
    Long deleteCart(CartDTO cartDTO);


    //Cart dtoToEntity
    default Cart dtoToEntity(CartDTO cartDTO){
        CartPK cartPK=CartPK.builder()
                .member(Member.builder().mno(cartDTO.getMno()).build())
                .place(Place.builder().pno(cartDTO.getPno()).build())
                .folder(Folder.builder().fno(cartDTO.getFno()).build())
                .build();
        Cart cart=Cart.builder()
                .cartPK(cartPK)
                .build();

        return cart;
    }

    //Cart entityToDTO
    default CartDTO entityToDto(Cart cart){
        CartDTO cartDTO=CartDTO.builder()
                .mno(cart.getCartPK().getMember().getMno())
                .pno(cart.getCartPK().getPlace().getPno())
                .fno(cart.getCartPK().getFolder().getFno())
                .build();
        return cartDTO;
    }
}
