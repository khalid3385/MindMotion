package khalid.com.example.demo5;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class RegisterController {
    @FXML
    private Button closeButton;
    @FXML
    private Label registrationMessageLabel;
    @FXML
    private Label userNameTaken;
    @FXML
    private Label emailadressTaken; // Label for email taken message
    @FXML
    private PasswordField setPasswordField;
    @FXML
    private PasswordField confirmPasswordField;
    @FXML
    private TextField unmaskedPasswordField;
    @FXML
    private TextField unmaskedConfirmPasswordField;
    @FXML
    private TextField FIrstnamefield;
    @FXML
    private TextField Lastnamefield;
    @FXML
    private TextField Usernamefield;
    @FXML
    private TextField emailadressField; // Email address field
    @FXML
    private CheckBox showPasswordCheckBox;
    @FXML
    private Label confirmPasswordLabel;

    @FXML
    public void registerButtonAction() {
        // Collect input values
        String emailAddress = emailadressField.getText().trim();
        String firstName = FIrstnamefield.getText().trim();
        String lastName = Lastnamefield.getText().trim();
        String username = Usernamefield.getText().trim();
        String password = showPasswordCheckBox.isSelected() ? unmaskedPasswordField.getText() : setPasswordField.getText();
        String confirmPassword = showPasswordCheckBox.isSelected() ? unmaskedConfirmPasswordField.getText() : confirmPasswordField.getText();

        // Validate inputs
        if (emailAddress.isEmpty() || firstName.isEmpty() || lastName.isEmpty() || username.isEmpty()) {
            registrationMessageLabel.setText("All fields are required!");
            return;
        }

        // Check if passwords match
        if (!password.equals(confirmPassword)) {
            confirmPasswordLabel.setText("Passwords do not match.");
            registrationMessageLabel.setText(""); // Clear registration message
            return;
        }

        // Check password length
        if (password.length() < 8) {
            confirmPasswordLabel.setText("Password must be at least 8 characters long.");
            registrationMessageLabel.setText(""); // Clear registration message
            return;
        }

        // Check if email address already exists in the database
        if (emailAddressExists(emailAddress)) {
            emailadressTaken.setText("Email address is already taken!");
            userNameTaken.setText("");
            confirmPasswordLabel.setText("");
            return;
        } else {
            emailadressTaken.setText(""); // Clear email taken message if registration proceeds
        }

        // Check if username already exists in the database
        if (usernameExists(username)) {
            userNameTaken.setText("Username is already taken!");
            emailadressTaken.setText("");
            confirmPasswordLabel.setText("");
            return;
        } else {
            userNameTaken.setText(""); // Clear username taken message if registration proceeds
        }

        // If all checks pass, register the user
        registerUser(emailAddress, firstName, lastName, username, password);

        registrationMessageLabel.setText("Registration successful!");
        confirmPasswordLabel.setText(""); // Clear confirm password label
    }

    @FXML
    public boolean usernameExists(String username) {
        Databaseconnect connectNow = new Databaseconnect();
        Connection connectDB = connectNow.getConnection();

        try {
            String query = "SELECT * FROM user_account WHERE gebruikersnaam = ?";
            PreparedStatement preparedStatement = connectDB.prepareStatement(query);
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();

            // If the result set is not empty, the username exists
            return resultSet.next();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @FXML
    public boolean emailAddressExists(String emailAddress) {
        Databaseconnect connectNow = new Databaseconnect();
        Connection connectDB = connectNow.getConnection();

        try {
            String query = "SELECT * FROM user_account WHERE emailadress = ?";
            PreparedStatement preparedStatement = connectDB.prepareStatement(query);
            preparedStatement.setString(1, emailAddress);
            ResultSet resultSet = preparedStatement.executeQuery();

            // If the result set is not empty, the email address exists
            return resultSet.next();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @FXML
    public void registerUser(String emailAddress, String firstName, String lastName, String username, String password) {
        Databaseconnect connectNow = new Databaseconnect();
        Connection connectDB = connectNow.getConnection();

        String insertFields = "INSERT INTO user_account(emailadress, voornaam, achternaam, gebruikersnaam, wachtwoord) VALUES(?, ?, ?, ?, ?)";
        try {
            PreparedStatement preparedStatement = connectDB.prepareStatement(insertFields);
            preparedStatement.setString(1, emailAddress);
            preparedStatement.setString(2, firstName);
            preparedStatement.setString(3, lastName);
            preparedStatement.setString(4, username);
            preparedStatement.setString(5, password);

            // Execute the query
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void toggleShowPassword() {
        boolean showPassword = showPasswordCheckBox.isSelected();

        if (showPassword) {
            // Show unmasked fields
            unmaskedPasswordField.setText(setPasswordField.getText());
            unmaskedPasswordField.setLayoutY(setPasswordField.getLayoutY());
            unmaskedPasswordField.setVisible(true);
            setPasswordField.setVisible(false);

            unmaskedConfirmPasswordField.setText(confirmPasswordField.getText());
            unmaskedConfirmPasswordField.setLayoutY(confirmPasswordField.getLayoutY());
            unmaskedConfirmPasswordField.setVisible(true);
            confirmPasswordField.setVisible(false);
        } else {
            // Show masked fields
            setPasswordField.setText(unmaskedPasswordField.getText());
            setPasswordField.setLayoutY(unmaskedPasswordField.getLayoutY());
            setPasswordField.setVisible(true);
            unmaskedPasswordField.setVisible(false);

            confirmPasswordField.setText(unmaskedConfirmPasswordField.getText());
            confirmPasswordField.setLayoutY(unmaskedConfirmPasswordField.getLayoutY());
            confirmPasswordField.setVisible(true);
            unmaskedConfirmPasswordField.setVisible(false);
        }
    }

    @FXML
    public void closeButtonOnAction() {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
        Platform.exit();
    }
}


