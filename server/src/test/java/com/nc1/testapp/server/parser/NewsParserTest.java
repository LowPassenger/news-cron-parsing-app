package com.nc1.testapp.server.parser;

import com.nc1.testapp.common.model.dto.NewsRequestDto;
import com.nc1.testapp.common.utils.AppConstants;
import java.io.IOException;
import java.util.List;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.when;

class NewsParserTest {

    private NewsParser newsParser;

    @BeforeEach
    void setUp() {
        newsParser = new NewsParser();
    }

    @Test
    void testParseNewsFromWebsiteSuccess() throws IOException {
        String mockHtml = "<div class='news-block'>" +
                "<h1 class='headline'>Test Headline</h1>" +
                "<p class='description'>Test Description</p>" +
                "</div>";

        Document mockDocument = mock(Document.class);
        Elements mockNewsBlocks = mock(Elements.class);
        Element mockNewsBlock = mock(Element.class);
        Elements mockHeadlineElements = mock(Elements.class);
        Elements mockDescriptionElements = mock(Elements.class);
        Connection mockConnection = mock(Connection.class);

        try (MockedStatic<Jsoup> jsoupMock = mockStatic(Jsoup.class)) {
            jsoupMock.when(() -> Jsoup.connect(anyString())).thenReturn(mockConnection);
            when(mockConnection.get()).thenReturn(mockDocument);

            when(mockDocument.select(AppConstants.NEWS_BLOCKS_TO_SELECT))
                    .thenReturn(mockNewsBlocks);
            when(mockNewsBlocks.iterator()).thenReturn(List.of(mockNewsBlock).iterator());

            when(mockNewsBlock.select(AppConstants.NEWS_HEADLINE_HTML_TAG))
                    .thenReturn(mockHeadlineElements);
            when(mockHeadlineElements.text()).thenReturn("Test Headline");

            when(mockNewsBlock.select(AppConstants.NEWS_DESCRIPTION_HTML_TAG))
                    .thenReturn(mockDescriptionElements);
            when(mockDescriptionElements.text()).thenReturn("Test Description");

            List<NewsRequestDto> result = newsParser.parseNewsFromWebsite();

            assertEquals(1, result.size());
            NewsRequestDto news = result.get(0);
            assertEquals("Test Headline", news.getHeadline());
            assertEquals("Test Description", news.getDescription());
            assertNotNull(news.getPublicationTime());
        }
    }

    @Test
    void testParseNewsFromWebsiteFailure() {
        Connection mockConnection = mock(Connection.class);

        try (MockedStatic<Jsoup> jsoupMock = mockStatic(Jsoup.class)) {
            jsoupMock.when(() -> Jsoup.connect(anyString())).thenReturn(mockConnection);
            when(mockConnection.get()).thenThrow(new IOException("Connection failed"));

            List<NewsRequestDto> result = newsParser.parseNewsFromWebsite();

            assertTrue(result.isEmpty());
        } catch (IOException e) {
            System.out.println("Test OK");
        }
    }
}