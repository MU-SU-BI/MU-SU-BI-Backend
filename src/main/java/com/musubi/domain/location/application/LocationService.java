package com.musubi.domain.location.application;

import com.musubi.domain.location.domain.Location;
import com.musubi.domain.location.dao.LocationRepository;
import com.musubi.domain.location.dto.LocationCheckRequestDto;
import com.musubi.domain.location.dto.LocationCheckResponseDto;
import com.musubi.domain.location.process.LocationMaker;
import com.musubi.domain.user.domain.User;
import com.musubi.domain.user.dto.UserLoginRequestDto;
import com.musubi.domain.user.dto.UserLoginResponseDto;
import com.musubi.domain.user.exception.NotFoundUserException;
import com.musubi.domain.user.exception.WrongPasswordException;
import com.musubi.global.constants.ErrorMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LocationService {

    private final LocationRepository locationRepository;

    public String checkLocation(LocationCheckRequestDto locationCheckRequestDto) { // 쓰레기 코드 2 (에러 핸들링 안함)
        String tmp = locationCheckRequestDto.getCoordinate();
        Location location = Location.builder()
                .coordinate(tmp)
                .district(LocationMaker.makeDistrict(tmp))
                .build();

        locationRepository.save(location);
        return LocationCheckResponseDto.fromEntity(location);
    }


}
