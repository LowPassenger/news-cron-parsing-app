package com.nc1.testapp.server.config;

import com.nc1.testapp.server.exception.ParsingProcessException;
import com.nc1.testapp.server.parser.NewsParser;
import com.nc1.testapp.server.service.NewsService;
import com.nc1.testapp.common.model.dto.NewsRequestDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SchedulerConfigTest {

    @Mock
    private NewsParser newsParser;

    @Mock
    private NewsService newsService;

    @InjectMocks
    private SchedulerConfig schedulerConfig;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testParseAndSaveNews_Success() {
        List<NewsRequestDto> newsList = Collections.singletonList(new NewsRequestDto());
        when(newsParser.parseNewsFromWebsite()).thenReturn(newsList);
        when(newsService.saveNewsScheduledTask(newsList)).thenReturn(1);

        schedulerConfig.parseAndSaveNews();

        verify(newsParser, times(1)).parseNewsFromWebsite();
        verify(newsService, times(1)).saveNewsScheduledTask(newsList);
    }

    @Test
    void testParseAndSaveNews_NoNewsParsed() {
        List<NewsRequestDto> emptyNewsList = Collections.emptyList();
        when(newsParser.parseNewsFromWebsite()).thenReturn(emptyNewsList);
        when(newsService.saveNewsScheduledTask(emptyNewsList)).thenReturn(0);

        schedulerConfig.parseAndSaveNews();

        verify(newsParser, times(1)).parseNewsFromWebsite();
        verify(newsService, times(1)).saveNewsScheduledTask(emptyNewsList);
    }

    @Test
    void testParseAndSaveNews_ExceptionDuringParsing() {
        when(newsParser.parseNewsFromWebsite()).thenThrow(new ParsingProcessException("Parsing error"));

        schedulerConfig.parseAndSaveNews();

        verify(newsParser, times(1)).parseNewsFromWebsite();
    }
}
