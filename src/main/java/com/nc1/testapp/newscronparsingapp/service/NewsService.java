package com.nc1.testapp.newscronparsingapp.service;

import com.nc1.testapp.newscronparsingapp.exception.PeriodNotFoundException;
import com.nc1.testapp.newscronparsingapp.model.News;
import com.nc1.testapp.newscronparsingapp.model.dto.NewsRequestDto;
import com.nc1.testapp.newscronparsingapp.model.mapper.impl.NewsMapperImpl;
import com.nc1.testapp.newscronparsingapp.repository.NewsRepository;
import com.nc1.testapp.newscronparsingapp.utils.DayPeriodLimitsEnum;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collections;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class NewsService {
    @Autowired
    private NewsRepository newsRepository;

    @Autowired
    private TimePeriodService periodService;

    @Autowired
    private NewsMapperImpl mapper;

    public List<News> getNewsForPeriod(String period) {
        LocalDateTime start = null;
        LocalDateTime end = null;
        try {
            DayPeriodLimitsEnum periodEnum = periodService.getDayPeriodFromString(period);
            LocalTime startTime = periodEnum.getDayPeriodLimits()[0];
            LocalTime endTime = periodEnum.getDayPeriodLimits()[1];
            LocalDateTime latestPublicationTime = newsRepository.findLatestPublicationTime();
            LocalDate latestPublicationDate = latestPublicationTime.toLocalDate();
            start = latestPublicationDate.atTime(startTime);
            end = latestPublicationDate.atTime(endTime);
        } catch (PeriodNotFoundException exception) {
            log.error("An exception throws for {} period parameter. Message: {}",
                    period, exception.getMessage());
        }
        return (start == null || end == null) ? Collections.emptyList() :
                newsRepository.findByPublicationTimeBetween(start, end);
    }

    public void saveNews(News news) {
        newsRepository.save(news);
    }

    public void deleteNews(Long id) {
        newsRepository.deleteById(id);
    }

    public int saveNewsScheduledTask(List<NewsRequestDto> newsList) {
        int counter = 0;
        for (NewsRequestDto dtoModel : newsList) {
            saveNews(mapper.toModel(dtoModel));
            counter++;
        }
        return counter;
    }

    public List<News> getTodayNews() {
        LocalDate today = LocalDate.now();
        LocalDateTime startOfDay = today.atStartOfDay();
        LocalDateTime enfOfDay = today.atTime(LocalTime.MAX);
        return newsRepository.findTodayNews(startOfDay, enfOfDay);
    }
}
