package com.musubi.domain.location.application;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.musubi.domain.location.dto.NaverMapApiResponseDto;
import com.musubi.global.exception.BusinessLogicException;
import jakarta.transaction.Transactional;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Component
@Transactional
@RequiredArgsConstructor
public class NaverMapApiService {

    @Value("${naver.url.search.path}")
    private String PATH;
    @Value("${naver.client.id}")
    private String ID;
    @Value("${naver.client.secret}")
    private String KEY;
    @Value("${naver.url.search.reverse}")
    private String URL;

    public String coordinateToDistrict(String coordinate) {
        URI uri = UriComponentsBuilder
                .fromUriString(URL)
                .path(PATH)
                .queryParam("coords", coordinate)
                .queryParam("output", "json")
                .encode()
                .build()
                .toUri();

        RequestEntity<Void> request = RequestEntity
                .get(uri)
                .header("X-NCP-APIGW-API-KEY-ID", ID)
                .header("X-NCP-APIGW-API-KEY", KEY)
                .build();

        System.out.println(KEY);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.exchange(request, String.class);

        ObjectMapper objectMapper = new ObjectMapper();

        NaverMapApiResponseDto naverMapAPIResponseDto;

        try {
            naverMapAPIResponseDto = objectMapper.readValue(response.getBody(), NaverMapApiResponseDto.class);
        } catch (JsonProcessingException e) {
            throw new BusinessLogicException("Naver Map API 요청에 실패 했습니다.", HttpStatus.INTERNAL_SERVER_ERROR.value());
        }

        return naverMapAPIResponseDto.getDistrict();
    }
}

