package com.saurabh.repository;

import com.saurabh.entity.Advertisement;
import com.saurabh.entity.Program;
import com.saurabh.entity.RadioJockey;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository

public interface ProgramRepository extends CrudRepository<Program, Long> {

    //    @Query(value = "SELECT rj.* FROM radio_jockeys rj " +
//            "INNER JOIN programs ph ON ph.id = rj.program_id " +
//            "WHERE ph.id =:programId", nativeQuery = true)
    @Query("SELECT p.hostedByid FROM Program p WHERE p.id = :programId")
    List<RadioJockey> findJockeysByProgramId(@Param("programId") Long programId);


    //    @Query(value = "SELECT p.* FROM programs p LEFT JOIN stations s ON p.station_id = s.id WHERE s.id = :stationId AND p.date_played = :playDate", nativeQuery = true)
//    List<Program> findProgramByStationAndDate(@Param("stationId") Long stationId, @Param("playDate") LocalDate playDate);
    @Query(value = "SELECT p.* FROM programs p WHERE p.radio_station_id = :stationId AND p.program_date = :date", nativeQuery = true)
    public List<Program> findProgramsByStationIdAndDate(@Param("stationId") Long stationId, @Param("date") LocalDate date);

    @Query(value = "SELECT p.* FROM programs p WHERE p.radio_station_id = :stationId AND p.program_date = :date AND p.id= :productId", nativeQuery = true)
    public List<Program> findProgramsByStationIdAndDateAndproductId(@Param("stationId") Long stationId, @Param("date") LocalDate date, @Param("productId") Long productId);

    @Query(value = "SELECT p.id as program_id, p.name as program_name, p.play_date, rs.id as station_id, rs.name as station_name, a.id as advertisement_id, a.name as advertisement_name, rj.id as jockey_id, rj.name as jockey_name FROM programs p JOIN radio_stations rs ON p.radio_station_id = rs.id  JOIN advertisements a ON a.program_id = p.id  JOIN radio_jockeys rj ON rj.program_id = p.id  WHERE p.radio_station_id = :stationId", nativeQuery = true)
    public List<Program> fetchAllStationData(@Param("stationId") Long stationId);




}
