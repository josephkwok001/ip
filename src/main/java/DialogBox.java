import java.io.IOException;
import java.util.Collections;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

import javafx.geometry.Insets;
import javafx.geometry.Pos;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

/**
 * Represents a dialog box consisting of an ImageView to represent the speaker's face
 * and a label containing text from the speaker.
 */
public class DialogBox extends HBox {
    @FXML
    private Label dialog;
    @FXML
    private ImageView displayPicture;

    private DialogBox(String text, Image img, boolean isError, boolean isUser) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MainWindow.class.getResource("/view/DialogBox.fxml"));
            fxmlLoader.setController(this);
            fxmlLoader.setRoot(this);
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        dialog.setText(text);
        displayPicture.setImage(img);

        if (isError) {
            applyErrorStyle();
        } else if (isUser) {
            applyUserStyle();
        } else {
            applyBotStyle();
        }

    }

    /**
     * Flips the dialog box such that the ImageView is on the left and text on the right.
     */
    private void flip() {
        ObservableList<Node> tmp = FXCollections.observableArrayList(this.getChildren());
        Collections.reverse(tmp);
        getChildren().setAll(tmp);
        setAlignment(Pos.TOP_LEFT);
    }

    public static DialogBox getUserDialog(String text, Image img) {
        return new DialogBox(text, img, false, true);
    }

    public static DialogBox getWaguriDialog(String text, Image img) {
        boolean isError = text.startsWith("ERROR:");
        var db = new DialogBox(text, img, isError, false);
        db.flip();
        return db;
    }

    private void applyErrorStyle() {
        dialog.setStyle("-fx-background-color: #ffebee; " +
                "-fx-text-fill: #c62828; " +
                "-fx-border-color: #f44336; " +
                "-fx-border-width: 2px; " +
                "-fx-border-radius: 5px; " +
                "-fx-background-radius: 5px; " +
                "-fx-padding: 10px; " +
                "-fx-font-weight: bold;");

        dialog.setText("⚠️ " + dialog.getText());
    }

    private void applyBotStyle() {
        // Clean, modern bot message style
        dialog.setStyle("-fx-background-color: #f8f9fa; " +
                "-fx-text-fill: #2d3748; " +
                "-fx-border-color: #e2e8f0; " +
                "-fx-border-width: 1px; " +
                "-fx-border-radius: 12px; " +
                "-fx-background-radius: 12px; " +
                "-fx-padding: 12px 16px; " +
                "-fx-font-family: 'Segoe UI', sans-serif; " +
                "-fx-font-size: 14px; " +
                "-fx-line-spacing: 1.4;");
    }
    private void applyUserStyle() {
        dialog.setStyle("-fx-background-color: #007bff; " +
                "-fx-text-fill: white; " +
                "-fx-border-color: #0056b3; " +
                "-fx-border-width: 1px; " +
                "-fx-border-radius: 12px; " +
                "-fx-background-radius: 12px; " +
                "-fx-padding: 12px 16px; " +
                "-fx-font-family: 'Segoe UI', sans-serif; " +
                "-fx-font-size: 14px; " +
                "-fx-line-spacing: 1.4;");
    }


    {
        this.setSpacing(10);
        this.setPadding(new Insets(5, 10, 5, 10));
    }


}
