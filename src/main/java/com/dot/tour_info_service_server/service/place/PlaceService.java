package com.dot.tour_info_service_server.service.place;

import com.dot.tour_info_service_server.dto.request.place.RegistPlaceRequestDTO;
import com.dot.tour_info_service_server.entity.Category;
import com.dot.tour_info_service_server.dto.PlaceDTO;
import com.dot.tour_info_service_server.entity.Place;
import org.apache.coyote.BadRequestException;

import java.util.List;
import java.util.Map;

public interface PlaceService {
    Long registerPlace(RegistPlaceRequestDTO dto);

    boolean modifyPlace(Long pno, String name);

    void removePlace(Long pno) throws BadRequestException;

    List<PlaceDTO> searchPlace(Category filter, String search, int page);

    Map<String, Object> getPlaceCount(Long mno);

    default Place dtoToEntity(RegistPlaceRequestDTO dto){
        return Place.builder()
                .name(dto.getName())
                .lng(dto.getLng())
                .lat(dto.getLat())
                .roadAddress(dto.getRoadAddress())
                .localAddress(dto.getLocalAddress())
                .engAddress(dto.getEngAddress())
                .category(dto.getCategory())
                .build();
    }
}
