package com.saurabh.service;

import com.saurabh.dto.AdvertisementDto;
import com.saurabh.entity.Advertisement;
import org.springframework.web.bind.annotation.DeleteMapping;

import java.util.List;

public interface AdvertisementImplement {
    public List<AdvertisementDto> fetchAllAdvertisement();

    public AdvertisementDto fetchAdvertisementbyId(Long advertisementId);
    public Advertisement fetchAdvertisementAllDetailsbyId(Long advertisementId);

    public void addNewAdvertisement(Long programId,Advertisement advertisement);

    public void updateAdvertisement(Long programId,Long advertisementId,Advertisement advertisement);

    public void deleteAdvertisement(Long programId,Long advertisementId);
}
