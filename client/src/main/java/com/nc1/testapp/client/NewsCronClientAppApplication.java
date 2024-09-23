package com.nc1.testapp.client;

import com.nc1.testapp.client.controller.NewsControllerFX;
import com.nc1.testapp.client.view.JavaFXModule;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication(scanBasePackages = {"com.nc1.testapp.common", "com.nc1.testapp.client"})
public class NewsCronClientAppApplication extends Application {

    private ConfigurableApplicationContext context;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void init() {
        SpringApplicationBuilder builder =
                new SpringApplicationBuilder(NewsCronClientAppApplication.class);
        context = builder.run();
    }

    @Override
    public void start(Stage stage) {
        JavaFXModule fxController = context.getBean(JavaFXModule.class);
        fxController.start(stage);
    }

    public void stop() {
        context.close();
        Platform.exit();
    }
}
