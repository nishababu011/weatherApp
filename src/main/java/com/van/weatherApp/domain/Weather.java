package com.van.weatherApp.domain;


import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Builder
@Entity
@Table(name="weather_stats")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Weather implements Serializable {
    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "city")
    private String city;

    @Column(name = "country")
    private String country;

    @Column(name = "description")
    private String description;

}
