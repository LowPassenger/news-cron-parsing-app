package com.nc1.testapp.newscronparsingapp.controller;

import com.nc1.testapp.newscronparsingapp.model.News;
import com.nc1.testapp.newscronparsingapp.service.NewsService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/news")
public class NewsController {
    @Autowired
    private NewsService newsService;

    @GetMapping("/period")
    public List<News> getNewsForPeriod(@RequestParam String period) {
        return newsService.getNewsForPeriod(period);
    }

    @PostMapping
    public void addNews(@RequestBody News news) {
        newsService.saveNews(news);
    }

    @DeleteMapping("/{id}")
    public void deleteNews(@PathVariable Long id) {
        newsService.deleteNews(id);
    }
}
