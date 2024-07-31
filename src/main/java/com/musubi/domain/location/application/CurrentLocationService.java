package com.musubi.domain.location.application;

import com.musubi.domain.location.dao.CurrentLocationRepository;
import com.musubi.domain.location.dao.LocationRepository;
import com.musubi.domain.location.domain.CurrentLocation;
import com.musubi.domain.location.domain.Location;
import com.musubi.domain.location.dto.CurrentLocationRequestDto;
import com.musubi.domain.location.dto.CurrentLocationResponseDto;
import com.musubi.domain.user.dao.GuardianRepository;
import com.musubi.domain.user.dao.UserRepository;
import com.musubi.domain.user.domain.Guardian;
import com.musubi.domain.user.domain.User;
import com.musubi.global.exception.BusinessLogicException;
import jakarta.transaction.Transactional;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
                    .orElseThrow(() -> new BusinessLogicException("올바른 User가 아닙니다.", HttpStatus.BAD_REQUEST.value()));

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
        } else if (type.equals("guardian")) {
            Guardian guardian = guardianRepository.findById(currentLocationRequestDto.getUserId())
                    .orElseThrow(() -> new BusinessLogicException("올바른 보호자가 아닙니다.", HttpStatus.BAD_REQUEST.value()));

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

    public CurrentLocationResponseDto checkCurrentLocation(Long guardianId) {
        User user = userRepository.findByGuardianId(guardianId)
                .orElseThrow(() -> new BusinessLogicException("연동된 유저가 없습니다.", HttpStatus.BAD_REQUEST.value()));

        return CurrentLocationResponseDto.fromEntity(user);
    }
}
