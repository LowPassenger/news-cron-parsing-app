package com.nc1.testapp.server.config;

import com.nc1.testapp.common.model.dto.NewsRequestDto;
import com.nc1.testapp.common.utils.AppConstants;
import com.nc1.testapp.server.exception.ParsingProcessException;
import com.nc1.testapp.server.parser.NewsParser;
import com.nc1.testapp.server.service.NewsService;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableScheduling
@Slf4j
public class SchedulerConfig {

    @Autowired
    private NewsParser newsParser;

    @Autowired
    private NewsService newsService;

    @Scheduled(cron = AppConstants.NEWS_PARSING_TIME_PERIOD)
    public void parseAndSaveNews() {
        List<NewsRequestDto> newsList = new ArrayList<>();
        try {
            newsList = newsParser.parseNewsFromWebsite();
        } catch (ParsingProcessException exception) {
            log.error("Parsing process failed! Params: {}", exception.getMessage());

        }
        int savedNews = newsService.saveNewsScheduledTask(newsList);
        log.info("The {} news was successfully saved to database", savedNews);
    }
}
