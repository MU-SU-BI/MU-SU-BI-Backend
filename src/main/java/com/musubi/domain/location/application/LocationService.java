package com.musubi.domain.location.application;

import com.musubi.domain.location.domain.Location;
import com.musubi.domain.location.dao.LocationRepository;
import com.musubi.domain.location.dto.LocationCheckRequestDto;
import com.musubi.domain.location.dto.LocationCheckResponseDto;
import com.musubi.global.utils.NaverMapUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LocationService {

    private final LocationRepository locationRepository;

    public String checkLocation(LocationCheckRequestDto locationCheckRequestDto) { // 쓰레기 코드 2 (에러 핸들링 안함)
        String tmp = locationCheckRequestDto.getCoordinate(); //LocationMaker.makeDistrict(tmp)
        Location location = Location.builder()
                .coordinate(tmp)
                .district(NaverMapUtil.districtParser(tmp))
                .build();

        locationRepository.save(location);
        return LocationCheckResponseDto.fromEntity(location);
    }


}
