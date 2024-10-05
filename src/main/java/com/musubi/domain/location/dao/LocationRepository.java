package com.musubi.domain.location.dao;

import com.musubi.domain.location.domain.Location;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocationRepository extends JpaRepository<Location, Long> {
    Optional<Location>  findByDistrict(String district);

}
