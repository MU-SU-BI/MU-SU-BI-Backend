package com.musubi.domain.location.api;

import com.musubi.domain.location.application.SafeAreaService;
import com.musubi.domain.location.dto.SafeAreaRequestDto;
import com.musubi.global.utils.DefaultDataResponse;
import com.musubi.global.utils.DefaultResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/safe-area")
@RequiredArgsConstructor
public class SafeAreaController {
    private final SafeAreaService safeAreaService;

    @PostMapping
    ResponseEntity<?> update(@RequestBody @Valid SafeAreaRequestDto safeAreaRequestDto) {
        safeAreaService.updateSafeArea(safeAreaRequestDto);
        return ResponseEntity.ok().body(new DefaultResponse(200, "안전구역 설정 성공"));
    }

    @GetMapping("{guardianId}")
    ResponseEntity<?> findSafeArea(@PathVariable Long guardianId) {
        return ResponseEntity.ok()
                .body(new DefaultDataResponse<>(200, "전송 완료", safeAreaService.findSafeAreas(guardianId)));
    }
}
