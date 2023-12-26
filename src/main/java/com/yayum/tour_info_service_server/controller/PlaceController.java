package com.yayum.tour_info_service_server.controller;

import com.yayum.tour_info_service_server.dto.PlaceDTO;
import com.yayum.tour_info_service_server.entity.Category;
import com.yayum.tour_info_service_server.entity.Place;
import com.yayum.tour_info_service_server.service.PlaceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@RestController
@RequestMapping("/place")
@Log4j2
@RequiredArgsConstructor
public class PlaceController {

    private final PlaceService placeService;

    // @GetMapping ("/register")
    // public void register(){
    //     log.info("register get.............");
    // }


    @PostMapping("/register")
    public ResponseEntity<Long> registerPlace(@RequestBody PlaceDTO placeDTO){
        log.info("registerPlace: " + placeDTO);
        Long pno = placeService.registerPlace(placeDTO);
        return new ResponseEntity<>(pno, HttpStatus.OK);
    }

    @GetMapping(value = "")
    public ResponseEntity<List<Long>> findPlace(@RequestParam(value="filter") Category filter, @RequestParam(value = "search") String search ){
        log.info("findPlace...... filter :  " + filter + " search : " + search);
        List<Long> placeList = placeService.findPlace(filter, search);
        return new ResponseEntity<>(placeList, HttpStatus.OK);
    }

    @DeleteMapping(value="/delete/{pno}")
    public ResponseEntity<Long> removePlace(@PathVariable("pno") Long pno){
        log.info("delete..............");
        log.info("deletenum: " + pno);
        placeService.removePlace(pno);
        return new ResponseEntity<>(pno, HttpStatus.OK);
    }
}
