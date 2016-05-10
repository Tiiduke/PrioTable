package graphics;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;


// MARKUS DID SOME THINGS HERE
// MARKUS DID SOME THINGS HERE
// MARKUS DID SOME THINGS HERE
// MARKUS DID SOME THINGS HERE
// MARKUS DID SOME THINGS HERE
// MARKUS DID SOME THINGS HERE
// MARKUS DID SOME THINGS HERE
// MARKUS DID SOME THINGS HERE

import static java.lang.Double.MAX_VALUE;

public class SilverMain extends Application {



    @Override
    public void start(Stage primaryStage) throws Exception{
//        Parent root = FXMLLoader.load(getClass().getResource("Note.fxml"));
        //Parent root = FXMLLoader.load(getClass().getResource("MainWindow.fxml"));

        Group main_group = new Group();
        main_group.getChildren().add(new Note("Test"));

        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(main_group));
// MARKUS DID SOME THINGS HERE
// MARKUS DID SOME THINGS HERE
// MARKUS DID SOME THINGS HERE
// MARKUS DID SOME THINGS HERE
// MARKUS DID SOME THINGS HERE
// MARKUS DID SOME THINGS HERE
// MARKUS DID SOME THINGS HERE
// MARKUS DID SOME THINGS HERE// MARKUS DID SOME THINGS HERE
// MARKUS DID SOME THINGS HERE
// MARKUS DID SOME THINGS HERE
// MARKUS DID SOME THINGS HERE
// MARKUS DID SOME THINGS HERE
// MARKUS DID SOME THINGS HERE
// MARKUS DID SOME THINGS HERE
// MARKUS DID SOME THINGS HERE// MARKUS DID SOME THINGS HERE
// MARKUS DID SOME THINGS HERE
// MARKUS DID SOME THINGS HERE
// MARKUS DID SOME THINGS HERE
// MARKUS DID SOME THINGS HERE
// MARKUS DID SOME THINGS HERE
// MARKUS DID SOME THINGS HERE
// MARKUS DID SOME THINGS HERE

        primaryStage.show();

    }


    public static void main(String[] args) {
        launch(args);


    }
}
