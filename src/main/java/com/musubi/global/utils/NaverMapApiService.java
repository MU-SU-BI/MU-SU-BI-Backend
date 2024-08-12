package com.musubi.global.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.musubi.global.exception.BusinessLogicException;
import com.musubi.global.utils.dto.RefactorNaverMapResponseDto;
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

    public String districtParser(String coordinate) {
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

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.exchange(request, String.class);

        ObjectMapper objectMapper = new ObjectMapper();

        RefactorNaverMapResponseDto refactorNaverMapResponseDto;

        try {
            refactorNaverMapResponseDto = objectMapper.readValue(response.getBody(), RefactorNaverMapResponseDto.class);
        } catch (JsonProcessingException e) {
            throw new BusinessLogicException("Naver Map API 요청에 실패 했습니다.", HttpStatus.INTERNAL_SERVER_ERROR.value());
        }

        return refactorNaverMapResponseDto.getDistrict();
    }
}

