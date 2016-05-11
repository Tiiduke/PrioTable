package graphics;

import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.shape.Rectangle;
import org.w3c.dom.css.Rect;

import java.awt.*;
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

    @FXML
    private boolean saved;
    private Pane pane;

    public Note(GridPane parentGridPane) throws IOException {

        Pane pane = FXMLLoader.load(getClass().getResource("Note.fxml"));

        pane.setStyle("-fx-background-color: yellow;");

        this.pane = pane;

        pane.widthProperty().addListener(observable -> pane.resize(parentGridPane.getWidth() / 3, parentGridPane.getHeight() / 2));
        pane.heightProperty().addListener(observable -> pane.resize(parentGridPane.getWidth() / 3, parentGridPane.getHeight() / 2));

        TextArea ta = (TextArea) pane.getChildren().get(0);
        ta.widthProperty().addListener(observable -> ta.resize(pane.getWidth() * 0.85, pane.getHeight()*0.75));
        ta.heightProperty().addListener(observable -> ta.resize(pane.getWidth() * 0.85, pane.getHeight() * 0.75));


    }

    public Pane getPane() {
        return pane;
    }
}