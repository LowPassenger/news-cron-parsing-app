package com.nc1.testapp.client.view;

import com.nc1.testapp.client.service.ClientNewsService;
import com.nc1.testapp.common.model.dto.NewsResponseDto;
import com.nc1.testapp.common.model.mapper.impl.NewsMapperImpl;
import com.nc1.testapp.common.utils.AppConstants;
import java.util.List;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.anyString;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.autoconfigure.web.client.RestTemplateAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import static org.testfx.util.WaitForAsyncUtils.waitForFxEvents;

@ExtendWith(ApplicationExtension.class)
@ExtendWith(MockitoExtension.class)
@SpringBootTest(classes = {JavaFXModuleTest.class,
        RestTemplateAutoConfiguration.class})
class JavaFXModuleTest {

    @InjectMocks
    private JavaFXModule javaFXModule;

    @Mock
    private ClientNewsService newsService;

    @Mock
    private NewsMapperImpl mapper;

    private List<NewsResponseDto> mockNewsList;

    private Stage stage;

    @Start
    public void start(Stage stage) {
        this.stage = stage;
        javaFXModule.start(stage);
    }

    @BeforeEach
    void setUp() {
        NewsResponseDto news1 = new NewsResponseDto();
        news1.setHeadline("Headline 1");
        news1.setDescription("Description 1");

        NewsResponseDto news2 = new NewsResponseDto();
        news2.setHeadline("Headline 2");
        news2.setDescription("Description 2");

        mockNewsList = List.of(news1, news2);
    }

    @Test
    void testStartStageAndInitialNewsLoading(FxRobot robot) {
        String expected = AppConstants.URL_REQUEST_MORNING_PERIOD_NAME + " (06:00 - 10:59)";

        when(newsService.getNewsByPeriod(anyString())).thenReturn(mockNewsList);

        waitForFxEvents();

        ComboBox<String> timePeriodComboBox = robot.lookup(".combo-box").queryAs(ComboBox.class);
        assertNotNull(timePeriodComboBox);
        assertEquals(expected, timePeriodComboBox.getValue());

        Label headlineLabel = robot.lookup(".label").nth(0).queryAs(Label.class);
        Label newsLabel = robot.lookup(".label").nth(1).queryAs(Label.class);

        waitForFxEvents();

        assertEquals("Headline 1", headlineLabel.getText());
        assertEquals("Description 1", newsLabel.getText());

        verify(newsService).getNewsByPeriod(AppConstants.URL_REQUEST_MORNING_PERIOD_NAME);
    }

    @Test
    void testNextButton(FxRobot robot) {
        when(newsService.getNewsByPeriod(anyString())).thenReturn(mockNewsList);

        Button nextButton = robot.lookup(".button").nth(1).queryAs(Button.class);
        robot.clickOn(nextButton);

        waitForFxEvents();

        Label headlineLabel = robot.lookup(".label").nth(0).queryAs(Label.class);
        Label newsLabel = robot.lookup(".label").nth(1).queryAs(Label.class);

        assertEquals("Headline 2", headlineLabel.getText());
        assertEquals("Description 2", newsLabel.getText());

        assertTrue(nextButton.isDisabled());
    }

    @Test
    void testPreviousButton(FxRobot robot) {
        when(newsService.getNewsByPeriod(anyString())).thenReturn(mockNewsList);

        Button nextButton = robot.lookup(".button").nth(1).queryAs(Button.class);
        robot.clickOn(nextButton);

        waitForFxEvents();

        Button prevButton = robot.lookup(".button").nth(0).queryAs(Button.class);
        robot.clickOn(prevButton);

        waitForFxEvents();

        Label headlineLabel = robot.lookup(".label").nth(0).queryAs(Label.class);
        Label newsLabel = robot.lookup(".label").nth(1).queryAs(Label.class);

        waitForFxEvents();

        assertEquals("Headline 1", headlineLabel.getText());
        assertEquals("Description 1", newsLabel.getText());

        assertTrue(prevButton.isDisabled());
    }

    @Test
    void testPeriodSelection(FxRobot robot) {
        when(newsService.getNewsByPeriod(anyString())).thenReturn(mockNewsList);

        ComboBox<String> timePeriodComboBox = robot.lookup(".combo-box").queryAs(ComboBox.class);
        robot.clickOn(timePeriodComboBox);

        waitForFxEvents();

        robot.clickOn("day (11.00 - 16.59)");

        verify(newsService).getNewsByPeriod("day");

        Label headlineLabel = robot.lookup(".label").nth(0).queryAs(Label.class);
        Label newsLabel = robot.lookup(".label").nth(1).queryAs(Label.class);

        assertEquals("Headline 1", headlineLabel.getText());
        assertEquals("Description 1", newsLabel.getText());
    }
}
