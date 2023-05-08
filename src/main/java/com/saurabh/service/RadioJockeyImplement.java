package com.saurabh.service;

import com.saurabh.dto.RadioJockeyDto;
import com.saurabh.entity.Program;
import com.saurabh.entity.RadioJockey;
import com.saurabh.entity.RadioStation;

import java.util.List;

public interface RadioJockeyImplement {
    List<RadioJockeyDto> fetchAllRadioJockey();

    RadioJockeyDto fetchRadioJockeybyId(Long jockeyId);

    void addNewRadioJockey(Long stationId, RadioJockey radioJockey);

    void updateRadioJockey(Long stationId, Long jockeyId, RadioJockey radioJockey);

    void deleteRadioJockey(Long stationId,Long jockeyId);

    RadioStation fetchAllStationAttachedwithJockey(Long jockeyId);

    List<RadioStation> fetchAllProgramDetailsAttachedwithJockey(Long jockeyId);

    List<RadioJockey> findRadioJockeysByProgramId(Long programId);

    List<RadioJockey> findJockeysByStationId(Long stationId);

    List<Program> findProgramByJockeyId(Long jockeyId);

    Program getRadioJockeyStationData(Long programId);


}