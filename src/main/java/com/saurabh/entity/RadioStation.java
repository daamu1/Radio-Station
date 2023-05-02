package com.saurabh.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.web.servlet.tags.Param;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "radio_stations")
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class RadioStation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "frequency")
    private String frequency;

//    @Column(name = "play_date")
//    private LocalDate playDate;

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

    @OneToMany(mappedBy = "broadcastedOn", fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH})
//    @JoinColumn(name = "host_by_id")
    @JsonIgnore
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private List<Program> programs;
    @ManyToMany(fetch = FetchType.EAGER,cascade = {CascadeType.REFRESH,
            CascadeType.DETACH,CascadeType.MERGE,CascadeType.PERSIST})
    @JoinTable(name = "jockey_station", joinColumns = @JoinColumn(name = "station_id"), inverseJoinColumns = @JoinColumn(name = "radio_jockey_id"))
    @JsonIgnore
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private List<RadioJockey> radioJockeys;

    public void addProgram(Program program) {
        if (programs == null) {
            programs = new ArrayList<Program>();
        }
        programs.add(program);
       program.setBroadcastedOn(this);
    }

}

