package com.musubi.domain.location.api;

import com.musubi.domain.location.application.CurrentLocationService;
import com.musubi.domain.location.dto.CurrentLocationRequestDto;
import com.musubi.global.utils.DefaultDataResponse;
import com.musubi.global.utils.DefaultResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/current-location")
@RequiredArgsConstructor
public class CurrentLocationController {
    private final CurrentLocationService currentLocationService;

    @PutMapping
    ResponseEntity<?> now(@RequestBody @Valid CurrentLocationRequestDto currentLocationRequestDto, @RequestParam String type) {
        currentLocationService.updateLocation(currentLocationRequestDto, type);
        return ResponseEntity.ok().body(new DefaultResponse(200, "전송 성공"));
    }
}
