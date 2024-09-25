package com.nc1.testapp.client.service;

import com.nc1.testapp.common.model.News;
import com.nc1.testapp.common.model.dto.NewsResponseDto;
import com.nc1.testapp.common.model.mapper.impl.NewsMapperImpl;
import com.nc1.testapp.common.utils.AppConstants;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isNull;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

@ExtendWith(MockitoExtension.class)
class ClientNewsServiceTest {

    @InjectMocks
    private ClientNewsService clientNewsService;

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private NewsMapperImpl mapper;

    private List<News> mockNewsList;

    private List<NewsResponseDto> mockNewsResponseDtoList;

    @BeforeEach
    void setUp() {
        News news1 = new News();
        news1.setHeadline("News 1");
        news1.setDescription("Description 1");

        News news2 = new News();
        news2.setHeadline("News 2");
        news2.setDescription("Description 2");

        mockNewsList = List.of(news1, news2);

        NewsResponseDto dto1 = new NewsResponseDto();
        dto1.setHeadline("News 1");
        dto1.setDescription("Description 1");

        NewsResponseDto dto2 = new NewsResponseDto();
        dto2.setHeadline("News 2");
        dto2.setDescription("Description 2");

        mockNewsResponseDtoList = List.of(dto1, dto2);
    }

    @Test
    void testGetNewsByPeriodSuccess() {
        String period = "morning";
        String url = AppConstants.SERVER_API_URL_FOR_LOCAL_SERVER + period;

        ResponseEntity<List<News>> responseEntity = new ResponseEntity<>(mockNewsList,
                HttpStatus.OK);
        when(restTemplate.exchange(eq(url), eq(HttpMethod.GET), isNull(),
                any(ParameterizedTypeReference.class)))
                .thenReturn(responseEntity);

        when(mapper.toDto(mockNewsList.get(0))).thenReturn(mockNewsResponseDtoList.get(0));
        when(mapper.toDto(mockNewsList.get(1))).thenReturn(mockNewsResponseDtoList.get(1));

        List<NewsResponseDto> result = clientNewsService.getNewsByPeriod(period);

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("News 1", result.get(0).getHeadline());
        assertEquals("News 2", result.get(1).getHeadline());

        verify(restTemplate).exchange(eq(url), eq(HttpMethod.GET), isNull(),
                any(ParameterizedTypeReference.class));
        verify(mapper).toDto(mockNewsList.get(0));
        verify(mapper).toDto(mockNewsList.get(1));
    }
}
