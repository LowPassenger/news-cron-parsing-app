package com.nc1.testapp.client.service;

import com.nc1.testapp.common.model.News;
import com.nc1.testapp.common.model.dto.NewsResponseDto;
import com.nc1.testapp.common.model.mapper.impl.NewsMapperImpl;
import com.nc1.testapp.common.utils.AppConstants;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ClientNewsService {
    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private NewsMapperImpl mapper;

    public List<NewsResponseDto> getNewsByPeriod(String period) {
        String url = AppConstants.SERVER_API_URL_FOR_LOCAL_SERVER + period;
//        ResponseEntity<NewsResponseDto[]> response = restTemplate.getForEntity(url, NewsResponseDto[].class);
        ResponseEntity<List<News>> response = restTemplate
                .exchange(url, HttpMethod.GET, null,
                        new ParameterizedTypeReference<List<News>>() {});

        return response.getBody()
                .stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }
}
