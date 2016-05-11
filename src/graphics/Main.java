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
import javafx.scene.control.ToolBar;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;


import javax.tools.Tool;

import static java.lang.Double.MAX_VALUE;

public class Main extends Application {

    MouseState mouseState = MouseState.CLICK;

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

    @Override
    public void start(final Stage primaryStage) throws Exception{
        //Loome stseeni juure ja stseeni
        Group root = new Group();
        Scene scene = new Scene(root, 1300, 800);

        //Loome põhipaani, mille saab vastavast fxml failist ning seome selle suuruse stseeni suurusega
        final Pane pane = FXMLLoader.load(getClass().getResource("MainWindow.fxml"));
        pane.minHeightProperty().bind(scene.heightProperty());
        pane.minWidthProperty().bind(scene.widthProperty());

        //Pääseme ligi põhilisele GridPane objektile, mille seome stseeni suurusega
        GridPane mainGridPane = (GridPane) pane.getChildren().get(0);
        mainGridPane.widthProperty().addListener(observable -> mainGridPane.resize(scene.getWidth(), scene.getHeight()));
        mainGridPane.heightProperty().addListener(observable -> mainGridPane.resize(scene.getWidth(), scene.getHeight()));
        mainGridPane.setGridLinesVisible(true);
        for (Node node : mainGridPane.getChildren())
            if (node instanceof GridPane)
                ((GridPane) node).setGridLinesVisible(true);


        GridPane urgImp = getGridPaneFromMainGridPane(true, true, mainGridPane);

        Note note = new Note(urgImp);
        Note note2 = new Note(urgImp);

        urgImp.add(note.getPane(), 2, 1);
        urgImp.add(note2.getPane(), 1, 1);


        //Loome ToolBari ja lisame nuppudele funktsionaalsuse
        ToolBar mainToolBar = (ToolBar) pane.getChildren().get(1);
        addButtonFunctionalityToToolBar(mainToolBar);


        //Lisame juurele paani ning määrame stseeni
        root.getChildren().addAll(pane);
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(scene);

        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
