package com.saurabh.service;

import com.saurabh.dto.RadioStationDto;
import com.saurabh.entity.Program;
import com.saurabh.entity.RadioStation;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import java.time.LocalDate;
import java.util.List;

public interface RadioStationImplement {
public List<RadioStationDto> fetchAllRadioStation();

    public RadioStationDto fetchRadioStationbyId(Long stationId);
    public void addNewRadioStation(RadioStation radioStation);
    public void updateRadioStation(Long stationId,RadioStation radioStation);
    public void deleteRadioStation(Long stationId);
   public List<Object[]> findAllDetailsForStation(Long stationId);
    List<Object[]> findProgramDetailsByDate(LocalDate programDate) ;

    List<Program> getStationDetails(Long stationId, LocalDate date);
}

