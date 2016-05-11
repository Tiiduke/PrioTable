package graphics;

import javafx.beans.property.StringProperty;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

import java.io.*;

public class Note {


    private boolean saved;
    String name;
    PrintWriter writer;
    Note note = this;
    @FXML
    private Pane pane;
    @FXML
    private TextArea textArea;


    public Note(GridPane parentGridPane, String name) throws IOException {
        note.name = name;

        Pane pane = FXMLLoader.load(getClass().getResource("Note.fxml"));

        pane.setStyle("-fx-background-color: yellow;");

        note.pane = pane;

        pane.widthProperty().addListener(observable -> pane.resize(parentGridPane.getWidth() / 3, parentGridPane.getHeight() / 2));
        pane.heightProperty().addListener(observable -> pane.resize(parentGridPane.getWidth() / 3, parentGridPane.getHeight() / 2));

        textArea = (TextArea) pane.getChildren().get(0);
        textArea.widthProperty().addListener(observable -> textArea.resize(pane.getWidth() * 0.85, pane.getHeight()*0.75));
        textArea.heightProperty().addListener(observable -> textArea.resize(pane.getWidth() * 0.85, pane.getHeight() * 0.75));



        note.pane.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (Main.mouseState == MouseState.DELETE) {
                    System.out.println(note.pane.getChildren().contains(note.getPane()));
                } else if (Main.mouseState == MouseState.MARK_DONE) {
                    note.finalizeNote();
                }
            }
        });
    }
    public void finalizeNote() {
        try {
            String string = note.getText();
            note.writer = new PrintWriter(new BufferedWriter(new FileWriter(note.name + ".txt")));
            note.writer.print(string);
            note.writer.close();
            note.getPane().getChildren().remove(0);
            note.getPane().getChildren().add(new Label(string));
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public String getText() {
        return textProperty().get();
    }

    public StringProperty textProperty() {
        return note.textArea.textProperty();
    }

    public Pane getPane() {
        return pane;
    }
}