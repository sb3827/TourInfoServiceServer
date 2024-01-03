package com.dot.tour_info_service_server.service;

import com.dot.tour_info_service_server.dto.CartDTO;
import com.dot.tour_info_service_server.entity.Cart;
import com.dot.tour_info_service_server.repository.CartRepository;
import com.dot.tour_info_service_server.repository.PlaceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartSeviceImpl implements CartService{

    //장바구니 repository
    private final CartRepository cartRepository;

    //장소 repository
    private final PlaceRepository placeRepository;


    //폴더에 장소 추가
    @Override
    public Long addCart(CartDTO cartDTO) {
        if (placeRepository.findById(cartDTO.getPno()).isPresent()){
            Cart cart=dtoToEntity(cartDTO);
            cartRepository.save(cart);
            return cartDTO.getFno();
        }
        return -1l;
    }


    //폴더에 장소 제거
    @Override
    public Long deleteCart(CartDTO cartDTO) {
        cartRepository.delete(dtoToEntity(cartDTO));
        return cartDTO.getPno();
    }
}
