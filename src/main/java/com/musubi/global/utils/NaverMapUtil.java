package com.musubi.global.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.musubi.global.utils.dto.NaverMapResponseDto;
import com.musubi.global.utils.dto.NaverMapResponseJsonDto;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

public class NaverMapUtil {
    @Value("${naver.url.search.reverse}")
    public static String URL;

    @Value("${naver.url.search.path}")
    public static String PATH;

    @Value("${naver.client.id}")
    public static String ID;

    @Value("${naver.client.secret}")
    public static String KEY;

    public static String districtParser(String coordinate) {
        URI uri = UriComponentsBuilder
                .fromUriString("https://naveropenapi.apigw.ntruss.com")
                .path("/map-reversegeocode/v2/gc")
                .queryParam("coords", coordinate)
                .queryParam("output", "json")
                .encode()
                .build()
                .toUri();

        RequestEntity<Void> request = RequestEntity
                .get(uri)
                .header("X-NCP-APIGW-API-KEY-ID", "2i3lonwpk6")
                .header("X-NCP-APIGW-API-KEY", "lQcbg33Z2fVhNAs2uVFRLN9Ebxa0zEcD0nzOV3ec")
                .build();

        System.out.println(request.getHeaders());

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.exchange(request, String.class);

        ObjectMapper objectMapper = new ObjectMapper();
        NaverMapResponseJsonDto naverMapResponseJsonDto = null;

        try {
            naverMapResponseJsonDto = objectMapper.readValue(response.getBody(), NaverMapResponseJsonDto.class);
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        List<NaverMapResponseDto> responseDtoList = naverMapResponseJsonDto.getResults();

        NaverMapResponseDto naverMapResponseDto = responseDtoList.get(0);

        String result =
                naverMapResponseDto.getRegion().getArea1().getName() +" "+ naverMapResponseDto.getRegion().getArea2()
                        .getName() + " " + naverMapResponseDto.getRegion().getArea3().getName();
        return result;
    }
}
