package com.nc1.testapp.server.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nc1.testapp.common.model.News;
import com.nc1.testapp.server.service.NewsService;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(NewsController.class)
class NewsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private NewsService newsService;

    @Autowired
    private ObjectMapper objectMapper;

    private News mockNews;

    @BeforeEach
    void setUp() {
        mockNews = new News();
        mockNews.setId(1L);
        mockNews.setHeadline("Test Headline");
        mockNews.setDescription("Test Description");
        mockNews.setPublicationTime(LocalDateTime.now());
    }

    @Test
    void testGetNewsForPeriod() throws Exception {
        String period = "morning";
        List<News> mockNewsList = List.of(mockNews);

        when(newsService.getNewsForPeriod(period)).thenReturn(mockNewsList);

        mockMvc.perform(get("/api/news/period")
                        .param("period", period)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(mockNewsList.size()))
                .andExpect(jsonPath("$[0].headline").value(mockNews.getHeadline()));

        verify(newsService, times(1)).getNewsForPeriod(period);
    }

    @Test
    void testAddNews() throws Exception {
        mockMvc.perform(post("/api/news")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(mockNews)))
                .andExpect(status().isOk());

        verify(newsService, times(1)).saveNews(any(News.class));
    }

    @Test
    void testDeleteNews() throws Exception {
        Long newsId = 1L;

        mockMvc.perform(delete("/api/news/{id}", newsId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(newsService, times(1)).deleteNews(newsId);
    }
}
