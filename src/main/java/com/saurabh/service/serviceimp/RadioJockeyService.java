package com.saurabh.service.serviceimp;

import com.saurabh.dto.RadioJockeyDto;
import com.saurabh.entity.Program;
import com.saurabh.entity.RadioJockey;
import com.saurabh.entity.RadioStation;
import com.saurabh.exception.ProgramNotFoundException;
import com.saurabh.exception.RadioJockeyNotFoundException;
import com.saurabh.exception.RadioStationNotFoundException;
import com.saurabh.repository.ProgramRepository;
import com.saurabh.repository.RadioJockeyRepository;
import com.saurabh.repository.RadioStationRepository;
import com.saurabh.service.RadioJockeyImplement;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@NoArgsConstructor
public class RadioJockeyService implements RadioJockeyImplement {
    private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    @Autowired
    private RadioJockeyRepository radioJockeyRepository;
    @Autowired
    private RadioStationRepository radioStationRepository;

    @Autowired
    private ProgramRepository programRepository;

    @Override
    public List<RadioJockeyDto> fetchAllRadioJockey() {
        List<RadioJockey> radioJockeys = (List<RadioJockey>) radioJockeyRepository.findAll();
        List<RadioJockeyDto> radioJockeyDtos = new ArrayList<>();
        for (RadioJockey radioJockey : radioJockeys) {
            RadioJockeyDto radioJockeyDto = new RadioJockeyDto(radioJockey);
            radioJockeyDtos.add(radioJockeyDto);
        }
        return radioJockeyDtos;
    }

    @Override
    public RadioJockeyDto fetchRadioJockeybyId(Long jockeyId) {
        Optional<RadioJockey> optionalRadioJockey = radioJockeyRepository.findById(jockeyId);
        if (!optionalRadioJockey.isPresent()) {
            throw new RadioJockeyNotFoundException("Given Id " + jockeyId + " does not correspond to an available radio jockey");
        }
        RadioJockey radioJockey = optionalRadioJockey.get();
        RadioJockeyDto radioJockeyDto = new RadioJockeyDto(radioJockey);
        return radioJockeyDto;
    }


    @Override
    public void addNewRadioJockey(Long stationId, RadioJockey radioJockey) throws RadioJockeyNotFoundException {
        Optional<RadioStation> radioStationOptional = radioStationRepository.findById(stationId);
        if (radioStationOptional.isEmpty()) {
            throw new RadioJockeyNotFoundException("Given Id " + stationId + " does not correspond to an available radio station");
        } else {
            RadioStation radioStation = radioStationOptional.get();
            radioJockey.setWorksAt(radioStation);
            radioStation.getRadioJockeys().add(radioJockey);
            radioStationRepository.save(radioStation);
        }
    }


    @Override
    @Transactional
    public void updateRadioJockey(Long stationId, Long jockeyId, RadioJockey radioJockey) {
        RadioStation radioStation = radioStationRepository.findById(stationId).orElseThrow(() -> new RadioStationNotFoundException("Given Id " + stationId + " does not correspond to an available radio station"));
        RadioJockey radioJockey1 = radioJockeyRepository.findById(jockeyId).orElseThrow(() -> new RadioJockeyNotFoundException("Given Id " + jockeyId + " does not correspond to an available radio jockey"));
        if (!radioJockey1.getWorksAt().equals(radioStation)) {
            throw new RuntimeException("Radio jockey does not work at the given radio station");
        }
        radioJockey1.setName(radioJockey.getName());
        radioJockey1.setGender(radioJockey.getGender());
        radioJockey1.setContactEmail(radioJockey.getContactEmail());
        radioJockey1.setWorksAt(radioJockey.getWorksAt());
        radioJockey1.setContactPhone(radioJockey.getContactPhone());
        radioJockey1.setDateOfBirth(radioJockey.getDateOfBirth());
        radioJockey1.setWorksAt(radioStation);
        radioJockeyRepository.save(radioJockey1);
    }


    @Override
    @Transactional
    public void deleteRadioJockey(Long stationId, Long jockeyId) {
        System.out.println("------------------>" + stationId + "--------->" + jockeyId);
        RadioStation radioStation = radioStationRepository.findById(stationId).orElseThrow(() -> new RadioStationNotFoundException("Given Id " + stationId + " does not correspond to an available radio station"));
        System.out.println("------------------>" + radioStation.toString());
        RadioJockey radioJockey1 = radioJockeyRepository.findById(jockeyId).orElseThrow(() -> new RadioJockeyNotFoundException("Given Id " + jockeyId + " does not correspond to an available radio jockey"));

        System.out.println("------------------>" + radioJockey1);
        if (!radioJockey1.getWorksAt().equals(radioStation)) {
            throw new RuntimeException("Radio jockey does not work at the given radio station");
        }
        radioJockeyRepository.deleteById(jockeyId);
    }


    //Radio jockeys attached to  station
    @Override
    @Transactional
    public RadioStation fetchAllStationAttachedwithJockey(Long jockeyId) {
        Optional<RadioJockey> radioJockey = radioJockeyRepository.findById(jockeyId);
        if (!radioJockey.isPresent()) {
            throw new RadioJockeyNotFoundException("Given Id " + jockeyId + " does not correspond to an available radio jockey");
        }
        return radioJockeyRepository.findStationsByJockeyId(jockeyId);
    }


    @Override
    @Transactional
    public List<RadioStation> fetchAllProgramDetailsAttachedwithJockey(Long jockeyId) {
        Optional<RadioJockey> radioJockey = radioJockeyRepository.findById(jockeyId);
        if (!radioJockey.isPresent()) {
            throw new RadioJockeyNotFoundException("Given Id " + jockeyId + " does not correspond to an available radio jockey");
        }
        return (List<RadioStation>) radioJockeyRepository.findStationsByJockeyId(jockeyId);
    }


    @Override
    public List<RadioJockey> findRadioJockeysByProgramId(Long programId) {
        Optional<Program> programOptional = programRepository.findById(programId);
        if (programOptional.isPresent()) {
            Program program = programOptional.get();
            return programRepository.findJockeysByProgramId(programId);
        } else {
            throw new ProgramNotFoundException("Program not found with id: " + programId);
        }
    }


    @Override
    public List<RadioJockey> findJockeysByStationId(Long stationId) {
        Optional<RadioStation> optionalRadioStation = radioStationRepository.findById(stationId);
        RadioStation radioStation = optionalRadioStation.orElseThrow(() -> new RadioStationNotFoundException("Given Id " + stationId + " does not correspond to an available radio station"));
        try {
            List<RadioJockey> jockeys = radioJockeyRepository.findJockeysByStationId(stationId);
            return jockeys;
        } catch (Exception e) {
            throw new RadioJockeyNotFoundException("Error occurred while fetching jockeys for radio station with id: " + stationId + "   " + e);
        }
    }


    //   Programs attached to Radio jockeys
    @Override
    public List<Program> findProgramByJockeyId(Long jockeyId) {
        Optional<RadioJockey> radioJockey = radioJockeyRepository.findById(jockeyId);
        if (radioJockey.isEmpty()) {
            throw new RadioJockeyNotFoundException("Given Id " + jockeyId + " does not correspond to an available radio jockey");
        } else {
            return radioJockeyRepository.findProgramByJockeyId(jockeyId);
        }
    }


    @Override
    public Program getRadioJockeyStationData(Long programId) {
        Optional<Program> program = programRepository.findById(programId);
        if (!program.isPresent()) {
            throw new RadioJockeyNotFoundException("Given Id " + programId + " does not correspond to an available Program");
        } else {
            return program.get();
        }
    }


}
