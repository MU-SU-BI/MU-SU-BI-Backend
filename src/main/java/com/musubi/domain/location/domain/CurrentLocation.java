package com.musubi.domain.location.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class CurrentLocation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double nowLatitude; // 현재 위도

    private double nowLongitude; // 현재 경도

    public void updateCoordinate(double nowLatitude, double nowLongitude) {
        this.nowLatitude = nowLatitude;
        this.nowLongitude = nowLongitude;
    }
}
