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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@NoArgsConstructor
public class RadioJockeyService implements RadioJockeyImplement {
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
        RadioJockey radioJockey = radioJockeyRepository.findById(jockeyId).get();
        RadioJockeyDto radioJockeyDto = new RadioJockeyDto(radioJockey);
        if (radioJockey == null) {
            throw new RadioJockeyNotFoundException("Given Id " + jockeyId + " does not correspond to an available  radio jockey");
        }
        return radioJockeyDto;
    }

    @Override
    public void addNewRadioJockey(Long stationId, RadioJockey radioJockey) {
        RadioStation radioStation = radioStationRepository.findById(stationId).get();
        if (radioStation == null) {
            throw new RadioJockeyNotFoundException("Given Id " + stationId + " does not correspond to an available radio station");
        } else {
//            radioJockey.setProgram(program)
            radioJockey.setWorksAt(radioStation);
            radioStation.getRadioJockeys().add(radioJockey);
            radioStationRepository.save(radioStation);
//            radioJockeyRepository.save(radioJockey)
        }
    }

    @Override
    @Transactional
    public void updateRadioJockey(Long stationId, Long jockeyId, RadioJockey radioJockey) {
        RadioStation radioStation = radioStationRepository.findById(stationId).get();
        RadioJockey radioJockey1 = radioJockeyRepository.findById(jockeyId).get();
        if (radioStation == null) {
            throw new RadioStationNotFoundException("Given Id " + stationId + " does not correspond to an available radio station");
        }
        if (radioJockey1 == null) {
            throw new RadioJockeyNotFoundException("Given Id " + jockeyId + " does not correspond to an available  radio jockey");
        } else {
            radioJockey1.setName(radioJockey.getName());
            radioJockey1.setGender(radioJockey.getGender());
            radioJockey1.setContactEmail(radioJockey.getContactEmail());
            radioJockey1.setWorksAt(radioJockey.getWorksAt());
            radioJockey1.setContactPhone(radioJockey.getContactPhone());
            radioJockey1.setDateOfBirth(radioJockey.getDateOfBirth());
            radioJockey1.setWorksAt(radioStation);
            radioJockeyRepository.save(radioJockey1);
        }

    }

    @Override
    public void deleteRadioJockeyt(Long jockeyId) {
        RadioJockey radioJockey1 = radioJockeyRepository.findById(jockeyId).get();
        if (radioJockey1 == null) {
            throw new RadioJockeyNotFoundException("Given Id " + jockeyId + " does not correspond to an available  radio jockey");
        }
        radioJockeyRepository.deleteById(jockeyId);
    }

    //Radio jockeys attached to  station
    @Override
    @Transactional
    public RadioStation fetchAllStationAttachedwithJockey(Long jockeyId) {
        Optional<RadioJockey> radioJockey1 = radioJockeyRepository.findById(jockeyId);
        if (radioJockey1 == null) {
            throw new RadioJockeyNotFoundException("Given Id " + jockeyId + " does not correspond to an available  radio jockey");
        } else {
            return radioJockeyRepository.findStationsByJockeyId(jockeyId);
        }
    }

    @Override
    public List<RadioStation> fetchAllProgramDetailsAttachedwithJockey(Long jockeyId) {
        Optional<RadioJockey> radioJockey1 = radioJockeyRepository.findById(jockeyId);
        if (radioJockey1 == null) {
            throw new RadioJockeyNotFoundException("Given Id " + jockeyId + " does not correspond to an available  radio jockey");
        } else {
//            return radioJockeyRepository.findStationsByJockeyId(jockeyId)  ;
            return null;
        }

    }

    @Override
    public List<RadioJockey> findRadioJockeysByProgramId(Long programId) {
        Program program = programRepository.findById(programId).get();
        if (program == null) {
            throw new ProgramNotFoundException("Given Id " + programId + " does not correspond to an available  radio program");
        } else {
            return programRepository.findJockeysByProgramId(programId);
        }
    }

    @Override
    public List<RadioJockey> findJockeysByStationId(Long stationId) {
        RadioStation radioStation = radioStationRepository.findById(stationId).get();
        if (radioStation == null) {
            throw new RadioStationNotFoundException("Given Id " + stationId + " does not correspond to an available radio station");
        } else {
            return radioJockeyRepository.findJockeysByStationId(stationId);
        }
    }

    //   Programs attached to Radio jockeys
    @Override
    public List<Program> findProgramByJockeyId(Long jockeyId) {
        Optional<RadioJockey> radioJockey1 = radioJockeyRepository.findById(jockeyId);
        if (radioJockey1 == null) {
            throw new RadioJockeyNotFoundException("Given Id " + jockeyId + " does not correspond to an available radio  jockey");
        } else {
            return (List<Program>) radioJockeyRepository.findProgramByJockeyId(jockeyId);
        }
    }

    @Override
    public RadioJockey getRadioJockeyProgramStationData(Long jockeyId) {
        Optional<RadioJockey> radioJockey1 = radioJockeyRepository.findById(jockeyId);
        if (radioJockey1 == null) {
            throw new RadioJockeyNotFoundException("Given Id " + jockeyId + " does not correspond to an available radio  jockey");
        } else {
            return radioJockeyRepository.findById(jockeyId).get();
        }
    }

}
