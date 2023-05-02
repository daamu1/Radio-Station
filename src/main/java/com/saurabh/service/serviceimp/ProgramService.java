package com.saurabh.service.serviceimp;

import com.saurabh.dto.ProgramDto;
import com.saurabh.entity.Program;
import com.saurabh.entity.RadioJockey;
import com.saurabh.entity.RadioStation;
import com.saurabh.exception.RadioJockeyNotFoundException;
import com.saurabh.repository.ProgramRepository;
import com.saurabh.repository.RadioJockeyRepository;
import com.saurabh.repository.RadioStationRepository;
import com.saurabh.service.ProgrameImplement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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
        ProgramDto programDto=new ProgramDto(program);
        if (program == null) {
            throw new RuntimeException("Given Id " + programId + "there are no Program Available ");
        }
        return programDto;
    }

    @Override
    public void addNewProgramOnJockey(Long stationId, Program program) {
        RadioStation radioStation = radioStationRepository.findById(stationId).get();
        if (radioStation == null) {
            throw new RadioJockeyNotFoundException("Given Id " + stationId + "there are not Radio Jockey are Available ");
        } else {
            program.setBroadcastedOn(radioStation);
        programRepository.save(program);
        }
    }

    @Override
    public void updateProgram(Long programId, Program programe) {
        Program program = programRepository.findById(programId).get();
        if (program == null) {
            throw new RuntimeException("Given Id " + programId + "there are no Program Available ");
        } else {
            program.setId(programId);
            program.setName(programe.getName());
            program.setBroadcastedOn(programe.getBroadcastedOn());
            program.setAdvertisements(programe.getAdvertisements());
            program.setDuration(programe.getDuration());
            program.setCategory(programe.getCategory());
            program.setEndTime(programe.getEndTime());
            program.setStartTime(programe.getStartTime());
            program.setHostedByid(programe.getHostedByid());
            programRepository.save(programe);
        }
    }

    @Override
    public void deleteProgram(Long programId) {
        Program program = programRepository.findById(programId).get();
        if (program == null) {
            throw new RuntimeException("Given Id " + programId + "there are no Program Available ");
        } else {
            programRepository.delete(program);
        }
    }

    @Override
    public List<RadioJockey> fetchAllJockey(Long programId) {
        Program program = programRepository.findById(programId).get();
        if (program == null) {
            throw new RuntimeException("Given Id " + programId + "there are no Program Available ");
        } else {
            return programRepository.findJockeysByProgramId(programId);
        }
    }

    @Override
    public List<Program> findProgramByStationAndDate(Long stationId, LocalDate playDate) {

//        List<Program> programs=new ArrayList<Program>();
//        programs.add(new Program());
        RadioStation radioStation = radioStationRepository.findById(stationId).get();
        if (radioStation == null) {
            throw new RuntimeException("Given Id " + stationId + "there are not Radio Station are Available ");
        } else {
            return programRepository.findProgramsByStationIdAndDate(stationId, playDate);
        }
    }

    @Override
    public List<Program> fetchAllStationData(Long stationId) {
        RadioStation radioStation = radioStationRepository.findById(stationId).get();

        if (radioStation == null) {
            throw new RuntimeException("Given Id \" + stationId + \"there are not Radio Station are Available ");
        } else {
            return programRepository.fetchAllStationData(stationId);
        }
    }

    @Override
    public List<Program> findProgramsByStationIdAndDateAndproductId(Long stationId, LocalDate playDate, Long programId) {
        RadioStation radioStation = radioStationRepository.findById(stationId).get();
        Program program = programRepository.findById(programId).get();
        if (radioStation == null && program == null) {
            throw new RuntimeException("Some thing went Wrong ");
        } else {
            return programRepository.findProgramsByStationIdAndDateAndproductId(stationId, playDate, programId);
        }

    }
}
