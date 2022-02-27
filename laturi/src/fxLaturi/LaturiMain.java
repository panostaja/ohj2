package fxLaturi;


import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import laturi.Laturi;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.fxml.FXMLLoader;


/**
 * Sähköautojen latausseuranta laturin perspektiivistä
 * @author plammi
 * @version 12.2.2022
 * 
 */
public class LaturiMain extends Application {
    @Override
    public void start(Stage primaryStage) {
        try {
            FXMLLoader ldr = new FXMLLoader(getClass().getResource("LaturiGUIView.fxml"));
            final Pane root = ldr.load();
            final LaturiGUIController laturiCtrl = (LaturiGUIController)ldr.getController();
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("laturi.css").toExternalForm());
            primaryStage.setScene(scene);
            primaryStage.setTitle("Laturi");
            Laturi laturi = new Laturi();
            laturiCtrl.setLaturi(laturi);
            
            primaryStage.show();
            if (!laturiCtrl.avaa()) Platform.exit();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @param args Ei k�yt�ss�
     */
    public static void main(String[] args) {
        launch(args);
    }
}