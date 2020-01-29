package ru.inspector_files.controller;

import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import ru.inspector_files.InspectorFileApplication;
import ru.inspector_files.model.Document;
import ru.inspector_files.service.DocumentService;
import ru.inspector_files.service.DocumentServiceImpl;
import ru.inspector_files.to.DocumentTo;
import ru.inspector_files.ui.InterfaceExecutor;

import java.io.File;
import java.net.URL;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

public class Controller implements Initializable {
    private static final String WINDOWS_FOLDER = "C:/Windows";
    private static final String PROGRAM_FILES = "C:/Program Files";
    private ExecutorService executorService = Executors.newSingleThreadExecutor();
    private SimpleStringProperty status = new SimpleStringProperty(this, "value");
    private AtomicInteger counter = new AtomicInteger(0);
    private volatile boolean isStop;
    @FXML
    private TableView<DocumentTo> fileInfoTable;
    @FXML
    private TableColumn<DocumentTo, String> pathColumn;
    @FXML
    private TableColumn<DocumentTo, String> nameColumn;
    @FXML
    private TableColumn<DocumentTo, Integer> levelColumn;
    @FXML
    private Button buttonScan;
    @FXML
    public Button buttonStop;
    @FXML
    private ChoiceBox<File> choiceBoxDisks;
    @FXML
    private Label labelNumberOfFiles;
    @FXML
    private ProgressBar sizeOfDisk;
    private Map<String, Integer> cache = new HashMap<>();
    private DocumentService service = new DocumentServiceImpl();
    private InspectorFileApplication application;

    private void initializeCatalogScan() {
        ObservableList<File> catalogs = FXCollections.observableArrayList(File.listRoots());
        catalogs.add(new File(WINDOWS_FOLDER));
        catalogs.add(new File(PROGRAM_FILES));
        choiceBoxDisks.setItems(catalogs);
        choiceBoxDisks.setValue(catalogs.get(0));
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializeCatalogScan();
        buttonStop.setDisable(true);
        labelNumberOfFiles.textProperty().bind(Bindings.convert(status));
        sizeOfDisk.setProgress(0);

        pathColumn.setCellValueFactory(cellData -> cellData.getValue().absolutePathProperty());
        levelColumn.setCellValueFactory(cellData -> cellData.getValue().levelProperty().asObject());
        nameColumn.setCellValueFactory(cellData -> cellData.getValue().fileNameProperty());

        new Timer().schedule(
                new TimerTask() {
                    @Override
                    public void run() {
                        ScrollBar scrollBar = (ScrollBar) fileInfoTable.lookup(".scroll-bar:vertical");
                        double scrollBarValue = scrollBar.getValue();
//                        fileInfoTable.sele

                        ObservableList<DocumentTo> storage = application.getStorage();
                        storage.clear();
                        fileInfoTable.setItems(storage);
                        List<Document> documents = service.getAllRootLevel();
                        documents.forEach(document -> storage.add(new DocumentTo(document)));

                        scrollBar.setValue(scrollBarValue);
                        System.out.println("Call refresh");
                    }
                }, 5_000, 5_000);
    }

    @FXML
    public void onClickScan() {
        isStop = false;
        buttonScan.setDisable(true);
        buttonStop.setDisable(isStop);
        counter.set(0);
        executorService.execute(() -> {
            File disk = choiceBoxDisks.getValue();
            Arrays.stream(Objects.requireNonNull(disk.listFiles())).filter(File::isDirectory)
                    .forEach(file -> cache.put(file.getAbsolutePath(), 0));
            traversalDisk(disk);
            buttonScan.setDisable(isStop);
            buttonStop.setDisable(true);
        });
    }

    @FXML
    public void onClickStop() {
        isStop = true;
        InterfaceExecutor.execute(() -> {
            buttonScan.setDisable(false);
            buttonStop.setDisable(isStop);
            status.set("Остановлен процесс сканирования...");
        });
    }

    private void traversalDisk(File file) {
        File[] files = file.listFiles();
        if (files != null) {
            for (File currentFile : files) {
                if (isStop) {
                    return;
                }

                service.create(currentFile);
                if (currentFile.isDirectory()) {
                    traversalDisk(currentFile);
                } else {

                    InterfaceExecutor.execute(() -> {
                        String value = String.format("%d | %s", counter.incrementAndGet(), currentFile.getAbsoluteFile());
                        status.set(value);
                    });
                }
            }
        }
    }

    @FXML
    public void onClose() {
        Platform.exit();
    }

    public void setInspectorFileApplication(InspectorFileApplication application) {
        this.application = application;
    }
}
