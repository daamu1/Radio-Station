package com.saurabh.controller;

import com.saurabh.dto.RadioStationDto;
import com.saurabh.entity.Advertisement;
import com.saurabh.entity.RadioJockey;
import com.saurabh.entity.RadioStation;
import com.saurabh.service.serviceimp.RadioStationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v2")
public class RadioStationController {
    @Autowired
    private RadioStationService radioStationService;

    public RadioStationController(RadioStationService radioStationService) {
        this.radioStationService = radioStationService;
    }

    @GetMapping("/radiostations")
    public List<RadioStationDto> fetchAllRadioStation() {
        return radioStationService.fetchAllRadioStation();
    }

    @GetMapping("/radiostations/{sId}")
    public RadioStationDto fetchRadioStationbyId(@PathVariable Long sId) {
        return radioStationService.fetchRadioStationbyId(sId);
    }

    @PostMapping("/radiostations")
    public void addNewRadioStation(@RequestBody RadioStation radioStation) {
        radioStationService.addNewRadioStation(radioStation);
    }

    @PutMapping("/radiostations/{sId}")
    public void updateRadioStation(@PathVariable Long sId, @RequestBody RadioStation radioStation) {
        radioStationService.updateRadioStation(sId, radioStation);
    }

    @DeleteMapping("/radiostation/{sId}")
    public void deleteRadioStation(@PathVariable Long sId) {
        radioStationService.deleteRadioStation(sId);
    }

    @GetMapping("/radiostation/{stationId}")
    List<Object[]> findAllDetailsForStation(@PathVariable Long stationId) {
        return radioStationService.findAllDetailsForStation(stationId);
    }
    @GetMapping("/radiostation")
    List<Object[]> findProgramDetailsByDate(@RequestParam LocalDate programDate) {
        return radioStationService.findProgramDetailsByDate(programDate);
    }


    }