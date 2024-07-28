package com.musubi.domain.location.application;

import com.musubi.domain.location.dao.CurrentLocationRepository;
import com.musubi.domain.location.dao.LocationRepository;
import com.musubi.domain.location.domain.CurrentLocation;
import com.musubi.domain.location.domain.Location;
import com.musubi.domain.location.dto.CurrentLocationRequestDto;
import com.musubi.domain.user.dao.UserRepository;
import com.musubi.domain.user.domain.User;
import jakarta.transaction.Transactional;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CurrentLocationService {
    private final CurrentLocationRepository currentLocationRepository;
    private final UserRepository userRepository;

    @Transactional
    public void updateLocation(CurrentLocationRequestDto currentLocationRequestDto) {
        User user = userRepository.findById(currentLocationRequestDto.getUserId())
                .orElseThrow(() -> new IllegalArgumentException());

        if (user.getCurrentLocation() == null) {
            CurrentLocation currentLocation = CurrentLocation.builder()
                    .nowLatitude(currentLocationRequestDto.getLatitude())
                    .nowLongitude(currentLocationRequestDto.getLongitude())
                    .build();
            currentLocationRepository.save(currentLocation);
            user.setCurrentLocation(currentLocation);
        } else {
            user.getCurrentLocation().updateCoordinate(currentLocationRequestDto.getLatitude(),
                    currentLocationRequestDto.getLongitude());
        }
    }
}
