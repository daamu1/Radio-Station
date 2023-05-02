package com.saurabh.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.saurabh.entity.Program;
import com.saurabh.entity.RadioStation;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ProgramDto {

    private Long id;

    private String name;

    private LocalDate playDate;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private int duration;

    private String category;

//    private RadioStation broadcastedOn;
//
//    private List<AdvertisementDto> advertisements;

    public ProgramDto(Program program) {
        this.id = program.getId();
        this.name = program.getName();
        this.playDate = program.getPlayDate();
        this.startTime = program.getStartTime();
        this.endTime = program.getEndTime();
        this.duration = program.getDuration();
        this.category = program.getCategory();
//        this.broadcastedOn = program.getBroadcastedOn();
//        this.advertisements = (List<AdvertisementDto>) program;
    }
}
