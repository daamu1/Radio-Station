package com.saurabh.repository;

import com.saurabh.entity.Advertisement;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@EnableJpaRepositories
public interface AdvertisementRepository extends CrudRepository<Advertisement, Long> {
    @Modifying
    @Query("DELETE FROM Advertisement j WHERE j.id = :advertisementId ")
    void deleteAdvertisement(@Param("advertisementId") Long advertisementId);

    @Query("SELECT a FROM Advertisement a WHERE a.addType = :addType AND a.advertiserName = :advertiserName")
    List<Advertisement> findByAddTypeAndAdvertiserName(@Param("addType") String addType, @Param("advertiserName") String advertiserName);
}
