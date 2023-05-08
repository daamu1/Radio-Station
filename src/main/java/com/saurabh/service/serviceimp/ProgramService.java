package com.saurabh.service.serviceimp;

import com.saurabh.dto.ProgramDto;
import com.saurabh.entity.Advertisement;
import com.saurabh.entity.Program;
import com.saurabh.entity.RadioJockey;
import com.saurabh.entity.RadioStation;
import com.saurabh.exception.ProgramNotFoundException;
import com.saurabh.exception.RadioJockeyNotFoundException;
import com.saurabh.exception.RadioStationNotFoundException;
import com.saurabh.repository.ProgramRepository;
import com.saurabh.repository.RadioJockeyRepository;
import com.saurabh.repository.RadioStationRepository;
import com.saurabh.service.ProgrameImplement;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class ProgramService implements ProgrameImplement {
    @Autowired
    private EntityManager entityManager;
    @Autowired
    private ProgramRepository programRepository;
    @Autowired
    private RadioJockeyRepository radioJockeyRepository;
    @Autowired
    private RadioStationRepository radioStationRepository;

    @Override
    public List<ProgramDto> fetchAllPrograms() {
        List<Program> programs = (List<Program>) programRepository.findAll();
        List<ProgramDto> programDtos = new ArrayList<>();
        for (Program program : programs) {
            ProgramDto programDto = new ProgramDto(program);
            programDtos.add(programDto);
        }
        return programDtos;
    }

    @Override
    public ProgramDto fetchProgrambyId(Long programId) {
        try {
            Program program = programRepository.findById(programId).get();
            ProgramDto programDto = new ProgramDto(program);
            return programDto;
        } catch (NoSuchElementException ex) {
            throw new RadioStationNotFoundException("Given Id " + programId + " does not correspond to an available radio program");
        }
    }


    @Override
    public void addNewProgramOnJockey(Long stationId, Long jockeyId, Program program) {
        RadioStation radioStation = radioStationRepository.findById(stationId).orElseThrow(() -> new RadioStationNotFoundException("Given Id " + stationId + " does not correspond to an available radio station"));
        RadioJockey radioJockey = radioJockeyRepository.findById(jockeyId).orElseThrow(() -> new RadioJockeyNotFoundException("Given Id " + jockeyId + " does not correspond to an available radio jockey"));
        if (!radioJockey.getWorksAt().equals(radioStation)) {
            throw new RuntimeException("The radio jockey with ID " + jockeyId + " does not work at the radio station with ID " + stationId);
        }
        program.setHostedByid(radioJockey);
        program.setBroadcastedOn(radioStation);
        programRepository.save(program);
    }


    @Override
    public void updateProgram(Long stationId, Long jockeyId, Long programId, Program programe) {
        Program program = programRepository.findById(programId).orElseThrow(() -> new ProgramNotFoundException("Given Id " + programId + " does not correspond to an available radio program"));
        RadioStation radioStation = radioStationRepository.findById(stationId).orElseThrow(() -> new RadioStationNotFoundException("Given Id " + stationId + " does not correspond to an available radio station"));
        RadioJockey radioJockey = radioJockeyRepository.findById(jockeyId).orElseThrow(() -> new RadioJockeyNotFoundException("Given Id " + jockeyId + " does not correspond to an available radio jockey"));
        if (!radioJockey.getWorksAt().equals(radioStation)) {
            throw new RuntimeException("Radio jockey with Id " + jockeyId + " does not work at radio station with Id " + stationId);
        }
        program.setId(programId);
        program.setName(programe.getName());
        program.setBroadcastedOn(programe.getBroadcastedOn());
        program.setAdvertisements(programe.getAdvertisements());
        program.setDuration(programe.getDuration());
        program.setPlayDate(programe.getPlayDate());
        program.setCategory(programe.getCategory());
        program.setEndTime(programe.getEndTime());
        program.setStartTime(programe.getStartTime());
        program.setHostedByid(programe.getHostedByid());
        program.setBroadcastedOn(radioStation);
        program.setHostedByid(radioJockey);
        programRepository.save(program);
    }

    @Override
    @Transactional
    @Modifying
    public void deleteProgram(Long stationId, Long jockeyId, Long programId) {
        Program program = programRepository.findById(programId).orElseThrow(() -> new ProgramNotFoundException("Given Id " + programId + " does not correspond to an available radio program"));
        RadioStation radioStation = radioStationRepository.findById(stationId).orElseThrow(() -> new RadioStationNotFoundException("Given Id " + stationId + " does not correspond to an available radio station"));
        RadioJockey radioJockey = radioJockeyRepository.findById(jockeyId).orElseThrow(() -> new RadioJockeyNotFoundException("Given Id " + jockeyId + " does not correspond to an available radio jockey"));
        if (!radioJockey.getWorksAt().equals(radioStation)) {
            throw new RuntimeException("Radio jockey with Id " + jockeyId + " does not work at radio station with Id " + stationId);
        }
        program.setHostedByid(null);
        program.setBroadcastedOn(null);
            List<Advertisement> advertisements = program.getAdvertisements();
            for (Advertisement advertisement : advertisements) {
                advertisement.setPlayedDuring(null);
                entityManager.merge(advertisement);
            }
            programRepository.deleteProgram(programId);

    }


    //Radio jockey attached to Program.
    @Override
    public List<RadioJockey> fetchAllJockey(Long programId) {
        Program program = programRepository.findById(programId).orElseThrow(() -> new ProgramNotFoundException("Given Id " + programId + " does not correspond to an available radio program"));
        return programRepository.findJockeysByProgramId(programId);
    }

    //program played on particulat staion on perticular date
    @Override
    public List<Program> findProgramByStationAndDate(Long stationId, LocalDate playDate) {
        Optional<RadioStation> radioStation = radioStationRepository.findById(stationId);
        if (!radioStation.isPresent()) {
            throw new RadioStationNotFoundException("Given Id " + stationId + " does not correspond to an available radio station");
        } else {
            return programRepository.findProgramsByStationIdAndDate(stationId, playDate);
        }
    }

    //which Radio Jockey will play program on which station and when details
    @Override
    public Optional<Program> findRadioJockeyAndStationByProgramId(Long programId) {
        Optional<Program> programOptional = programRepository.findById(programId);

        if (programOptional.isEmpty()) {
            throw new ProgramNotFoundException("Given Id " + programId + " does not correspond to an available radio program");
        } else {
            return programOptional;
        }
    }

    //make filters for getting data on behalf of date, stationId, programId
    @Override
    public List<Program> findProgramsByStationIdAndDateAndproductId(Long stationId, LocalDate playDate, Long programId) {
        Optional<RadioStation> radioStation = radioStationRepository.findById(stationId);
        Optional<Program> program = programRepository.findById(programId);

        if (!radioStation.isPresent()) {
            throw new RadioStationNotFoundException("Given Id " + stationId + " does not correspond to an available radio station");
        }

        if (!program.isPresent()) {
            throw new ProgramNotFoundException("Given Id " + programId + " does not correspond to an available radio program");
        }

        return programRepository.findProgramsByStationIdAndDateAndproductId(stationId, playDate, programId);
    }

}
