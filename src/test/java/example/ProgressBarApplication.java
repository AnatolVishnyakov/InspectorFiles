package example;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import ru.inspector_files.controller.snapshot.ProcessController;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.Callable;

public class ProgressBarApplication extends Application {
    private Stage primaryStage;
    private double xOffset = 0;
    private double yOffset = 0;

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;

        initRootLayout();
    }

    private void initRootLayout() {
        try {
            // Загружаем корневой макет из fxml файла
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/snapshot/scan/FolderScanProgressComponent.fxml"));
            loader.setControllerFactory(param -> {
                Callable<?> controllerCallable = (Callable<ProcessController>) () -> new ProcessController(new File("C:\\Program Files (x86)"));
                try {
                    return controllerCallable.call();
                } catch (Exception ex) {
                    throw new IllegalStateException(ex);
                }
            });

            Pane rootLayout = loader.load();
            primaryStage.initStyle(StageStyle.TRANSPARENT);

            rootLayout.setOnMousePressed(event -> {
                xOffset = event.getSceneX();
                yOffset = event.getSceneY();
            });
            rootLayout.setOnMouseDragged(event -> {
                primaryStage.setX(event.getScreenX() - xOffset);
                primaryStage.setY(event.getScreenY() - yOffset);
            });

            // Отображаем сцену, содержащую корневой макет
            Scene scene = new Scene(rootLayout);
            scene.setFill(Color.TRANSPARENT);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}