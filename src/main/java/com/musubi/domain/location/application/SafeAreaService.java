package com.musubi.domain.location.application;

import com.musubi.domain.location.dao.SafeAreaRepository;
import com.musubi.domain.location.domain.SafeArea;
import com.musubi.domain.location.dto.SafeAreaRequestDto;
import com.musubi.domain.location.dto.SafeAreaResponseDto;
import com.musubi.domain.user.dao.UserRepository;
import com.musubi.domain.user.domain.User;
import com.musubi.domain.user.type.UserType;
import jakarta.transaction.Transactional;
import com.musubi.global.exception.BusinessLogicException;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.PrecisionModel;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SafeAreaService {
    private final SafeAreaRepository safeAreaRepository;
    private final UserRepository userRepository;

    public static Point createPoint(Double latitude, Double longitude) {
        GeometryFactory geometryFactory = new GeometryFactory(new PrecisionModel(), 4326);
        return geometryFactory.createPoint(new Coordinate(latitude, longitude));
    }

    public void updateSafeArea(SafeAreaRequestDto safeAreaRequestDto) {

        User user = userRepository.findByGuardianId(safeAreaRequestDto.getUserId())
                .orElseThrow(() -> new BusinessLogicException("올바른 보호자 ID가 아닙니다.", HttpStatus.BAD_REQUEST.value()));

        SafeArea safeArea = SafeArea.builder()
                .latitude(safeAreaRequestDto.getLatitude())
                .longitude(safeAreaRequestDto.getLongitude())
                .radius(safeAreaRequestDto.getRadius())
                .center(createPoint(safeAreaRequestDto.getLatitude(), safeAreaRequestDto.getLongitude()))
                .user(user)
                .build();

        safeAreaRepository.save(safeArea);
    }

    public List<SafeAreaResponseDto> findSafeAreas(Long guardianId) {

        User user = userRepository.findByGuardianId(guardianId)
                .orElseThrow(() -> new BusinessLogicException("올바른 보호자 ID가 아닙니다.", HttpStatus.BAD_REQUEST.value()));

        return user.getSafeAreas().stream()
                .map(m -> new SafeAreaResponseDto(m.getLongitude(), m.getLatitude(), m.getRadius()))
                .collect(Collectors.toList());
    }
}