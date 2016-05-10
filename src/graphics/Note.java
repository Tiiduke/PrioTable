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
    @FXML
    private TextArea textArea;
    private PrintWriter writer;
    private boolean saved;

    public Note() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(
                "Note.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }


        Note neg = this;

        textArea.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (!newValue && !saved) {
                    neg.salvestaTekst();
                    saved = true;
                }
            }
        });
        textArea.setOnKeyTyped(event -> saved = false);
    }
    void salvestaTekst() {
        try {
            writer = new PrintWriter(new BufferedWriter(new FileWriter("name2123.txt")), true);
            writer.println(this.getText());
            writer.close();
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
        return textArea.textProperty();
    }
}