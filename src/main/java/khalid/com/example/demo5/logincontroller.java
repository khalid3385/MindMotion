package khalid.com.example.demo5;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.Parent;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.stage.StageStyle;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class logincontroller {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private TextField passwordTextField;

    @FXML
    private Button loginButton;

    @FXML
    private Button cancelButton;

    @FXML
    private Label loginMessagelabel;

    @FXML
    private CheckBox showPasswordCheckBox;

    public void loginButtonOnAction() {
        if (!usernameField.getText().isBlank() && !passwordField.getText().isBlank()) {
            validateLogin();
        } else {
            loginMessagelabel.setText("Please fill in all fields.");
        }
    }

    @FXML
    public void cancelButtonOnAction(ActionEvent event) {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        if (stage != null) {
            stage.close();
        }
    }

    @FXML
    public void togglePasswordVisibility() {
        double passwordFieldY = passwordField.getLayoutY();
        double passwordTextFieldY = passwordTextField.getLayoutY();

        if (showPasswordCheckBox.isSelected()) {
            passwordTextField.setText(passwordField.getText());
            passwordTextField.setVisible(true);
            passwordTextField.setManaged(true);

            passwordField.setVisible(false);
            passwordField.setManaged(false);

            passwordTextField.setLayoutY(passwordFieldY);
        } else {
            passwordField.setText(passwordTextField.getText());
            passwordField.setVisible(true);
            passwordField.setManaged(true);

            passwordTextField.setVisible(false);
            passwordTextField.setManaged(false);

            passwordField.setLayoutY(passwordTextFieldY);
        }
    }

    @FXML
    public void initialize() {
        // Manually setting the positions at initialization
        usernameField.setLayoutX(50.0);
        usernameField.setLayoutY(224.0);

        passwordField.setLayoutX(50.0);
        passwordField.setLayoutY(273.0);

        loginButton.setLayoutX(50.0);
        loginButton.setLayoutY(378.0);

        cancelButton.setLayoutX(83.0);
        cancelButton.setLayoutY(427.0);

        // Set other components' layout here...

        usernameField.requestFocus();
    }


    public void validateLogin() {
        Databaseconnect connectNow = new Databaseconnect();
        Connection connectDB = connectNow.getConnection();

        String verifyLogin = "SELECT count(*) FROM mindmotion.user_account WHERE gebruikersnaam = '"
                + usernameField.getText() + "' AND wachtwoord = '" + passwordField.getText() + "'";

        try {
            Statement statement = connectDB.createStatement();
            ResultSet resultSet = statement.executeQuery(verifyLogin);

            if (resultSet.next()) {
                int count = resultSet.getInt(1);

                if (count > 0) {
                    createDashboard();
                } else {
                    loginMessagelabel.setText("Incorrect username or password!");
                }
            } else {
                loginMessagelabel.setText("Database error occurred!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            loginMessagelabel.setText("Database error occurred!");
        }
    }

    public void createDashboard() {
        try {
            // Load the FXML file explicitly with the correct path
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Dashboard.fxml"));
            Parent root = loader.load();

            // Optionally set up the controller (if needed)
            Dashboardcontroller dashboardController = loader.getController();
            dashboardController.initialize(); // Call initialization if necessary

            // Create a new stage and set the scene
            Stage stage = new Stage();
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setScene(new Scene(root));
            stage.show();

            // Close the current login window
            Stage loginStage = (Stage) usernameField.getScene().getWindow();
            loginStage.close();
        } catch (Exception e) {
            e.printStackTrace();
            loginMessagelabel.setText("Error loading Dashboard.fxml.");
        }
    }

}
