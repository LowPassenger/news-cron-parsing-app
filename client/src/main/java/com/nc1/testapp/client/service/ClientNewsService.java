package com.nc1.testapp.client.service;

import com.nc1.testapp.common.model.dto.NewsResponseDto;
import com.nc1.testapp.common.utils.AppConstants;
import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ClientNewsService {
    @Autowired
    private RestTemplate restTemplate;

    public List<NewsResponseDto> getNewsByPeriod(String period) {
        String url = AppConstants.SERVER_API_URL_FOR_LOCAL_SERVER + period;
        ResponseEntity<NewsResponseDto[]> response = restTemplate.getForEntity(url, NewsResponseDto[].class);
        return Arrays.asList(response.getBody());
    }
}
