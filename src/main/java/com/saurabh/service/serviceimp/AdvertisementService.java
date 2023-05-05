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
        Advertisement advertisement = advertisementRepository.findById(advertisementId).get();
        AdvertisementDto advertisementDto = new AdvertisementDto(advertisement);
        if (advertisement == null) {
            throw new AdvertisementNotFoundException("Given Id " + advertisementId + " does not correspond to an available radio program");
        }
        return advertisementDto;
    }

    //which advertisement will be played in between of program and on which station
    @Override
    public Advertisement fetchAdvertisementAllDetailsbyId(Long advertisementId) {
        Advertisement advertisement = advertisementRepository.findById(advertisementId).get();
        if (advertisement == null) {
            throw new AdvertisementNotFoundException("Given Id " + advertisementId + " does not correspond to an available radio program");
        }
        return advertisement;
    }

    @Override
    public void addNewAdvertisement(Long programId, Advertisement advertisement) {
        Program program = programRepository.findById(programId).get();
        if (program == null) {
            throw new ProgramNotFoundException("Given Id " + programId + " does not correspond to an available radio program");
        } else {
            //program.setAdvertisements((List<Advertisement>) advertisement);
            program.addAdd(advertisement);
            programRepository.save(program);
        }
    }

    @Override
    public void updateAdvertisement(Long programId, Long advertisementId, Advertisement advertisement) {
        Advertisement advertisemen = advertisementRepository.findById(advertisementId).get();
        Program program = programRepository.findById(programId).get();
        if (advertisemen == null) {
            throw new AdvertisementNotFoundException("Given Id " + advertisementId + " does not correspond to an available radio program");
        } else if (program == null) {
            throw new ProgramNotFoundException("Given Id " + programId + " does not correspond to an available radio program");
        } else if(advertisemen.getPlayedDuring().equals(program)){
            advertisemen.setAdvertiserName(advertisement.getAdvertiserName());
            advertisemen.setCost(advertisement.getCost());
            advertisemen.setPlayedDuring(advertisement.getPlayedDuring());
            advertisemen.setAddType(advertisement.getAddType());
            advertisemen.setAddDuration(advertisement.getAddDuration());
//            advertisemen.setgetPlayedDuring(advertisemen.getPlayedDuring());
//            advertisemen.setPlayedDuring(advertisemen.getPlayedDuring());
            advertisemen.setPlayedDuring(program);
            advertisementRepository.save(advertisemen);
        }
        else {
            throw  new RuntimeException(" Something went wrong ");
        }
    }

    @Override
    public void deleteAdvertisement(Long advertisementId) {
        Advertisement advertisement = advertisementRepository.findById(advertisementId).get();
        if (advertisement == null) {
            throw new AdvertisementNotFoundException("Given Id " + advertisementId + " does not correspond to an available radio program");
        }
        advertisementRepository.deleteById(advertisementId);
    }
}
