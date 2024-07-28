package com.musubi.domain.location.dao;

import com.musubi.domain.location.domain.CurrentLocation;
import lombok.Getter;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CurrentLocationRepository extends JpaRepository<CurrentLocation, Long> {
}
