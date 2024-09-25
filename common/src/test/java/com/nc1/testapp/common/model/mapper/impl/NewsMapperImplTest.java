package com.nc1.testapp.common.model.mapper.impl;

import com.nc1.testapp.common.model.News;
import com.nc1.testapp.common.model.dto.NewsRequestDto;
import com.nc1.testapp.common.model.dto.NewsResponseDto;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class NewsMapperImplTest {
    private NewsMapperImpl newsMapper;

    @BeforeEach
    void setUp() {
        newsMapper = new NewsMapperImpl();
    }

    @Test
    void testToDto() {
        News news = new News();
        news.setId(1L);
        news.setHeadline("Test Headline");
        news.setDescription("Test Description");
        news.setPublicationTime(LocalDateTime.of(2024, 9, 17, 14, 28));

        NewsResponseDto dto = newsMapper.toDto(news);

        assertNotNull(dto);
        assertEquals(news.getId(), dto.getId());
        assertEquals(news.getHeadline(), dto.getHeadline());
        assertEquals(news.getDescription(), dto.getDescription());
        assertEquals(news.getPublicationTime(), dto.getPublicationTime());
    }

    @Test
    void testToModel() {
        NewsRequestDto requestDto = new NewsRequestDto();
        requestDto.setHeadline("Test Headline");
        requestDto.setDescription("Test Description");
        requestDto.setPublicationTime(LocalDateTime.of(2024, 3, 7, 7, 40));

        News model = newsMapper.toModel(requestDto);

        assertNotNull(model);
        assertEquals(requestDto.getHeadline(), model.getHeadline());
        assertEquals(requestDto.getDescription(), model.getDescription());
        assertEquals(requestDto.getPublicationTime(), model.getPublicationTime());
    }
}
