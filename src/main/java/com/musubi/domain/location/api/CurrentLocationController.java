package com.musubi.domain.location.api;

import com.google.firebase.messaging.FirebaseMessagingException;
import com.musubi.domain.location.application.CurrentLocationService;
import com.musubi.domain.location.dto.CurrentLocationRequestDto;
import com.musubi.global.utils.DefaultDataResponse;
import com.musubi.global.utils.DefaultResponse;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
	ResponseEntity<?> now(@RequestBody @Valid CurrentLocationRequestDto currentLocationRequestDto,
		@RequestParam String type) throws FirebaseMessagingException {
		currentLocationService.updateLocation(currentLocationRequestDto, type);
		return ResponseEntity.ok().body(new DefaultResponse(200, "전송 성공"));
	}

	@GetMapping("{guardianId}")
	ResponseEntity<?> findCurrentLocation(@PathVariable Long guardianId) {
		return ResponseEntity.ok()
			.body(new DefaultDataResponse<>(200, "위치 확인 완료", currentLocationService.checkCurrentLocation(guardianId)));
	}
}
