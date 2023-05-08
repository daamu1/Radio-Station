package com.saurabh.service.serviceimp;

import com.saurabh.dto.AdvertisementDto;
import com.saurabh.entity.Advertisement;
import com.saurabh.entity.Program;
import com.saurabh.exception.AdvertisementNotFoundException;
import com.saurabh.exception.ProgramNotFoundException;
import com.saurabh.repository.AdvertisementRepository;
import com.saurabh.repository.ProgramRepository;
import com.saurabh.service.AdvertisementImplement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AdvertisementService implements AdvertisementImplement {
    @Autowired
    private AdvertisementRepository advertisementRepository;
    @Autowired
    private ProgramRepository programRepository;

    @Override
    public List<AdvertisementDto> fetchAllAdvertisement() {
        List<Advertisement> advertisements = (List<Advertisement>) advertisementRepository.findAll();
        List<AdvertisementDto> advertisementDtos = new ArrayList<>();
        for (Advertisement advertisement : advertisements) {
            AdvertisementDto advertisementDto = new AdvertisementDto(advertisement);
            advertisementDtos.add(advertisementDto);
        }
        return advertisementDtos;
    }

    @Override
    public AdvertisementDto fetchAdvertisementbyId(Long advertisementId) {
        Advertisement advertisement = advertisementRepository.findById(advertisementId)
                .orElseThrow(() -> new AdvertisementNotFoundException("Given Id " + advertisementId + " does not correspond to an available radio advertisement"));
        return new AdvertisementDto(advertisement);
    }


    //which advertisement will be played in between of program and on which station
    @Override
    public Advertisement fetchAdvertisementAllDetailsbyId(Long advertisementId) {
        Advertisement advertisement = advertisementRepository.findById(advertisementId)
                .orElseThrow(() -> new AdvertisementNotFoundException("Given Id " + advertisementId + " does not correspond to an available advertisement"));
        return advertisement;
    }

    @Override
    public void addNewAdvertisement(Long programId, Advertisement advertisement) {
        Program program = programRepository.findById(programId).orElseThrow(() -> new ProgramNotFoundException("Given Id " + programId + " does not correspond to an available radio program"));
        program.addAdd(advertisement);
        programRepository.save(program);
    }


    @Override
    public void updateAdvertisement(Long programId, Long advertisementId, Advertisement advertisement) {
        Advertisement advertisemen = advertisementRepository.findById(advertisementId)
                .orElseThrow(() -> new AdvertisementNotFoundException("Given Id " + advertisementId + " does not correspond to an available advertisement"));
        Program program = programRepository.findById(programId)
                .orElseThrow(() -> new ProgramNotFoundException("Given Id " + programId + " does not correspond to an available radio program"));
        if(!advertisemen.getPlayedDuring().equals(program)){
            throw new RuntimeException("Advertisement with Id " + advertisementId + " does not belong to program with Id " + programId);
        } else {
            advertisemen.setAdvertiserName(advertisement.getAdvertiserName());
            advertisemen.setCost(advertisement.getCost());
            advertisemen.setPlayedDuring(advertisement.getPlayedDuring());
            advertisemen.setAddType(advertisement.getAddType());
            advertisemen.setAddDuration(advertisement.getAddDuration());
            advertisemen.setPlayedDuring(program);
            advertisementRepository.save(advertisemen);
        }
    }


    @Override
    public void deleteAdvertisement(Long programId,Long advertisementId) {
        Advertisement advertisemen = advertisementRepository.findById(advertisementId)
                .orElseThrow(() -> new AdvertisementNotFoundException("Given Id " + advertisementId + " does not correspond to an available advertisement"));
        Program program = programRepository.findById(programId)
                .orElseThrow(() -> new ProgramNotFoundException("Given Id " + programId + " does not correspond to an available radio program"));
        if(!advertisemen.getPlayedDuring().equals(program)){
            throw new RuntimeException("Advertisement with Id " + advertisementId + " does not belong to program with Id " + programId);
        } else{
            advertisementRepository.deleteById(advertisementId);
        }
    }

}
