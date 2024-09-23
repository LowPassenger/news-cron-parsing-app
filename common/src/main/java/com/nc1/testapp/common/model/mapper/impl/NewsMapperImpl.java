package com.nc1.testapp.common.model.mapper.impl;

import com.nc1.testapp.common.model.News;
import com.nc1.testapp.common.model.dto.NewsRequestDto;
import com.nc1.testapp.common.model.dto.NewsResponseDto;
import com.nc1.testapp.common.model.mapper.MapperToDto;
import com.nc1.testapp.common.model.mapper.MapperToModel;
import org.springframework.stereotype.Component;

@Component
public class NewsMapperImpl implements MapperToDto<NewsResponseDto, News>,
        MapperToModel<News, NewsRequestDto> {

    @Override
    public NewsResponseDto toDto(News news) {
        NewsResponseDto responseDto = new NewsResponseDto();
        responseDto.setId(news.getId());
        responseDto.setHeadline(news.getHeadline());
        responseDto.setDescription(news.getDescription());
        responseDto.setPublicationTime(news.getPublicationTime());
        return responseDto;
    }

    @Override
    public News toModel(NewsRequestDto newsRequestDto) {
        News news = new News();
        news.setHeadline(newsRequestDto.getHeadline());
        news.setDescription(newsRequestDto.getDescription());
        news.setPublicationTime(newsRequestDto.getPublicationTime());
        return news;
    }
}
