package com.nc1.testapp.newscronparsingapp;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class NewsCronParsingAppApplication extends Application {

    private ConfigurableApplicationContext context;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void init() {
        SpringApplicationBuilder builder =
                new SpringApplicationBuilder(NewsCronParsingAppApplication.class);
        context = builder.run();
    }

    @Override
    public void start(Stage stage) {
        NewsControllerFX fxController = context.getBean(NewsControllerFX.class);
        fxController.start(stage);
    }

    public void stop() {
        context.close();
        Platform.exit();
    }
}
