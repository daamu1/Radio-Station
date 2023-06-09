package com.saurabh.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "radio_stations")
//@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
//@ToString
@JsonIgnoreProperties({"lazyProperty"})
public class RadioStation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "frequency")
    private String frequency;

    @Column(name = "city")
    private String city;

    @Column(name = "contact_phone")
    private String contactPhone;

    @Column(name = "contact_email")
    private String contactEmail;

    @Column(name = "website")
    private String website;

    @Column(name = "genre")
    private String genre;

    @OneToMany(mappedBy = "broadcastedOn", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Program> programs;
    @OneToMany(mappedBy = "worksAt", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @JsonIgnore
    private List<RadioJockey> radioJockeys;

    public void addProgram(Program program) {
        if (programs == null) {
            programs = new ArrayList<Program>();
        }
        programs.add(program);
        program.setBroadcastedOn(this);
    }

    public void addRadioJockey(RadioJockey program) {
        if (programs == null) {
            radioJockeys = new ArrayList<RadioJockey>();
        }
        radioJockeys.add(program);
        program.setWorksAt(this);
    }

}

