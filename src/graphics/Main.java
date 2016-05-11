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

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.StringJoiner;

import static java.lang.Double.MAX_VALUE;

public class Main extends Application {

    public static MouseState mouseState = MouseState.CLICK;

    private static HashMap<String, Note> noteHashMap = new HashMap<>();

    private GridPane getGridPaneFromMainGridPane(boolean urgent, boolean important, GridPane mainGridPane) {
        GridPane temp;
        int gridPaneIdentifier;
        if (urgent && important) {
            temp = (GridPane) mainGridPane.getChildren().get(4);
            gridPaneIdentifier = 0;
        }
        else if (urgent) {
            temp = (GridPane) mainGridPane.getChildren().get(5);
            gridPaneIdentifier = 1;
        }
        else if (important) {
            temp = (GridPane) mainGridPane.getChildren().get(6);
            gridPaneIdentifier = 2;
        }
        else {
            temp = (GridPane) mainGridPane.getChildren().get(7);
            gridPaneIdentifier = 3;
        }
        temp.widthProperty().addListener(observable -> temp.resize(mainGridPane.getWidth()/2 - 25, mainGridPane.getHeight()/2 - 25));
        temp.heightProperty().addListener(observable -> temp.resize(mainGridPane.getWidth()/2 - 25, mainGridPane.getHeight()/2 - 25));
        temp.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (mouseState == MouseState.ADD) {
                    addNewNoteToGridPane(gridPaneIdentifier, temp);
                    mouseState = MouseState.CLICK;
                }
            }
        });
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

    private void addNewNoteToGridPane(int gridPaneIndex, GridPane gridPane) {

        String temp;
        try {
            for (int i = 0; i < 2; i++)
                for(int j = 0; j < 3; j++) {
                    temp = gridPaneIndex + " " + j + " " + i;
                    if (noteHashMap.get(temp) == null) {
                        Note note = new Note(gridPane);
                        addListenersToPane(note.getPane(), gridPane);
                        noteHashMap.put(temp, note);
                        gridPane.add(note.getPane(), j, i);
                        return;
                    }
                }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void deleteNoteFromGridPane(int gridPaneIndex, GridPane gridPane, int column, int row) {
        Note temp = noteHashMap.get(gridPaneIndex + " " + column + " " + row);
        noteHashMap.remove(gridPaneIndex + " " + column + " " + row);
        gridPane.getChildren().remove(temp.getPane());
    }

    private void addListenersToPane(Pane pane, GridPane gridPane) {
        pane.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {

                if (mouseState == MouseState.DELETE) {
                    for (Map.Entry<String, Note> entry : noteHashMap.entrySet()) {
                        if (entry.getValue().getPane() == pane) {
                            String[] temp = entry.getKey().split(" ");
                            deleteNoteFromGridPane(Integer.parseInt(temp[0]), gridPane, Integer.parseInt(temp[1]), Integer.parseInt(temp[2]));
                        }
                    }
                    mouseState = MouseState.CLICK;
                } else if (mouseState == MouseState.MARK_DONE) {
                    for (Map.Entry<String, Note> entry : noteHashMap.entrySet()) {
                        if (entry.getValue().getPane() == pane) {
                            String[] temp = entry.getKey().split(" ");
                            Note tempNote = entry.getValue();
                            tempNote.writeNoteToMarkDoneFile();
                            deleteNoteFromGridPane(Integer.parseInt(temp[0]), gridPane, Integer.parseInt(temp[1]), Integer.parseInt(temp[2]));
                        }
                    }
                    mouseState = MouseState.CLICK;
                }
            }
        });
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

        GridPane[] childGridPanes = new GridPane[] {
                getGridPaneFromMainGridPane(true, true, mainGridPane),
                getGridPaneFromMainGridPane(true, false, mainGridPane),
                getGridPaneFromMainGridPane(false, true, mainGridPane),
                getGridPaneFromMainGridPane(false, false, mainGridPane)
        };

        //Näidis lisamisest ja kustutamisest
        /*deleteNoteFromGridPane(0, urgImp, 2, 0);
        addNewNoteToGridPane(0, urgImp);
        urgImp index = 0
        urgNotImp index = 1
        notUrgImp index = 2
        notUrgNotImp index = 3
        */

        addNewNoteToGridPane(0, childGridPanes[0]);
        addNewNoteToGridPane(0, childGridPanes[0]);
        addNewNoteToGridPane(0, childGridPanes[0]);

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
