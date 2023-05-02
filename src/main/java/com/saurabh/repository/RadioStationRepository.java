package com.saurabh.repository;

import com.saurabh.entity.Advertisement;
import com.saurabh.entity.RadioStation;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Repository
@EnableJpaRepositories
public interface RadioStationRepository extends CrudRepository<RadioStation,Long> {
    @Query(value = "SELECT rs.* FROM radio_stations rs " +
            "JOIN jockey_station js ON rs.id = js.station_id " +
            "JOIN radio_jockeys rj ON rj.id = js.radio_jockey_id " +
            "WHERE rj.id = :jockeyId", nativeQuery = true)
    List<RadioStation> findStationsByJockeyId(@Param("jockeyId") Long jockeyId);

    @Query(value = "SELECT s "+
            "FROM radio_stations s " +
            "LEFT JOIN programs s ON s.station_id = p.station_id " +
            "LEFT JOIN radio_jockeys j ON s.station_id = j.station_id " +
            "WHERE s.station_id = :stationId", nativeQuery = true)
    @Transactional
    List<Object[]> findAllDetailsForStation(@Param("stationId") Long stationId);
    @Query(value = "SELECT DISTINCT p.*, rj.*, ad.*, rs.* FROM programs p LEFT JOIN radio_jockeys rj ON p.id = rj.program_id LEFT JOIN advertisements ad ON p.id = ad.program_id LEFT JOIN radio_stations rs ON p.radio_station_id = rs.id WHERE p.program_date = :programDate", nativeQuery = true)
//@Query(value = "SELECT DISTINCT p.id, p.name, rj.name, ad.advertiser_name, rs.name " +
//        "FROM Program p " +
//        "JOIN radio_jockeys rj ON p.id = rj.program_id " +
//        "JOIN advertisements ad ON p.id = ad.program_id " +
//        "JOIN radio_stations rs ON p.radio_station_id = rs.id " +
//        "WHERE p.program_date = :programDate " +
//        "GROUP BY p.id, p.name, rj.name, ad.advertiser_name, rs.name")
    List<Object[]> findProgramDetailsByDate(@Param("programDate") LocalDate programDate);
}
