package com.saurabh.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "advertisements")
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Advertisement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "advertiser_name")
    private String advertiserName;

    @Column(name = "add_type")
    private String addType;

    @Column(name = "add_duration")
    private int addDuration;

    @Column(name = "cost")
    private double cost;
    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.REFRESH,
            CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "program_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Program playedDuring;

}
