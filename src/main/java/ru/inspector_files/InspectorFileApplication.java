package ru.inspector_files;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import ru.inspector_files.to.DocumentTo;

import java.io.IOException;

public class InspectorFileApplication extends Application {
    private ObservableList<DocumentTo> storage = FXCollections.observableArrayList();
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
            loader.setLocation(getClass().getResource("/view/RootLayout.fxml"));
            AnchorPane rootLayout = loader.load();
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

    /**
     * Возвращает главную сцену
     */
    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public ObservableList<DocumentTo> getStorage() {
        return storage;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
