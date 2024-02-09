package com.dot.tour_info_service_server.controller;

import com.dot.tour_info_service_server.dto.PlaceDTO;
import com.dot.tour_info_service_server.dto.request.place.ModifyNameRequestDTO;
import com.dot.tour_info_service_server.dto.request.place.RegistPlaceRequestDTO;
import com.dot.tour_info_service_server.entity.Category;
import com.dot.tour_info_service_server.service.place.PlaceService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/place")
@Log4j2
@RequiredArgsConstructor
public class PlaceController {

    private final PlaceService placeService;

    // authenticated
    @PostMapping(value = "/register")
    public ResponseEntity<Map<String, Long>> registerPlace(@RequestBody RegistPlaceRequestDTO placeDTO){

        if(placeDTO.getName()==null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Map<String, Long> result = new HashMap<>();
        Long pno = placeService.registerPlace(placeDTO);
        result.put("pno", pno);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    // permit all
    @GetMapping(value = "")
    public ResponseEntity<List<PlaceDTO>> findPlace(@RequestParam(value="filter") String filter,
                                                    @RequestParam(value = "search") String search,
                                                    @RequestParam int page){

        Category category=null;
        if(!filter.isEmpty()) {
            category = Category.valueOf(filter);
        }
        List<PlaceDTO> placeList = placeService.searchPlace(category, search, page);
        return new ResponseEntity<>(placeList, HttpStatus.OK);
    }

    // 지역별 방문수
    // permit all
    @GetMapping(value="/placecount")
    public ResponseEntity<Map<String, Object>> getPlaceCount(@Valid @RequestParam(value="mno")
                                                                 @NotNull(message = "mno cannot be null") Long mno){
        Map<String, Object> result = placeService.getPlaceCount(mno);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    //admin
    @PutMapping(value = "/modify")
    public ResponseEntity<Void> modifyPlace(@Valid @RequestParam("pno")
                                                @NotNull(message = "pno cannot be null") Long pno,
                                            @RequestBody ModifyNameRequestDTO nameRequestDTO) throws BadRequestException {
        if(!placeService.modifyPlace(pno, nameRequestDTO.getName()))
            throw new BadRequestException("잘못된 요청 입니다.");

        return new ResponseEntity<>(HttpStatus.OK);
    }

    // admin
    @DeleteMapping(value="/delete", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Long>> removePlace(@Valid @RequestParam("pno")
                                                             @NotNull(message = "pno cannot be null") Long pno){

        Map<String, Long> result = new HashMap<>();
        placeService.removePlace(pno);
        result.put("pno", pno);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

}
