package com.musubi.domain.location.application;

import com.musubi.domain.location.domain.Location;
import com.musubi.domain.location.dao.LocationRepository;
import com.musubi.domain.location.dto.LocationCheckRequestDto;
import com.musubi.domain.location.dto.LocationCheckResponseDto;
import com.musubi.domain.user.application.GuardianService;
import com.musubi.domain.user.application.UserService;
import com.musubi.domain.user.dao.GuardianRepository;
import com.musubi.domain.user.dao.UserRepository;
import com.musubi.domain.user.domain.Guardian;
import com.musubi.domain.user.domain.User;
import com.musubi.domain.user.type.UserType;
import com.musubi.global.utils.NaverMapUtil;
import jakarta.transaction.Transactional;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LocationService {

    private final LocationRepository locationRepository;
    private final UserRepository userRepository;
    private final GuardianRepository guardianRepository;

    @Transactional
    public LocationCheckResponseDto checkLocation(
            LocationCheckRequestDto locationCheckRequestDto, String type) { // 쓰레기 코드 2 (에러 핸들링 안함)
        String tmp = locationCheckRequestDto.getCoordinate(); //LocationMaker.makeDistrict(tmp)
        Location location = Location.builder()
                .coordinate(tmp)
                .district(NaverMapUtil.districtParser(tmp))
                .build();

        locationRepository.save(location);

        if (type.equals(UserType.USER.getValue())) {
            User user = userRepository.findById(locationCheckRequestDto.getUserId())
                    .orElseThrow(() -> new IllegalArgumentException("Error"));
            user.setLocation(location);
        }
        else if (type.equals(UserType.GUARDIAN.getValue())) {
            Guardian guardian = guardianRepository.findById(locationCheckRequestDto.getUserId())
                    .orElseThrow(() -> new IllegalArgumentException("Error"));
            guardian.setLocation(location);
        }
        else {
            throw new IllegalArgumentException("Error");
        }

        return LocationCheckResponseDto.fromEntity(location);
    }

}
