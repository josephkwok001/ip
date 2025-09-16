import java.io.IOException;

import javafx.application.Application;

import javafx.fxml.FXMLLoader;

import javafx.scene.Scene;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.AnchorPane;

import javafx.stage.Stage;

import waguri.Waguri;


public class Main extends Application {
    private Waguri waguri = new Waguri("./data/waguri.txt");

    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/MainWindow.fxml"));
            AnchorPane ap = fxmlLoader.load();
            Scene scene = new Scene(ap);
            stage.setTitle("Waguri - Task Management Assistant");

            stage.setFullScreenExitHint("Press ESC to exit fullscreen");
            stage.setFullScreenExitKeyCombination(KeyCombination.keyCombination("ESC"));

            stage.setScene(scene);
            fxmlLoader.<MainWindow>getController().setWaguri(waguri);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}

