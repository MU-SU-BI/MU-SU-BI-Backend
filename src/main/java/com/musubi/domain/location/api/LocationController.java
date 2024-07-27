package com.musubi.domain.location.api;

import com.musubi.domain.location.application.LocationService;
import com.musubi.domain.location.domain.Location;
import com.musubi.domain.location.dto.LocationCheckRequestDto;
import com.musubi.global.utils.DefaultDataResponse;
import com.musubi.global.utils.DefaultResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/location")
@RequiredArgsConstructor
public class LocationController {
    private final LocationService locationService;

    @PostMapping("set")
    ResponseEntity<?> set(@RequestBody @Valid LocationCheckRequestDto locationCheckRequestDto) {
        return ResponseEntity.status(201)
                .body(new DefaultDataResponse<>(201, "위치 확인 완료", locationService.checkLocation(locationCheckRequestDto)))
        ;
    }
}
