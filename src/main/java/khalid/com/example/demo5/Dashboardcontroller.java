package khalid.com.example.demo5;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

public class Dashboardcontroller {
    @FXML
    private VBox vbox;

    @FXML
    private Button myButton;

    @FXML
    private Label stepsLabel;

    @FXML
    private ProgressBar stepsProgressBar;

    @FXML
    private ImageView imageView;

    private int currentSteps = 0;
    private final int goalSteps = 10000; // Het doel voor het aantal stappen

    @FXML
    private Label welcomeText;

    // Methode om stappen toe te voegen
    public void addSteps(int steps) {
        currentSteps += steps;
        updateStepsDisplay();
    }

    // Methode om de voortgangsbalk en de label bij te werken
    private void updateStepsDisplay() {
        if (stepsLabel != null) {
            stepsLabel.setText(String.valueOf(currentSteps));
        }

        double progress = (double) currentSteps / goalSteps;
        if (stepsProgressBar != null) {
            stepsProgressBar.setProgress(Math.min(progress, 1.0));
        }
    }

    // Methode om een afbeelding te laden
    private void loadImage() {
        try {
            Image image = new Image(getClass().getResourceAsStream("/com/example/demo1/Afbeeldingen/608675.png"));
            if (imageView != null) {
                imageView.setImage(image);
            }
        } catch (NullPointerException e) {
            System.out.println("Afbeelding niet gevonden: " + e.getMessage());
        }
    }

    // Simuleert beweging door stappen toe te voegen
    public void simulateMovement() {
        addSteps(500); // Voeg 500 stappen toe per simulatie
    }

    // Methode die wordt aangeroepen bij een klik op de knop
    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }

    // Initializeert de standaardwaarden
    @FXML
    public void initialize() {
        updateStepsDisplay();
        loadImage();
    }
}
