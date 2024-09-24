package com.nc1.testapp.client;

import com.nc1.testapp.client.view.JavaFXModule;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication(exclude = {WebMvcAutoConfiguration.class},
        scanBasePackages = {"com.nc1.testapp.common", "com.nc1.testapp.client",
                "com.nc1.testapp.server"})
public class NewsCronClientApplication extends Application {

    private ConfigurableApplicationContext context;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void init() {
        SpringApplicationBuilder builder =
                new SpringApplicationBuilder(NewsCronClientApplication.class);
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
