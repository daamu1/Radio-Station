package com.saurabh.controller;

import com.saurabh.dto.AdvertisementDto;
import com.saurabh.entity.Advertisement;
import com.saurabh.service.serviceimp.AdvertisementService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public void updateAdvertisement(@PathVariable Long programId, @PathVariable Long addId, @RequestBody Advertisement advertisement) {
        advertisementService.updateAdvertisement(programId, addId, advertisement);
    }

    @DeleteMapping("/{programId}/advertisements/{addId}")
    public void deleteAdvertisement(@PathVariable Long programId, @PathVariable Long addId) {
        advertisementService.deleteAdvertisement(programId, addId);
    }

    //    which advertisement will be played in between of program and on which station
    @GetMapping("/advertisements/{advertisementId}/details")
    public Advertisement fetchAdvertisementAllDetailsbyId(@PathVariable Long advertisementId) {
        return advertisementService.fetchAdvertisementAllDetailsbyId(advertisementId);
    }

    @GetMapping("/advertisements/detailsBasedOnName")
    List<Advertisement> listOfAdvertisementBasedOnListOfIds(@RequestParam String addType, @RequestParam String advertiserName) {
        return advertisementService.listOfAdvertisementBasedOnListOfIds(addType, advertiserName);
    }

}