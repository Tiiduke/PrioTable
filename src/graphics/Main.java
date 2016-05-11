package graphics;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ToolBar;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;


import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class Main extends Application {

    public static MouseState mouseState = MouseState.CLICK;

    private static HashMap<String, Note> noteHashMap = new HashMap<>();
    //Meetod child GridPane'ide saamine parentist vastavate paremeetrite järgi
    private GridPane getGridPaneFromMainGridPane(boolean urgent, boolean important, GridPane mainGridPane) {
        GridPane temp;
        int gridPaneIdentifier;
        /* Väga spagetine kood, mis sisaldab palju magic variable'id, kuid oli keeruline teha paremini
         * Võtab vanemast vastava GridPane objekti, lisab sellele vastavad kuularid suuruse muutmiseks ja hiirekliki tuvastamiseks
         */

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
    //Meetod ToolBaril olevatele nuppudele ligi pääsemiseks
    private Button getButtonFromMainToolBar(int number, ToolBar mainToolBar) {
        return (Button) mainToolBar.getItems().get(number);
    }
    //Meetod kõigile nuppudele ToolBaril funktsionaalsuse lisamiseks
    private void addButtonFunctionalityToToolBar (ToolBar toolBar) {
        //Jällegi spagetine kood, mis sisaldab n-ö magic variable'eid
        addFunctionalityToAddNote(getButtonFromMainToolBar(0, toolBar));
        addFunctionalityToDeleteNote(getButtonFromMainToolBar(1, toolBar));
        addFunctionalityToMarkDone(getButtonFromMainToolBar(2, toolBar));
    }
    //Kolm järgnevat meetodit on vastavatele nuppudele funktsionaalsuse lisamiseks
    private void addFunctionalityToAddNote (Button addNote) {
        addNote.setOnMouseClicked(observable -> mouseState = MouseState.ADD);
    }

    private void addFunctionalityToDeleteNote (Button deleteNote) {
        deleteNote.setOnMouseClicked(observable -> mouseState = MouseState.DELETE);
    }

    private void addFunctionalityToMarkDone (Button markDone) {
        markDone.setOnMouseClicked(observable -> mouseState = MouseState.MARK_DONE);
    }
    //Meetod GridPane'ile esimesse vabasse kohta uue märkme lisamiseks
    private void addNewNoteToGridPane(int gridPaneIndex, GridPane gridPane) {
        //Jällegi spagetine kood, mis sisaldab palju magic variable'eid
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
    //Meetod GridPane'ist vastava märkme lisamiseks
    private void deleteNoteFromGridPane(int gridPaneIndex, GridPane gridPane, int column, int row) {
        Note temp = noteHashMap.get(gridPaneIndex + " " + column + " " + row);
        noteHashMap.remove(gridPaneIndex + " " + column + " " + row);
        gridPane.getChildren().remove(temp.getPane());
    }
    //Meetod Note'is olevale Pane'ile funktsionaalsuse lisamiseks
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
        //Loeb vastavast fxml failist põhikujunduse ja seob selle suuruse stseeni suurusega
        final Pane pane = FXMLLoader.load(getClass().getResource("MainWindow.fxml"));
        pane.minHeightProperty().bind(scene.heightProperty());
        pane.minWidthProperty().bind(scene.widthProperty());

        //Saab peapaanist põhilise GridPane elemendi, lisab sellele suurust muutvad kuularid
        //Muudab selle ja kõigi child GridPane elementide jooned nähtavaks
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
        //Võtab põhipaanist ToolBari ja lisab sellele nuppude funktsionaalsuse
        ToolBar mainToolBar = (ToolBar) pane.getChildren().get(1);
        addButtonFunctionalityToToolBar(mainToolBar);

        //Vormistab stseeni ja näitab lava
        root.getChildren().addAll(pane);

        primaryStage.setTitle("Priority Table");
        primaryStage.setScene(scene);

        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
