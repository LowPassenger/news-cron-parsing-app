package com.nc1.testapp.client.controller;

import com.nc1.testapp.common.model.News;
import com.nc1.testapp.common.model.dto.NewsResponseDto;
import com.nc1.testapp.common.model.mapper.impl.NewsMapperImpl;
import com.nc1.testapp.common.utils.DayPeriodLimitsEnum;
import com.nc1.testapp.server.service.NewsService;
import java.util.List;
import java.util.stream.Collectors;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class NewsControllerFX {
    @Autowired
    private NewsService newsService;

    @Autowired
    private NewsMapperImpl mapper;

    @FXML
    private Label newsTitleLabel;
    @FXML
    private Label newsDescriptionLabel;
    @FXML
    private Button previousButton;
    @FXML
    private Button nextButton;
    @FXML
    private ChoiceBox<String> periodChoiceBox;

    private List<NewsResponseDto> newsList;
    private int currentIndex = 0;

    @FXML
    private void initialize() {
        periodChoiceBox.getItems().addAll(DayPeriodLimitsEnum.MORNING.name(),
                DayPeriodLimitsEnum.DAY.name(), DayPeriodLimitsEnum.EVENING.name());
        periodChoiceBox.setOnAction(event -> loadNewsForSelectedPeriod(periodChoiceBox.getValue()));
    }

    private void loadNewsForSelectedPeriod(String period) {
        List<News> news = newsService.getNewsForPeriod(period.toLowerCase());
        newsList = news.stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
        displayNews();
    }

    private void displayNews() {
        if (newsList != null && !newsList.isEmpty()) {
            NewsResponseDto currentNews = newsList.get(currentIndex);
            newsTitleLabel.setText(currentNews.getHeadline());
            newsDescriptionLabel.setText(currentNews.getDescription());
        }
    }

    @FXML
    private void showNextNews() {
        if (newsList != null && currentIndex < newsList.size() - 1) {
            currentIndex++;
            displayNews();
        }
    }

    @FXML
    private void showPreviousNews() {
        if (newsList != null && currentIndex > 0) {
            currentIndex--;
            displayNews();
        }
    }
}
