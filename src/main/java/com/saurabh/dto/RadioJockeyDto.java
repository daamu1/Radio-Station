package com.saurabh.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.saurabh.entity.Program;
import com.saurabh.entity.RadioJockey;
import com.saurabh.entity.RadioStation;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;


@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class RadioJockeyDto {

    private Long id;

    private String name;

    private LocalDate dateOfBirth;

    private String gender;

    private String contactPhone;

    private String contactEmail;
//
//    private List<RadioStation> worksAt;
//
//    private Program program;

    public RadioJockeyDto(RadioJockey radioJockey) {
        this.id = radioJockey.getId();
        this.name = radioJockey.getName();
        this.dateOfBirth = radioJockey.getDateOfBirth();
        this.gender = radioJockey.getGender();
        this.contactPhone = radioJockey.getContactPhone();
        this.contactEmail = radioJockey.getContactEmail();
//        this.worksAt = radioJockey.getWorksAt();
//        this.program = radioJockey.getProgram();
    }
}
