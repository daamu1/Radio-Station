package com.saurabh.dto;

import com.saurabh.entity.RadioStation;
import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class RadioStationDto {

    private Long id;

    private String name;

    private String frequency;

    private String city;

    private String contactPhone;

    private String contactEmail;

    private String website;

    private String genre;


    public RadioStationDto(RadioStation radioStation) {
        this.id = radioStation.getId();
        this.name = radioStation.getName();
        this.frequency = radioStation.getFrequency();
        this.city = radioStation.getCity();
        this.contactPhone = radioStation.getContactPhone();
        this.contactEmail = radioStation.getContactEmail();
        this.website = radioStation.getWebsite();
        this.genre = radioStation.getGenre();
    }
}

