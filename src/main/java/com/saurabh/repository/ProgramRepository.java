package com.saurabh.repository;

import com.saurabh.entity.Program;
import com.saurabh.entity.RadioJockey;
import com.saurabh.entity.RadioStation;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository

public interface ProgramRepository extends CrudRepository<Program, Long> {
    @Query("SELECT p.hostedByid FROM Program p WHERE p.id = :programId")
    List<RadioJockey> findJockeysByProgramId(@Param("programId") Long programId);


    //  program played on particulat staion on perticular date
    @Query(value = "SELECT p.* FROM programs p WHERE p.radio_station_id = :stationId AND p.program_date = :date", nativeQuery = true)
    List<Program> findProgramsByStationIdAndDate(@Param("stationId") Long stationId, @Param("date") LocalDate date);

    //make filters for getting data on behalf of date, stationId, programId
    @Query(value = "SELECT p.* FROM programs p WHERE p.radio_station_id = :stationId AND p.program_date = :date AND p.id= :productId", nativeQuery = true)
    List<Program> findProgramsByStationIdAndDateAndproductId(@Param("stationId") Long stationId, @Param("date") LocalDate date, @Param("productId") Long productId);

    @Query(nativeQuery = true, value = "SELECT * FROM programs p " + "JOIN radio_jockeys j ON p.jockey_id = j.id " + "JOIN advertisement a ON p.advertisement_id = a.id " + "WHERE p.station_id = :stationId AND p.date = :date")
    List<Object[]> getStationDetailsForDate(@Param("stationId") Long stationId, @Param("date") LocalDate date);

    //All details for station for the any date
    List<Program> findByplayDateAndBroadcastedOn(LocalDate playDate, RadioStation broadcastedOn);
    @Modifying
    @Query("DELETE FROM Program j WHERE j.id = :programId ")
    void deleteProgram( @Param("programId") Long programId);
}
