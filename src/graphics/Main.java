package graphics;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import static java.lang.Double.MAX_VALUE;

public class Main extends Application {

    private GridPane getGridPaneFromMainGridPane(boolean urgent, boolean important, GridPane mainGridPane) {
        if (urgent && important)
            return (GridPane) mainGridPane.getChildren().get(4);
        else if (urgent)
            return (GridPane) mainGridPane.getChildren().get(5);
        else if (important)
            return (GridPane) mainGridPane.getChildren().get(6);
        else
            return (GridPane) mainGridPane.getChildren().get(7);
    }

    @Override
    public void start(final Stage primaryStage) throws Exception{
        Group root = new Group();
        Scene scene = new Scene(root, 1300, 800);
        final Pane pane = FXMLLoader.load(getClass().getResource("MainWindow.fxml"));

        GridPane mainGridPane = (GridPane) pane.getChildren().get(0);

        mainGridPane.widthProperty().addListener(observable -> mainGridPane.resize(scene.getWidth(), scene.getHeight()));
        mainGridPane.heightProperty().addListener(observable -> mainGridPane.resize(scene.getWidth(), scene.getHeight()));
        mainGridPane.setGridLinesVisible(true);


        for (Node node : mainGridPane.getChildren()) {
            System.out.println(node);
            if (node instanceof GridPane) {
                ((GridPane) node).setGridLinesVisible(true);

            }
        }

        GridPane urgImp = getGridPaneFromMainGridPane(true, true, mainGridPane);
        urgImp.add(new Note("Tekst"), 0, 0);

        GridPane notUrgImp = getGridPaneFromMainGridPane(false, true, mainGridPane);
        notUrgImp.add(new Note("Tekst2"), 0, 0);

        pane.minHeightProperty().bind(scene.heightProperty());
        pane.minWidthProperty().bind(scene.widthProperty());

        root.getChildren().addAll(pane);
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(scene);

        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
