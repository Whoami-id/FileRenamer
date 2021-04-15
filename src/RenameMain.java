
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/*
 * Grober Aufbau einer JavaFX Anwendung
 * 1. Stage
 * 2. Scene
 * 3. evtl. Layout Container - HBox, VBox, FlowPane, BordePane
 * 4. Button, Label, Tabellen, VBox -> Elemente etc.
 */

public class RenameMain extends Application {

    // Variablen
    private ListView<String> listView; // UI Control Element
    private List<String> nameFiles; // Namen der Dateien gespeichert

    private ObservableList<String> fileNameList; // Namen der Dateien gepsichert

    private List<File> fileList;

    private String newFileName;

    @Override
    public void start(final Stage primaryStage) throws Exception {

        final BorderPane root = new BorderPane();
        final Scene scene = new Scene(root, 400, 400);

        // Eingabe...
        final TextField textField = new TextField();
        final Label label = new Label("Neuer Dateiname:");
        final Button button = new Button("Rename");

        final VBox vBox = new VBox(10);
        vBox.setAlignment(Pos.CENTER);
        vBox.getChildren().addAll(label, textField, button);

        root.setLeft(vBox);
        BorderPane.setMargin(vBox, new Insets(0, 10, 0, 10));

        button.setOnAction(event -> {
            if (textField.getText() != null && !textField.getText().isEmpty()) {
                newFileName = textField.getText();
            }

            if (fileList != null) {
                int i = 0;
                for (final File file : fileList) {
                    i++;
                    // String name = file.getName();
                    // System.out.println(name);

                    final String newName = newFileName + i + ".jpg";
                    // C:\Users\christian\Bilder
                    final String path = "/Users/christian/Desktop/Bilder";
                    final String newPath = path + "/" + newName;

                    final File newFile = new File(newPath);
                    file.renameTo(newFile);

                    // ListView
                    final String newFileName = newFile.getName();
                    fileNameList.removeAll(nameFiles);
                    fileNameList.add(newFileName);

                    listView.refresh();
                }
            }
        });

        // Menü 3.Schritte MenuBar -> Menu -> MenuItem
        final MenuBar menuBar = new MenuBar();
        root.setTop(menuBar);

        final Menu menu = new Menu("Datei");

        final MenuItem item1 = new MenuItem("Öffnen");
        final MenuItem item2 = new MenuItem("Exit");

        menu.getItems().addAll(item1, item2);
        menuBar.getMenus().add(menu);

        // ListView
        listView = new ListView<>();
        root.setCenter(listView);

        nameFiles = new ArrayList<>();

        // Öffnen
        item1.setOnAction(event -> {
            final FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("JPG", "*.jpg"));
            fileList = fileChooser.showOpenMultipleDialog(primaryStage);

            if (fileList != null) {
                for (final File file : fileList) {
                    // System.out.println(file.getName());
                    // System.out.println(file.getPath());
                    final String fileName = file.getName();
                    nameFiles.add(fileName);
                }
                fileNameList = FXCollections.observableArrayList(nameFiles);
                listView.setItems(fileNameList);
            }

        });

        // Exit
        item2.setOnAction(event -> Platform.exit());

        primaryStage.setScene(scene);
        primaryStage.setTitle("Datei umbenennen");
        primaryStage.show();

    }

    public static void main(final String[] args) {
        launch(args);
    }

}
