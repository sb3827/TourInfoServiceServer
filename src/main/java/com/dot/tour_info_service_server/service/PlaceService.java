package com.dot.tour_info_service_server.service;

import com.dot.tour_info_service_server.entity.Category;
import com.dot.tour_info_service_server.dto.PlaceDTO;
import com.dot.tour_info_service_server.entity.Place;

import java.util.List;
import java.util.Map;

public interface PlaceService {
    Long registerPlace(PlaceDTO dto);

    void removePlace(Long pno);

    List<PlaceDTO> searchPlace(Category filter, String search);

    Map<String, Object> getPlaceCount(Long mno);

    default Place dtoToEntity(PlaceDTO dto){
        Place place = Place.builder()
                .pno(dto.getPno())
                .name(dto.getName())
                .lng(dto.getLng())
                .lat(dto.getLat())
                .roadAddress(dto.getRoadAddress())
                .localAddress(dto.getLocalAddress())
                .engAddress(dto.getEngAddress())
                .category(dto.getCategory())
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
                .category(dto.getCategory())
                .cart(dto.getCart())
                .regDate(dto.getRegDate())
                .modDate(dto.getModDate())
                .build();
        return placeDTO;
    }
}
