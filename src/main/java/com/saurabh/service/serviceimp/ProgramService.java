package com.saurabh.service.serviceimp;

import com.saurabh.dto.ProgramDto;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProgramService implements ProgrameImplement {
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
        Program program = programRepository.findById(programId).get();
        if (program == null) {
            throw new RadioStationNotFoundException("Given Id " + programId + " does not correspond to an available radio program");
        } else {
            ProgramDto programDto = new ProgramDto(program);
            return programDto;
        }
    }

    @Override
    public void addNewProgramOnJockey(Long stationId, Long jockeyId, Program program) {
        RadioStation radioStation = radioStationRepository.findById(stationId).get();
        RadioJockey radioJockey = radioJockeyRepository.findById(jockeyId).get();
        if (radioStation == null) {
            throw new RadioStationNotFoundException("Given Id " + stationId + " does not correspond to an available radio station");
        } else if (radioJockey == null) {
            throw new RadioJockeyNotFoundException("Given Id " + jockeyId + " does not correspond to an available radio  jockey");
        } else if (radioJockey.getWorksAt().equals(radioStation)) {
            program.setHostedByid(radioJockey);
            program.setBroadcastedOn(radioStation);
            programRepository.save(program);
        }
    }


    @Override
    public void updateProgram(Long stationId, Long jockeyId, Long programId, Program programe) {
        Program program = programRepository.findById(programId).get();
        RadioStation radioStation = radioStationRepository.findById(stationId).get();
        RadioJockey radioJockey = radioJockeyRepository.findById(jockeyId).get();
        if (radioStation == null) {
            throw new RadioStationNotFoundException("Given Id " + stationId + " does not correspond to an available radio station");
        } else if (radioJockey == null) {
            throw new RadioJockeyNotFoundException("Given Id " + jockeyId + " does not correspond to an available radio  jockey");
        } else if (program == null) {
            throw new ProgramNotFoundException("Given Id " + programId + " does not correspond to an available radio program");
        } else if (radioJockey.getWorksAt().equals(radioStation)) {
            program.setId(programId);
            program.setName(programe.getName());
            program.setBroadcastedOn(programe.getBroadcastedOn());
            program.setAdvertisements(programe.getAdvertisements());
            program.setDuration(programe.getDuration());
            program.setCategory(programe.getCategory());
            program.setEndTime(programe.getEndTime());
            program.setStartTime(programe.getStartTime());
            program.setHostedByid(programe.getHostedByid());
            program.setBroadcastedOn(radioStation);
            program.setHostedByid(radioJockey);
            programRepository.save(program);
        }
    }

    @Override
    public void deleteProgram(Long programId) {
        Program program = programRepository.findById(programId).get();
        if (program == null) {
            throw new ProgramNotFoundException("Given Id " + programId + " does not correspond to an available radio program");
        } else {
            programRepository.delete(program);
        }
    }

    //Radio jockey attached to Program.
    @Override
    public List<RadioJockey> fetchAllJockey(Long programId) {
        Program program = programRepository.findById(programId).get();
        if (program == null) {
            throw new ProgramNotFoundException("Given Id " + programId + " does not correspond to an available radio program");
        } else {
            return programRepository.findJockeysByProgramId(programId);
        }
    }
//program played on particulat staion on perticular date
    @Override
    public List<Program> findProgramByStationAndDate(Long stationId, LocalDate playDate) {
        RadioStation radioStation = radioStationRepository.findById(stationId).get();
        if (radioStation == null) {
            throw new RadioStationNotFoundException("Given Id " + stationId + " does not correspond to an available radio station");
        } else {
            return programRepository.findProgramsByStationIdAndDate(stationId, playDate);
        }
    }
//which Radio Jockey will play program on which station and when details

    @Override
    public Optional<Program> findRadioJockeyAndStationByProgramId(Long programId) {
        Program program = programRepository.findById(programId).get();

        if (program == null) {
            throw new ProgramNotFoundException("Given Id " + programId + " does not correspond to an available radio program");
        } else {
            return programRepository.findById(programId);
        }
    }
//make filters for getting data on behalf of date, stationId, programId
    @Override
    public List<Program> findProgramsByStationIdAndDateAndproductId(Long stationId, LocalDate playDate, Long programId) {
        RadioStation radioStation = radioStationRepository.findById(stationId).get();
        Program program = programRepository.findById(programId).get();
        if (radioStation == null && program == null) {
            throw new RadioStationNotFoundException("Given Id " + programId + " does not correspond to an available radio program");
        } else {
            return programRepository.findProgramsByStationIdAndDateAndproductId(stationId, playDate, programId);
        }

    }
}
