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
    static Map<String, Note> notes = new HashMap<>();
    static GridPane mainGridPane;

    static MouseState mouseState = MouseState.CLICK;

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

    static void addNote(GridPane pane) throws IOException {


        int x = 0;
        int y = 0;
        String a = pane.hashCode() + "";

        try {
            for (int i=0; i<2; i++) {
                for (int j=0; j<3; j++) {
                    a += String.valueOf(j) + " " + String.valueOf(i);
                    if (!notes.keySet().contains(a)) {
                        x = j;
                        y = i;
                        throw new LOOPBREAKERRR();
                    }
                }
            }
        } catch (LOOPBREAKERRR e) {}
        Note note = new Note(pane, a);
        notes.put(a, note);
        pane.add(note.getPane(), x, y);
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


    @Override
    public void start(final Stage primaryStage) throws Exception{
        Group root = new Group();
        Scene scene = new Scene(root, 1300, 800);

        final Pane pane = FXMLLoader.load(getClass().getResource("MainWindow.fxml"));
        pane.minHeightProperty().bind(scene.heightProperty());
        pane.minWidthProperty().bind(scene.widthProperty());

        mainGridPane = (GridPane) pane.getChildren().get(0);
        mainGridPane.widthProperty().addListener(observable -> mainGridPane.resize(scene.getWidth(), scene.getHeight()));
        mainGridPane.heightProperty().addListener(observable -> mainGridPane.resize(scene.getWidth(), scene.getHeight()));
        mainGridPane.setGridLinesVisible(true);
        for (Node node : mainGridPane.getChildren())
            if (node instanceof GridPane)
                ((GridPane) node).setGridLinesVisible(true);

        for (int i=4; i<8; i++) {
            GridPane a = (GridPane) mainGridPane.getChildren().get(i);
            a.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    if (mouseState == MouseState.ADD) {
                        try {
                            addNote(a);
                        } catch (IOException breaka) {}
                    }
                }
            });
        }


        GridPane urgImp = getGridPaneFromMainGridPane(true, true, mainGridPane);

        addNote(urgImp);
        addNote(urgImp);


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
