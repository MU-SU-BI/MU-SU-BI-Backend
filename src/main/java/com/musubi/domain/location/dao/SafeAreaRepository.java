package com.musubi.domain.location.dao;

import com.musubi.domain.location.domain.SafeArea;
import com.musubi.domain.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SafeAreaRepository extends JpaRepository<SafeArea, Long> {
    @Query("SELECT count(1) FROM SafeArea a WHERE a.user = :user AND CAST(ST_DistanceSphere(ST_SetSRID(ST_MakePoint(a.longitude, a.latitude), 4326), ST_SetSRID(ST_MakePoint(:longitude, :latitude), 4326)) AS DOUBLE) < a.radius")
    Integer calculateDistance(@Param("user") User user, @Param("latitude") double latitude, @Param("longitude") double longitude);
}
