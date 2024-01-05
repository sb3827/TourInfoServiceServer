package com.dot.tour_info_service_server.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BoardAllDTO {
    private List<BoardInfoDTO> boardInfoDTOS;
    private List<PlaceDTO> placeDTOS;
    private List<ImageDTO> imageDTOS;
    private List<BoardLikeCntDTO> boardLikeNameDTOS;
}
