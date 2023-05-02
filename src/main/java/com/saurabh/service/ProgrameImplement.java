package com.saurabh.service;

import com.saurabh.dto.ProgramDto;
import com.saurabh.entity.Program;
import com.saurabh.entity.RadioJockey;
import org.springframework.data.repository.query.Param;


import java.time.LocalDate;
import java.util.List;

public interface ProgrameImplement {
    public List<ProgramDto> fetchAllPrograms();

    public ProgramDto fetchProgrambyId(Long programId);

    public void addNewProgramOnJockey(Long stationId, Program program);

    public void updateProgram(Long programId, Program program);

    public void deleteProgram(Long programId);

    public List<RadioJockey> fetchAllJockey(Long programId);

    public List<Program> findProgramByStationAndDate(Long stationId, LocalDate playDate);
    public List<Program> fetchAllStationData( Long stationId);
    public List<Program> findProgramsByStationIdAndDateAndproductId(Long stationId, LocalDate playDate,Long productId);

}
