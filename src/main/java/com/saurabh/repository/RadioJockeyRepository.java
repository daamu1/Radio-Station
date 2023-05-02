package com.saurabh.repository;


import com.saurabh.entity.Program;
import com.saurabh.entity.RadioJockey;
import com.saurabh.entity.RadioStation;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RadioJockeyRepository extends CrudRepository<RadioJockey, Long> {
//        @Query(value = "SELECT s.* FROM radio_stations s JOIN jockey_station rs ON s.id = rs.station_id WHERE rs.radio_jockey_id = :radioJockeyId", nativeQuery = true)
//        List<Object[]> getStationsForRadioJockey(@Param("radioJockeyId") Long radioJockeyId);

    @Query("SELECT p.hostedByid FROM Program p WHERE p.id = :programId")
    List<RadioJockey> findRadioJockeysByProgramId(@Param("programId") Long programId);

    @Query("SELECT rj.worksAt FROM RadioJockey rj WHERE rj.id = :jockeyId")
    List<RadioStation> findStationsByJockeyId(@Param("jockeyId") Long jockeyId);
    @Query("SELECT rs.radioJockeys FROM RadioStation rs WHERE rs.id = :stationId")
    List<RadioJockey> findJockeysByStationId(@Param("stationId") Long stationId);
    @Query(nativeQuery = true, value = "SELECT rj.*, p.*, rs.* "
            + "FROM radio_jockeys rj "
            + "INNER JOIN programs p ON rj.program_id = p.id "
            + "INNER JOIN radio_stations rs ON p.radio_station_id = rs.id "
            + "WHERE rj.id = :jockeyId")
    List<Object[]> getRadioJockeyProgramStationData(@Param("jockeyId") Long radioJockeyId);

    @Query(value = "SELECT p FROM Program p " +
            "JOIN p.hostedByid j " +
            "WHERE j.id = :jockeyId")
    Program findProgramByJockeyId(@Param("jockeyId") Long jockeyId);


}



