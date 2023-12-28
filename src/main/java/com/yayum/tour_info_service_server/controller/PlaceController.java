package com.yayum.tour_info_service_server.controller;

import com.yayum.tour_info_service_server.dto.PlaceDTO;
import com.yayum.tour_info_service_server.entity.Category;
import com.yayum.tour_info_service_server.entity.Place;
import com.yayum.tour_info_service_server.service.PlaceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/place")
@Log4j2
@RequiredArgsConstructor
public class PlaceController {

    private final PlaceService placeService;


    @PostMapping(value = "/register")
    public ResponseEntity<Map<String, Long>> registerPlace(@RequestBody PlaceDTO placeDTO){
        log.info("registerPlace: " + placeDTO);
        Map<String, Long> result = new HashMap<>();
        Long pno = placeService.registerPlace(placeDTO);
        result.put("pno", pno);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }


    @GetMapping(value = "")
    public ResponseEntity<List<Long>> findPlace(@RequestParam(value="filter") Category filter, @RequestParam(value = "search") String search ){
        log.info("findPlace...... filter :  " + filter + " search : " + search);
        List<Long> placeList = placeService.findPlace(filter, search);
        return new ResponseEntity<>(placeList, HttpStatus.OK);
    }

    @DeleteMapping(value="/delete/{pno}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Long>> removePlace(@PathVariable Long pno){
        log.info("delete..............");
        Map<String, Long> result = new HashMap<>();
        placeService.removePlace(pno);
        result.put("pno", pno);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
