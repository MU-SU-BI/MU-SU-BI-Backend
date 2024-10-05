package com.musubi.domain.location.application;

import com.musubi.domain.location.dao.CurrentLocationRepository;
import com.musubi.domain.location.dao.LocationRepository;
import com.musubi.domain.location.domain.CurrentLocation;
import com.musubi.domain.location.domain.Location;
import com.musubi.domain.location.domain.SafeArea;
import com.musubi.domain.location.dto.CurrentLocationRequestDto;
import com.musubi.domain.location.dto.CurrentLocationResponseDto;
import com.musubi.domain.user.dao.GuardianRepository;
import com.musubi.domain.user.dao.UserRepository;
import com.musubi.domain.user.domain.Guardian;
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
    private final GuardianRepository guardianRepository;

    @Transactional
    public void updateLocation(CurrentLocationRequestDto currentLocationRequestDto, String type) {
        if (type.equals("user")) {
            User user = userRepository.findById(currentLocationRequestDto.getUserId())
                    .orElseThrow(() -> new IllegalArgumentException("Error"));

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
            //if (!user.getSafeAreas().isEmpty()) {
            //    for (SafeArea safeArea : user.getSafeAreas()) {

            //    }
            //}
        } else if (type.equals("guardian")) {
            Guardian guardian = guardianRepository.findById(currentLocationRequestDto.getUserId())
                    .orElseThrow(() -> new IllegalArgumentException("Error"));

            if (guardian.getCurrentLocation() == null) {
                CurrentLocation currentLocation = CurrentLocation.builder()
                        .nowLatitude(currentLocationRequestDto.getLatitude())
                        .nowLongitude(currentLocationRequestDto.getLongitude())
                        .build();
                currentLocationRepository.save(currentLocation);
                guardian.setCurrentLocation(currentLocation);
            } else {
                guardian.getCurrentLocation().updateCoordinate(currentLocationRequestDto.getLatitude(),
                        currentLocationRequestDto.getLongitude());
            }
        }
    }

    public CurrentLocationResponseDto checkCurrentLocation(Long userId) {
        User user = guardianRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("Error"))
                .getUser();
        if (user == null) {
            throw new IllegalArgumentException("Error");
        }
        return CurrentLocationResponseDto.fromEntity(user);
    }

}
