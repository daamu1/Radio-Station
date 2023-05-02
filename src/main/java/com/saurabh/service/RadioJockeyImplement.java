package com.saurabh.service;

import com.saurabh.dto.RadioJockeyDto;
import com.saurabh.entity.Program;
import com.saurabh.entity.RadioJockey;
import com.saurabh.entity.RadioStation;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RadioJockeyImplement {
    public List<RadioJockeyDto> fetchAllRadioJockey();
    public RadioJockeyDto fetchRadioJockeybyId(Long jockeyId);
    public void addNewRadioJockey(Long stationId,Long programId,RadioJockey radioJockey);
    public void updateRadioJockey(Long jockeyId,RadioJockey radioJockey);
    public void deleteRadioJockeyt(Long jockeyId);
    public List<RadioStation> fetchAllStationAttachedwithJockey(Long jockeyId);
    public List<RadioStation> fetchAllProgramDetailsAttachedwithJockey(Long jockeyId);
    List<RadioJockey> findRadioJockeysByProgramId(Long programId);
    List<RadioJockey> findJockeysByStationId(Long stationId);
    Program findProgramByJockeyId(@Param("jockeyId") Long jockeyId);

   RadioJockey getRadioJockeyProgramStationData( Long jockeyId);


}