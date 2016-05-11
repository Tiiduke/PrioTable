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


    private boolean saved;
    String name;
    PrintWriter writer;
    Note tiit = this;


    @FXML
    private Pane pane;
    @FXML
    private TextArea textArea;


    public Note(GridPane parentGridPane, String name) throws IOException {
        tiit.name = name;

        Pane pane = FXMLLoader.load(getClass().getResource("Note.fxml"));

        pane.setStyle("-fx-background-color: yellow;");

        tiit.pane = pane;

        pane.widthProperty().addListener(observable -> pane.resize(parentGridPane.getWidth() / 3, parentGridPane.getHeight() / 2));
        pane.heightProperty().addListener(observable -> pane.resize(parentGridPane.getWidth() / 3, parentGridPane.getHeight() / 2));

        textArea = (TextArea) pane.getChildren().get(0);
        textArea.widthProperty().addListener(observable -> textArea.resize(pane.getWidth() * 0.85, pane.getHeight()*0.75));
        textArea.heightProperty().addListener(observable -> textArea.resize(pane.getWidth() * 0.85, pane.getHeight() * 0.75));


        tiit.pane.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (Main.mouseState == MouseState.DELETE) {
//                    System.out.println();
//                    System.out.println(( (GridPane) tiit.getPane().getParent()).getChildren().remove(this));
                    System.out.println(tiit.pane.getChildren().contains(tiit.getPane()));
                } else if (Main.mouseState == MouseState.MARK_DONE) {
                    tiit.finalizeNote();
                }
            }
        });
    }
    public void finalizeNote() {
        try {
            String string = tiit.getText();
            tiit.writer = new PrintWriter(new BufferedWriter(new FileWriter("notes/" + tiit.name + ".txt")));
            tiit.writer.print(string);
            tiit.writer.close();
            tiit.getPane().getChildren().remove(0);
            tiit.getPane().getChildren().add(new Label(string));
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public String getText() {
        return textProperty().get();
    }

    public StringProperty textProperty() {
        return tiit.textArea.textProperty();
    }

    public Pane getPane() {
        return pane;
    }
}