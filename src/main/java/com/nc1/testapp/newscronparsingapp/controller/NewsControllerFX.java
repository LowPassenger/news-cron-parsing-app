package com.nc1.testapp.newscronparsingapp.controller;

import com.nc1.testapp.newscronparsingapp.model.dto.NewsResponseDto;
import com.nc1.testapp.newscronparsingapp.model.mapper.MapperToDto;
import com.nc1.testapp.newscronparsingapp.model.mapper.impl.NewsMapperImpl;
import com.nc1.testapp.newscronparsingapp.service.NewsService;
import com.nc1.testapp.newscronparsingapp.utils.DayPeriodLimitsEnum;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class NewsControllerFX {

    @Autowired
    private NewsService newsService;

    private NewsMapperImpl mapper;

    private List<NewsResponseDto> responseDtoList;

    private int currentIndex = 0;

    public void start(Stage stage) {
        stage.setTitle("News Parser");

        responseDtoList = newsService.getTodayNews()
                .stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());

        BorderPane root = new BorderPane();

        ComboBox<String> timePeriodComboBox = new ComboBox<>();
        timePeriodComboBox.getItems().addAll(getInformationAboutTimePeriods());

    }

    private String getInformationAboutTimePeriods() {
        StringBuilder periodsInformation = new StringBuilder();
        for (DayPeriodLimitsEnum period : DayPeriodLimitsEnum.values()) {
            LocalTime[] periodTimeLimits = period.getDayPeriodLimits();
            periodsInformation.append(period.name())
                    .append(" (")
                    .append(periodTimeLimits[0].toString())
                    .append(" - ")
                    .append(periodTimeLimits[1].toString())
                    .append(") ");
        }
        return periodsInformation.toString();
    }
}
