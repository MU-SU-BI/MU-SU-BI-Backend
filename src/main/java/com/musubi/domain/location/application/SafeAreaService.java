package com.musubi.domain.location.application;

import com.musubi.domain.location.dao.SafeAreaRepository;
import com.musubi.domain.location.domain.SafeArea;
import com.musubi.domain.location.dto.SafeAreaRequestDto;
import com.musubi.domain.location.dto.SafeAreaResponseDto;
import com.musubi.domain.user.dao.GuardianRepository;
import com.musubi.domain.user.dao.UserRepository;
import com.musubi.domain.user.domain.Guardian;
import com.musubi.domain.user.domain.User;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SafeAreaService {
    private final SafeAreaRepository safeAreaRepository;
    private final UserRepository userRepository;
    private final GuardianRepository guardianRepository;

    public void updateSafeArea(SafeAreaRequestDto safeAreaRequestDto) {
        Guardian guardian = guardianRepository.findById(safeAreaRequestDto.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("Error"));

        User user = userRepository.findById(guardian.getUser().getId())
                .orElseThrow(() -> new IllegalArgumentException("Error"));

        SafeArea safeArea = SafeArea.builder()
                .latitude(safeAreaRequestDto.getLatitude())
                .longitude(safeAreaRequestDto.getLongitude())
                .radius(safeAreaRequestDto.getRadius())
                .user(user)
                .build();

        safeAreaRepository.save(safeArea);
    }

    public List<SafeAreaResponseDto> findSafeAreas(Long userId) {
        Guardian guardian = guardianRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Error"));

        User user = userRepository.findById(guardian.getUser().getId())
                .orElseThrow(() -> new IllegalArgumentException("Error"));

        return user.getSafeAreas().stream()
                .map(m -> new SafeAreaResponseDto(m.getLongitude(), m.getLatitude(), m.getRadius()))
                .collect(Collectors.toList());
    }
}