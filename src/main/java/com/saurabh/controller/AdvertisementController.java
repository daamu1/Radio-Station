package com.saurabh.controller;

import com.saurabh.dto.AdvertisementDto;
import com.saurabh.entity.Advertisement;
import com.saurabh.entity.Program;
import com.saurabh.service.serviceimp.AdvertisementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v2")
public class AdvertisementController {
    @Autowired
    private AdvertisementService advertisementService;

    @GetMapping("/advertisements")
    public List<AdvertisementDto> fetchAllAdvertisement() {
        return advertisementService.fetchAllAdvertisement();
    }

    @GetMapping("/advertisements/{addId}")
    public AdvertisementDto fetchAdvertisementbyId(@PathVariable Long addId) {
        return advertisementService.fetchAdvertisementbyId(addId);
    }

    @PostMapping("/{programId}/advertisements")
    public void addNewAdvertisement(@PathVariable Long programId, @RequestBody Advertisement advertisement) {
        advertisementService.addNewAdvertisement(programId, advertisement);
    }

    @PutMapping("/{programId}/advertisements/{addId}")
    public void updateAdvertisement(@PathVariable Long addId, @PathVariable Long programId, @RequestBody Advertisement advertisement) {
        advertisementService.updateAdvertisement(addId, programId, advertisement);
    }

    @DeleteMapping("/advertisements/{addId}")
    public void deleteAdvertisement(@PathVariable Long addId) {
        advertisementService.deleteAdvertisement(addId);
    }

    @GetMapping("/advertisements/{advertisementId}/details")
    public Advertisement fetchAdvertisementAllDetailsbyId(@PathVariable Long advertisementId) {
        return advertisementService.fetchAdvertisementAllDetailsbyId(advertisementId);
    }
}
