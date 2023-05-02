package com.saurabh.controller;

import com.saurabh.dto.RadioJockeyDto;
import com.saurabh.entity.Program;
import com.saurabh.entity.RadioJockey;
import com.saurabh.entity.RadioStation;
import com.saurabh.service.serviceimp.RadioJockeyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v2")
public class RadioJockeyController {
    @Autowired
    private RadioJockeyService radioJockeyService;

    @GetMapping("/radiojockeys")
    public List<RadioJockeyDto> fetchAllRadioJockey() {

        return radioJockeyService.fetchAllRadioJockey();
    }

    @GetMapping("/radiojockeys/{jokeyId}")
    public RadioJockeyDto fetchRadioJockeybyId(@PathVariable Long jokeyId) {
        return radioJockeyService.fetchRadioJockeybyId(jokeyId);
    }

    @PostMapping("{stationId}/programs/{programId}/radiojockeys")
    public void addNewRadioJockey(@PathVariable Long stationId, @RequestBody RadioJockey radioJockey, @PathVariable Long programId) {
        radioJockeyService.addNewRadioJockey(stationId, programId, radioJockey);
    }

    @PutMapping("/radiojockeys/{jockeyId}")
    public void updateRadioJockey(@PathVariable Long jockeyId, @RequestBody RadioJockey radioJockey) {
        radioJockeyService.updateRadioJockey(jockeyId, radioJockey);
    }

    @DeleteMapping("/radiojockeys/{jockeyId}")
    public void deleteRadioJockeyt(@PathVariable Long jockeyId) {
        radioJockeyService.deleteRadioJockeyt(jockeyId);
    }

    @GetMapping("/radiojockeys/{jockeyId}/stations")
    public List<RadioStation> fetchAllStationAttachedwithJockey(@PathVariable Long jockeyId) {
        return radioJockeyService.fetchAllStationAttachedwithJockey(jockeyId);
    }

    @GetMapping("/radiojockeys/{jockeyId}/program")
    public List<RadioStation> fetchAllProgramAttachedwithJockey(@PathVariable Long jockeyId) {
        return radioJockeyService.fetchAllProgramDetailsAttachedwithJockey(jockeyId);
    }

    @GetMapping("/programs/{programId}/radiojockeys")
    public List<RadioJockey> findRadioJockeysByProgramId(@PathVariable Long programId) {
        return radioJockeyService.findRadioJockeysByProgramId(programId);
    }

    @GetMapping("/stations/{stationId}/radiojockeys")
    public List<RadioJockey> findJockeysByStationId(@PathVariable Long stationId) {
        return radioJockeyService.findJockeysByStationId(stationId);
    }

    @GetMapping("/radiojockeys/{jockeyId}/data")
    public RadioJockey getRadioJockeyProgramStationData(@PathVariable Long jockeyId) {
        return radioJockeyService.getRadioJockeyProgramStationData(jockeyId);
    }
}
