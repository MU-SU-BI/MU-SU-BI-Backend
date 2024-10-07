package com.musubi.domain.location.application;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.musubi.domain.location.dto.NaverMapApiResponseDto;
import com.musubi.domain.user.domain.User;
import com.musubi.global.exception.BusinessLogicException;
import com.musubi.global.utils.S3Util;
import jakarta.transaction.Transactional;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.net.URI;
import java.time.LocalTime;

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

    private final AmazonS3Client amazonS3Client;
    private final S3Util s3Util;
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

    public String getMissingMap(User user) {

        URI uri = UriComponentsBuilder
                .fromUriString(URL)
                .path("/map-static/v2/raster")
                .queryParam("center", user.getCurrentLocation().getNowLongitude() + ", " + user.getCurrentLocation().getNowLatitude())
                .queryParam("level", "16")
                .queryParam("w", "300")
                .queryParam("h", "300")
                .queryParam("scale", "2")
                .queryParam("markers", "pos:"+user.getCurrentLocation().getNowLongitude() + " " + user.getCurrentLocation().getNowLatitude())
                .queryParam("format", "png")
                .encode()
                .build()
                .toUri();

        RequestEntity<Void> request = RequestEntity
                .get(uri)
                .header("X-NCP-APIGW-API-KEY-ID", ID)
                .header("X-NCP-APIGW-API-KEY", KEY)
                .build();

        RestTemplate restTemplate = new RestTemplate();
        try {
            ResponseEntity<byte[]> response = restTemplate.exchange(request, byte[].class);
            if (response.getStatusCode().is2xxSuccessful()) {
                // S3에 업로드
                byte[] imageBytes = response.getBody();
                InputStream inputStream = new ByteArrayInputStream(imageBytes);
                ObjectMetadata metadata = new ObjectMetadata();
                metadata.setContentLength(imageBytes.length);
                metadata.setContentType("image/png");
                return s3Util.putS3Maps(inputStream, user.getId() + "_" + LocalTime.now(), metadata);

            } else {
                throw new BusinessLogicException("저장 실패.", HttpStatus.BAD_REQUEST.value());
            }
        } catch (Exception e) {
           throw new BusinessLogicException("Naver Map API 요청에 실패 했습니다.", HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
    }
}

