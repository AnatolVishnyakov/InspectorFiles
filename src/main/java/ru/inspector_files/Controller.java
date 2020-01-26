package ru.inspector_files;

import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import ru.inspector_files.service.DocumentService;
import ru.inspector_files.service.DocumentServiceImpl;
import ru.inspector_files.ui.utils.InterfaceUtils;

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
    private Button buttonScan;
    @FXML
    public Button buttonStop;
    @FXML
    private ChoiceBox<File> choiceBoxDisks;
    @FXML
    private Label labelNumberOfFiles;
    @FXML
    private TableView<FileInfo> fileInfoTable;
    @FXML
    private TableColumn<FileInfo, String> fileName;
    @FXML
    private TableColumn<FileInfo, Integer> countOfNumber;
    private Map<String, Integer> cache = new HashMap<>();
    private DocumentService service = new DocumentServiceImpl();

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
        fileName.setCellValueFactory(cellData -> cellData.getValue().fileNameProperty());
        countOfNumber.setCellValueFactory(cellData -> cellData.getValue().numberOfFilesProperty().asObject());
        // Добавление в таблицу данных из наблюдаемого списка
        fileInfoTable.setItems(Main.getFileInfos());
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
        InterfaceUtils.updateElement(() -> {
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
                if (currentFile.isDirectory()) {
                    traversalDisk(currentFile);
                } else {
                    service.create(currentFile);

                    InterfaceUtils.updateElement(() -> {
                        String value = String.format("%d | %s", counter.incrementAndGet(), currentFile.getAbsoluteFile());
                        status.set(value);
                    });
                }
            }
        }
    }
}
