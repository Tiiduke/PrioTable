package graphics;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;


import java.io.*;

public class Note {

    private Pane pane;
    private TextArea textArea;

    public Note(GridPane parentGridPane) throws IOException {

        //Laeb fxml failist kujunduse
        Pane pane = FXMLLoader.load(getClass().getResource("Note.fxml"));

        pane.setStyle("-fx-background-color: yellow;");

        //Lisab kuularid paani suurendamiseks/vähendamiseks
        pane.widthProperty().addListener(observable -> pane.resize(parentGridPane.getWidth() / 3, parentGridPane.getHeight() / 2));
        pane.heightProperty().addListener(observable -> pane.resize(parentGridPane.getWidth() / 3, parentGridPane.getHeight() / 2));

        this.pane = pane;

        //Võtab paanilt TextArea childi ja lisab sellele suurustmuutvad kuularid
        textArea = (TextArea) pane.getChildren().get(0);
        textArea.widthProperty().addListener(observable -> textArea.resize((pane.getWidth() - 10) * 0.85, (pane.getHeight() - 15) * 0.75));
        textArea.heightProperty().addListener(observable -> textArea.resize((pane.getWidth() - 10) * 0.85, (pane.getHeight() - 15) * 0.75));
    }
    //Meetod märkme tehtud märkimiseks ja faili kirjutamiseks(et enda üle uhkust tunda)
    public void writeNoteToMarkDoneFile(){
        try {
            File file = new File("donenotes.txt");
            PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(file, true)));
            pw.println(textArea.getText());
            pw.close();
        }
        catch (IOException e) {
            System.out.println("Vigane fail");
        }
    }

    public Pane getPane() {
        return this.pane;
    }
}