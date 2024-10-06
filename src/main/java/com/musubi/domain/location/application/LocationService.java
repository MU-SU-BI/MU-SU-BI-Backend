package com.musubi.domain.location.application;

import com.musubi.domain.location.dao.LocationRepository;
import com.musubi.domain.location.domain.Location;
import com.musubi.domain.location.dto.LocationCheckRequestDto;
import com.musubi.domain.location.dto.LocationCheckResponseDto;
import com.musubi.domain.user.dao.GuardianRepository;
import com.musubi.domain.user.dao.UserRepository;
import com.musubi.domain.user.domain.Guardian;
import com.musubi.domain.user.domain.User;
import com.musubi.domain.user.type.UserType;
import com.musubi.global.exception.BusinessLogicException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class LocationService {

    private final LocationRepository locationRepository;
    private final UserRepository userRepository;
    private final GuardianRepository guardianRepository;
    private final NaverMapApiService naverMapApiService;

    @Transactional
    public LocationCheckResponseDto checkLocation(
            LocationCheckRequestDto locationCheckRequestDto, String type) { // 쓰레기 코드 2 (에러 핸들링 안함)

        Location location = Location.builder()
                .coordinate(locationCheckRequestDto.getCoordinate())
                .district(naverMapApiService.coordinateToDistrict(locationCheckRequestDto.getCoordinate()))
                .build();

        locationRepository.save(location);

        if (type.equals(UserType.USER.getValue())) {
            User user = userRepository.findById(locationCheckRequestDto.getUserId())
                    .orElseThrow(() -> new BusinessLogicException("올바른 USER ID가 아닙니다", HttpStatus.BAD_REQUEST.value()));
            user.setLocation(location);
        } else if (type.equals(UserType.GUARDIAN.getValue())) {
            Guardian guardian = guardianRepository.findById(locationCheckRequestDto.getUserId())
                    .orElseThrow(() -> new BusinessLogicException("올바른 보호자 ID가 아닙니다.", HttpStatus.BAD_REQUEST.value()));
            guardian.setLocation(location);
        } else {
            throw new BusinessLogicException("올바른 요청이 아닙니다.", HttpStatus.BAD_REQUEST.value());
        }

        return LocationCheckResponseDto.fromEntity(location);
    }

}
