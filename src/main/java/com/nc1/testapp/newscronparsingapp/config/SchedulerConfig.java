package com.nc1.testapp.newscronparsingapp.config;

import com.nc1.testapp.newscronparsingapp.model.News;
import com.nc1.testapp.newscronparsingapp.parser.NewsParser;
import com.nc1.testapp.newscronparsingapp.service.NewsService;
import com.nc1.testapp.newscronparsingapp.utils.AppConstants;
import java.io.IOException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableScheduling
public class SchedulerConfig {

    @Autowired
    private NewsParser newsParser;

    @Autowired
    private NewsService newsService;

    @Scheduled(cron = AppConstants.NEWS_PARSING_TIME_PERIOD)
    public void parseAndSaveNews() throws IOException {
        List<News> newsList = newsParser.parseNewsFromWebsite();
        for (News news : newsList) {
            newsService.saveNews(news);
        }
    }
}
