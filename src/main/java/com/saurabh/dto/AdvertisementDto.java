package com.saurabh.dto;

import com.saurabh.entity.Advertisement;
import com.saurabh.entity.Program;
import lombok.*;


@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AdvertisementDto {
    private Long id;
    private String advertiserName;

    private String addType;

    private int addDuration;

    private double cost;

//    private Program playedDuring;

    public AdvertisementDto(Advertisement advertisement) {
        this.id = advertisement.getId();
        this.advertiserName = advertisement.getAdvertiserName();
        this.addType = advertisement.getAddType();
        this.addDuration = advertisement.getAddDuration();
        this.cost = advertisement.getCost();
//        this.playedDuring = advertisement.getPlayedDuring();
    }
}
