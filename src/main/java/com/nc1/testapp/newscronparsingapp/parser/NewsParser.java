package com.nc1.testapp.newscronparsingapp.parser;

import com.nc1.testapp.newscronparsingapp.model.News;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class NewsParser {
    public List<News> parseNewsFromWebsite() throws IOException {
        List<News> newsList = new ArrayList<>();
        return newsList;
    }
}
