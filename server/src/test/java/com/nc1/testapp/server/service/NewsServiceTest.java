package com.nc1.testapp.server.service;

import com.nc1.testapp.common.model.News;
import com.nc1.testapp.common.model.dto.NewsRequestDto;
import com.nc1.testapp.common.model.mapper.impl.NewsMapperImpl;
import com.nc1.testapp.server.exception.PeriodNotFoundException;
import com.nc1.testapp.server.repository.NewsRepository;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class NewsServiceTest {
    @Mock
    private NewsRepository newsRepository;

    @Mock
    private TimePeriodService periodService;

    @Mock
    private NewsMapperImpl mapper;

    @InjectMocks
    private NewsService newsService;

    @Test
    void testGetNewsForPeriodPeriodNotFoundException() throws PeriodNotFoundException {
        String period = "invalid_period";

        when(periodService.getDayPeriodFromString(period))
                .thenThrow(new PeriodNotFoundException("Invalid period!"));

        List<News> result = newsService.getNewsForPeriod(period);

        assertTrue(result.isEmpty());
        verify(newsRepository, never()).findByPublicationTimeBetween(any(), any());
    }

    @Test
    void testSaveNews() {
        News news = new News();

        newsService.saveNews(news);

        verify(newsRepository).save(news);
    }

    @Test
    void testDeleteNews() {
        Long newsId = 1L;

        newsService.deleteNews(newsId);

        verify(newsRepository).deleteById(newsId);
    }

    @Test
    void testSaveNewsScheduledTask() {
        List<NewsRequestDto> newsList = List.of(new NewsRequestDto(), new NewsRequestDto());
        News mockNews = new News();

        when(mapper.toModel(any(NewsRequestDto.class))).thenReturn(mockNews);

        int result = newsService.saveNewsScheduledTask(newsList);

        assertEquals(2, result);
        verify(newsRepository, times(2)).save(mockNews);
    }

    @Test
    void testGetTodayNews() {
        LocalDate today = LocalDate.now();
        LocalDateTime startOfDay = today.atStartOfDay();
        LocalDateTime endOfDay = today.atTime(LocalTime.MAX);

        List<News> expectedNews = List.of(new News());

        when(newsRepository.findTodayNews(startOfDay, endOfDay)).thenReturn(expectedNews);

        List<News> result = newsService.getTodayNews();

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(newsRepository).findTodayNews(startOfDay, endOfDay);
    }
}
