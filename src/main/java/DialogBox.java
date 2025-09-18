import java.io.IOException;
import java.util.Collections;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

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

        this.setSpacing(5);
        this.setAlignment(Pos.TOP_RIGHT);

        setLayout();

        if (isError) {
            applyErrorStyle();
        } else if (isUser) {
            applyUserStyle();
        } else {
            applyBotStyle();
        }

    }

    private void setLayout() {
        dialog.setMaxWidth(Double.MAX_VALUE);
        HBox.setHgrow(dialog, Priority.ALWAYS);

        this.setMaxWidth(Double.MAX_VALUE);

        dialog.maxWidthProperty().bind(
                this.widthProperty()
                        .subtract(displayPicture.getFitWidth() + 20)
                        .subtract(this.getPadding().getLeft() + this.getPadding().getRight())
        );
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
        dialog.setStyle("-fx-background-color: #ffebee; "
                + "-fx-text-fill: #c62828; "
                + "-fx-border-color: #f44336; "
                + "-fx-border-width: 2px; "
                + "-fx-border-radius: 5px; "
                + "-fx-background-radius: 5px; "
                + "-fx-padding: 10px; "
                + "-fx-font-weight: bold;");

        dialog.setText("⚠️ " + dialog.getText());
    }

    private void applyBotStyle() {
        dialog.setStyle("-fx-background-color: #374151; " +
                "-fx-text-fill: #f3f4f6; " +
                "-fx-border-color: #4b5563; " +
                "-fx-border-width: 1px; " +
                "-fx-border-radius: 18px 18px 18px 4px; " +
                "-fx-background-radius: 18px 18px 18px 4px; " +
                "-fx-padding: 14px 18px; " +
                "-fx-font-family: 'Segoe UI', sans-serif; " +
                "-fx-font-size: 14px; " +
                "-fx-line-spacing: 1.5;");
    }

    private void applyUserStyle() {
        dialog.setStyle("-fx-background-color: #6366f1; " +
                "-fx-text-fill: white; " +
                "-fx-border-color: #4f46e5; " +
                "-fx-border-width: 1px; " +
                "-fx-border-radius: 18px 18px 4px 18px; " +
                "-fx-background-radius: 18px 18px 4px 18px; " +
                "-fx-padding: 14px 18px; " +
                "-fx-font-family: 'Segoe UI', sans-serif; " +
                "-fx-font-size: 14px; " +
                "-fx-line-spacing: 1.5; " +
                "-fx-effect: dropshadow(gaussian, rgba(99, 102, 241, 0.3), 6, 0.2, 0, 2);");
    }


}
