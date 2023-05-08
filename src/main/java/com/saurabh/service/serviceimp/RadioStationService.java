package com.saurabh.service.serviceimp;

import com.saurabh.dto.RadioStationDto;
import com.saurabh.entity.Program;
import com.saurabh.entity.RadioStation;
import com.saurabh.exception.RadioStationNotFoundException;
import com.saurabh.repository.ProgramRepository;
import com.saurabh.repository.RadioStationRepository;
import com.saurabh.service.RadioStationImplement;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@NoArgsConstructor
public class RadioStationService implements RadioStationImplement {
    @Autowired
    private RadioStationRepository radioStationRepository;
    @Autowired
    private ProgramRepository programRepository;

    public RadioStationService(RadioStationRepository radioStationRepository) {
        this.radioStationRepository = radioStationRepository;
    }

    @Override
    public List<RadioStationDto> fetchAllRadioStation() {
        List<RadioStation> radioStations = (List<RadioStation>) radioStationRepository.findAll();
        List<RadioStationDto> radioStationDtos = new ArrayList<>();
        for (RadioStation radioStation : radioStations) {
            RadioStationDto radioStationDto = new RadioStationDto(radioStation);
            radioStationDtos.add(radioStationDto);
        }
        return radioStationDtos;
    }

    @Override
    public RadioStationDto fetchRadioStationbyId(Long stationId) {
        RadioStation radioStation = radioStationRepository.findById(stationId)
                .orElseThrow(() -> new RadioStationNotFoundException("Radio station with ID " + stationId + " not found"));
        return new RadioStationDto(radioStation);
    }


    @Override
    public void addNewRadioStation(RadioStation radioStation) {
        radioStationRepository.save(radioStation);
    }

    @Override
    public void updateRadioStation(Long stationId, RadioStation radioStatio) {
        RadioStation radioStation = radioStationRepository.findById(stationId)
                .orElseThrow(() -> new RadioStationNotFoundException("Radio station with ID " + stationId + " not found"));
        if (radioStation != null)
            radioStation.setName(radioStatio.getName());
        radioStation.setCity(radioStatio.getCity());
        radioStation.setGenre(radioStatio.getGenre());
        radioStation.setFrequency(radioStatio.getFrequency());
        radioStation.setContactEmail(radioStatio.getContactEmail());
        radioStation.setContactPhone(radioStatio.getContactPhone());
        radioStation.setWebsite(radioStatio.getWebsite());
        radioStation.setPrograms(radioStatio.getPrograms());
        radioStationRepository.save(radioStation);

    }


    @Override
    @Transactional
    public void deleteRadioStation(Long stationId) {
        try {
            RadioStation radioStation = radioStationRepository.findById(stationId)
                    .orElseThrow(() -> new RadioStationNotFoundException("Given Id " + stationId + " does not correspond to an available radio station"));
            radioStationRepository.delete(radioStation);
        } catch (DataAccessException ex) {
            throw new RuntimeException("An error occurred while deleting the radio station", ex);
        }
    }


    public List<Object[]> findAllDetailsForStation(Long stationId) {
        RadioStation radioStation = radioStationRepository.findById(stationId).get();
        if (radioStation == null) {
            throw new RadioStationNotFoundException("Given Id " + stationId + " does not correspond to an available radio station");
        }
        return radioStationRepository.findAllDetailsForStation(stationId);
    }

    @Override
    @Transactional
    public List<Object[]> findProgramDetailsByDate(LocalDate programDate) {
//        if (programDate==null)
//        {
//            throw new RuntimeException("Some Thing went Wrong");
//        }
//        return radioStationRepository.findProgramDetailsByDate(programDate);
        return null;
    }

    //All details for station for the any date
    @Override
    public List<Program> getStationDetails(Long stationId, LocalDate date) {
        RadioStation radioStation = radioStationRepository.findById(stationId)
                .orElseThrow(() -> new RadioStationNotFoundException("Given Id " + stationId + " does not correspond to an available radio station"));
        return programRepository.findByplayDateAndBroadcastedOn(date, radioStation);
    }

}



