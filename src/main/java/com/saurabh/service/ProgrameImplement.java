package com.saurabh.service;

import com.saurabh.dto.ProgramDto;
import com.saurabh.entity.Program;
import com.saurabh.entity.RadioJockey;


import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ProgrameImplement {
    public List<ProgramDto> fetchAllPrograms();

    public ProgramDto fetchProgrambyId(Long programId);

    public void addNewProgramOnJockey(Long stationId,Long jockeyId ,Program program);

    public void updateProgram(Long stationId,Long programId,Long jockeyId , Program programe) ;

    public void deleteProgram(Long programId);

    public List<RadioJockey> fetchAllJockey(Long programId);

    public List<Program> findProgramByStationAndDate(Long stationId, LocalDate playDate);
    public Optional<Program> findRadioJockeyAndStationByProgramId(Long programId) ;

    public List<Program> findProgramsByStationIdAndDateAndproductId(Long stationId, LocalDate playDate,Long productId);

}
