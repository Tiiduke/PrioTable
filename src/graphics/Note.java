package graphics;

import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.control.TextArea;


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


public class Note extends Group
{

    private String name;

    @FXML
    private TextArea textArea;
    private PrintWriter writer;
    private boolean saved;
    Note shit = this;

    public Note(String name) throws IOException {
        shit.name = name;


        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(
                "Note.fxml"));
        fxmlLoader.setRoot(shit);
        fxmlLoader.setController(shit);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        textArea.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (!newValue && !saved) {
                    shit.salvestaTekst();
                    shit.saved = true;
                }
            }
        });
        textArea.setOnKeyTyped(event -> saved = false);
    }
    void salvestaTekst() {
        try {
            shit.writer = new PrintWriter(new BufferedWriter(new FileWriter("name2123.txt")), true);
            shit.writer.println(this.getText());
            shit.writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void test(){
        System.out.println("b0ss");
    }

    public String getText() {
        return textProperty().get();
    }

    public void setText(String value) {
        textProperty().set(value);
    }

    public StringProperty textProperty() {
        return shit.textArea.textProperty();
    }
}