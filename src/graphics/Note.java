package graphics;

import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

import java.io.*;


// MARKUS DID SOME THINGS HERE
// MARKUS DID SOME THINGS HERE
// MARKUS DID SOME THINGS HERE
// MARKUS DID SOME THINGS HERE
// MARKUS DID SOME THINGS HERE
// MARKUS DID SOME THINGS HERE
// MARKUS DID SOME THINGS HERE
// MARKUS DID SOME THINGS HERE
//the main thing is here


public class Note {

    private Pane pane;
    private TextArea textArea;


    public Note(GridPane parentGridPane) throws IOException {

        Pane pane = FXMLLoader.load(getClass().getResource("Note.fxml"));

        pane.setStyle("-fx-background-color: yellow;");

        pane.widthProperty().addListener(observable -> pane.resize(parentGridPane.getWidth() / 3, parentGridPane.getHeight() / 2));
        pane.heightProperty().addListener(observable -> pane.resize(parentGridPane.getWidth() / 3, parentGridPane.getHeight() / 2));

        this.pane = pane;

        textArea = (TextArea) pane.getChildren().get(0);
        textArea.widthProperty().addListener(observable -> textArea.resize(pane.getWidth() * 0.85, pane.getHeight()*0.75));
        textArea.heightProperty().addListener(observable -> textArea.resize(pane.getWidth() * 0.85, pane.getHeight() * 0.75));

    }


    public Pane getPane() {
        return this.pane;
    }
}