package graphics;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToolBar;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.StringJoiner;

import static java.lang.Double.MAX_VALUE;

public class Main extends Application {

    private static MouseState mouseState = MouseState.CLICK;

    private static HashMap<String, Note> noteHashMap = new HashMap<>();

    private GridPane getGridPaneFromMainGridPane(boolean urgent, boolean important, GridPane mainGridPane) {
        GridPane temp;
        if (urgent && important)
            temp = (GridPane) mainGridPane.getChildren().get(4);
        else if (urgent)
            temp = (GridPane) mainGridPane.getChildren().get(5);
        else if (important)
            temp = (GridPane) mainGridPane.getChildren().get(6);
        else
            temp = (GridPane) mainGridPane.getChildren().get(7);
        temp.widthProperty().addListener(observable -> temp.resize(mainGridPane.getWidth()/2 - 25, mainGridPane.getHeight()/2 - 25));
        temp.heightProperty().addListener(observable -> temp.resize(mainGridPane.getWidth()/2 - 25, mainGridPane.getHeight()/2 - 25));
        return temp;
    }

    private Button getButtonFromMainToolBar(int number, ToolBar mainToolBar) {
        return (Button) mainToolBar.getItems().get(number);
    }

    private void addButtonFunctionalityToToolBar (ToolBar toolBar) {
        addFunctionalityToAddNote(getButtonFromMainToolBar(0, toolBar));
        addFunctionalityToDeleteNote(getButtonFromMainToolBar(1, toolBar));
        addFunctionalityToMarkDone(getButtonFromMainToolBar(2, toolBar));
    }

    private void addFunctionalityToAddNote (Button addNote) {
        addNote.setOnMouseClicked(observable -> mouseState = MouseState.ADD);
    }

    private void addFunctionalityToDeleteNote (Button deleteNote) {
        deleteNote.setOnMouseClicked(observable -> mouseState = MouseState.DELETE);
    }

    private void addFunctionalityToMarkDone (Button markDone) {
        markDone.setOnMouseClicked(observable -> mouseState = MouseState.MARK_DONE);
    }

    private void addNewNoteToGridPane(int gridPaneIndex, GridPane gridPane) throws IOException{

        String temp;
        for (int i = 0; i < 2; i++)
            for(int j = 0; j < 3; j++) {
                temp = gridPaneIndex + " " + j + " " + i;
                if (noteHashMap.get(temp) == null) {
                    Note note = new Note(gridPane);
                    noteHashMap.put(temp, note);
                    gridPane.add(note.getPane(), j, i);
                    return;
                }
            }
    }

    private void deleteNoteFromGridPane(int gridPaneIndex, GridPane gridPane, int column, int row) {
        Note temp = noteHashMap.get(gridPaneIndex + " " + column + " " + row);
        noteHashMap.remove(gridPaneIndex + " " + column + " " + row);
        gridPane.getChildren().remove(temp.getPane());
    }


    @Override
    public void start(final Stage primaryStage) throws Exception{
        Group root = new Group();
        Scene scene = new Scene(root, 1300, 800);

        final Pane pane = FXMLLoader.load(getClass().getResource("MainWindow.fxml"));
        pane.minHeightProperty().bind(scene.heightProperty());
        pane.minWidthProperty().bind(scene.widthProperty());

        GridPane mainGridPane = (GridPane) pane.getChildren().get(0);
        mainGridPane.widthProperty().addListener(observable -> mainGridPane.resize(scene.getWidth(), scene.getHeight()));
        mainGridPane.heightProperty().addListener(observable -> mainGridPane.resize(scene.getWidth(), scene.getHeight()));
        mainGridPane.setGridLinesVisible(true);
        for (Node node : mainGridPane.getChildren())
            if (node instanceof GridPane)
                ((GridPane) node).setGridLinesVisible(true);

        //Näidis lisamisest ja kustutamisest
        /*deleteNoteFromGridPane(0, urgImp, 2, 0);
        addNewNoteToGridPane(0, urgImp);
        urgImp index = 0
        urgNotImp index = 1
        notUrgImp index = 2
        notUrgNotImp index = 3
        */

        ToolBar mainToolBar = (ToolBar) pane.getChildren().get(1);
        addButtonFunctionalityToToolBar(mainToolBar);

        root.getChildren().addAll(pane);
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(scene);

        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
