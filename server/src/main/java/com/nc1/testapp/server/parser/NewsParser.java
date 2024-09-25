package com.nc1.testapp.server.parser;

import com.nc1.testapp.common.model.dto.NewsRequestDto;
import com.nc1.testapp.common.utils.AppConstants;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class NewsParser {
    public List<NewsRequestDto> parseNewsFromWebsite() {
        List<NewsRequestDto> newsList = new ArrayList<>();
        try {
            Document document = Jsoup.connect(AppConstants.SITE_FOR_PARSING).get();
            Elements newsBlocks = document.select(AppConstants.NEWS_BLOCKS_TO_SELECT);
            for (Element currentBlock : newsBlocks) {
                NewsRequestDto currentRequestDto = new NewsRequestDto();
                currentRequestDto.setHeadline(currentBlock
                        .select(AppConstants.NEWS_HEADLINE_HTML_TAG).text());
                currentRequestDto.setDescription(currentBlock
                        .select(AppConstants.NEWS_DESCRIPTION_HTML_TAG).text());
                currentRequestDto.setPublicationTime(LocalDateTime.now());
                newsList.add(currentRequestDto);
            }

        } catch (IOException exception) {
            log.error("An error occurred during news parsing process! Params: {}",
                    exception.getMessage());
        }
        return newsList;
    }
}
