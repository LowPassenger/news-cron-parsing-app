package com.nc1.testapp.client.view;

import com.nc1.testapp.client.service.ClientNewsService;
import com.nc1.testapp.common.model.dto.NewsResponseDto;
import com.nc1.testapp.common.model.mapper.impl.NewsMapperImpl;
import com.nc1.testapp.common.utils.AppConstants;
import com.nc1.testapp.common.utils.DayPeriodLimitsEnum;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class JavaFXModule {
    @Autowired
    private ClientNewsService newsService;

    @Autowired
    private NewsMapperImpl mapper;

    private List<NewsResponseDto> commonNewsList;

    private int currentIndex = 0;

    public void start(Stage stage) {
        stage.setTitle("News Parser");

        commonNewsList = newsService
                .getNewsByPeriod(AppConstants.URL_REQUEST_MORNING_PERIOD_NAME);;

        BorderPane root = new BorderPane();

        ComboBox<String> timePeriodComboBox = new ComboBox<>();
        timePeriodComboBox.getItems().addAll(getTimePeriodsOptions());
        timePeriodComboBox.setValue(getInformationAboutDefaultPeriod());
        root.setTop(timePeriodComboBox);

        Label headlineLabel = new Label();
        Label newsLabel = new Label();
        updateNewsLabels(headlineLabel, newsLabel);
        BorderPane newsPane = new BorderPane();
        newsPane.setTop(headlineLabel);
        newsPane.setCenter(newsLabel);
        root.setCenter(newsPane);

        Button prevButton = new Button("←");
        Button nextButton = new Button("→");
        updateButtonsState(prevButton, nextButton);

        BorderPane navPane = new BorderPane();
        navPane.setLeft(prevButton);
        navPane.setRight(nextButton);
        root.setBottom(navPane);

        prevButton.setOnAction(event -> {
            if (currentIndex > 0) {
                currentIndex--;
                newsLabel.setText(commonNewsList.get(currentIndex).getHeadline());
                updateButtonsState(prevButton, nextButton);
            }
        });

        nextButton.setOnAction(event -> {
            if (currentIndex < commonNewsList.size() - 1) {
                currentIndex++;
                newsLabel.setText(commonNewsList.get(currentIndex).getHeadline());
                updateButtonsState(prevButton, nextButton);
            }
        });

        timePeriodComboBox.setOnAction(event -> {
            String selectedPeriod = timePeriodComboBox.getValue();
            commonNewsList = newsService.getNewsByPeriod(selectedPeriod);
            currentIndex = 0;
            newsLabel.setText(commonNewsList.get(currentIndex).getHeadline());
            updateButtonsState(prevButton, nextButton);
        });

        Scene scene = new Scene(root, AppConstants.JAVAFX_WINDOW_WIDTH,
                AppConstants.JAVAFX_WINDOW_HEIGHT);
        stage.setScene(scene);
        stage.show();
    }

    private List<String> getTimePeriodsOptions() {
        List<String> outputList = new ArrayList<>();
        for (DayPeriodLimitsEnum period : DayPeriodLimitsEnum.values()) {
            LocalTime[] periodTimeLimits = period.getDayPeriodLimits();
            String periodInformation = period.name() + " (" + periodTimeLimits[0].toString()
                    + " - " + periodTimeLimits[1].toString() + ") ";
            outputList.add(periodInformation);
        }
        return outputList;
    }

    private String getInformationAboutDefaultPeriod() {
        DayPeriodLimitsEnum defaultPeriod = DayPeriodLimitsEnum.MORNING;
        LocalTime[] defaultPeriodTimeLimits = defaultPeriod.getDayPeriodLimits();
        return defaultPeriod.name().toLowerCase() + " (" + defaultPeriodTimeLimits[0].toString()
                + " - " + defaultPeriodTimeLimits[1].toString() + ") ";
    }

    private void updateNewsLabels(Label headlineLabel, Label newsLabel) {
        if (!commonNewsList.isEmpty()) {
            NewsResponseDto currentNews = commonNewsList.get(currentIndex);
            headlineLabel.setText(currentNews.getHeadline());
            newsLabel.setText(currentNews.getDescription());
        }
    }

    private void updateButtonsState(Button prevButton, Button nextButton) {
        prevButton.setDisable(currentIndex == 0);
        nextButton.setDisable(currentIndex == commonNewsList.size() - 1);
    }
}
