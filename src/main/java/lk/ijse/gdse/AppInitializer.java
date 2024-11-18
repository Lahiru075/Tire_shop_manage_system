package lk.ijse.gdse;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class AppInitializer extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        Parent load =  FXMLLoader.load(getClass().getResource("/view/LoginForm.fxml"));
        Scene scene = new Scene(load);

        Image image = new Image(getClass().getResourceAsStream("/images/tire.png"));
        stage.getIcons().add(image);

        stage.setScene(scene);
        stage.setTitle("Login Form");
        stage.setResizable(false);
        stage.show();
    }
}
