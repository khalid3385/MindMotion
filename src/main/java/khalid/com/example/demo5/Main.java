package khalid.com.example.demo5;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        // Make sure the path points correctly to the location of login.fxml
        AnchorPane root = FXMLLoader.load(getClass().getResource("/khalid/com/example/demo5/login.fxml"));

        // Set up the Scene with the root AnchorPane
        Scene scene = new Scene(root);

        // Set the stage (window) title
        primaryStage.setTitle("Login Application");

        // Set the scene
        primaryStage.setScene(scene);

        // Show the stage (window)
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

