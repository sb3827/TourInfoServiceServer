package com.yayum.tour_info_service_server.service;

import com.yayum.tour_info_service_server.dto.PlaceDTO;
import com.yayum.tour_info_service_server.entity.Category;
import com.yayum.tour_info_service_server.entity.Place;

import java.util.List;

public interface PlaceService {
    Long registerPlace(PlaceDTO dto);

    List<Long> findPlace(Category filter, String search);

    void removePlace(Long pno);

    default Place dtoToEntity(PlaceDTO dto){
        Place place = Place.builder()
                .pno(dto.getPno())
                .name(dto.getName())
                .lng(dto.getLng())
                .lat(dto.getLat())
                .roadAddress(dto.getRoadAddress())
                .localAddress(dto.getLocalAddress())
                .engAddress(dto.getEngAddress())
                .category(Category.valueOf(dto.getCategory()))
                .cart(dto.getCart())
                .build();
        return place;
    }
    default PlaceDTO entityToDto(Place dto){
        PlaceDTO placeDTO = PlaceDTO.builder()
                .pno(dto.getPno())
                .name(dto.getName())
                .lng(dto.getLng())
                .lat(dto.getLat())
                .roadAddress(dto.getRoadAddress())
                .localAddress(dto.getLocalAddress())
                .engAddress(dto.getEngAddress())
                .category(String.valueOf(Category.valueOf(String.valueOf(dto.getCategory()))))
                .cart(dto.getCart())
                .regDate(dto.getRegDate())
                .modDate(dto.getModDate())
                .build();
        return placeDTO;
    }
}
