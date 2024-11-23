package com.example.dataform;

import javafx.application.Application;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.geometry.Insets;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.File;

public class ApplicationForm extends Application {

    private ObservableList<String[]> submittedData = FXCollections.observableArrayList();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Application Form");

        // Banner
        BorderPane bannerPane = new BorderPane();
        bannerPane.setStyle("-fx-background-color: #4B1D1D; -fx-padding: 15px;");

        Text bannerText = new Text("Application Form");
        bannerText.setFont(Font.font("Georgia", FontWeight.BOLD, 28));
        bannerText.setFill(Color.GOLD);
        bannerPane.setCenter(bannerText);

        // Form layout
        GridPane formGrid = new GridPane();
        formGrid.setPadding(new Insets(20));
        formGrid.setHgap(10);
        formGrid.setVgap(10);
        formGrid.setStyle("-fx-background-color: #5E2A2A; -fx-border-color: #8B3F3F; -fx-border-radius: 10px; -fx-padding: 20px;");

        // Name field
        Label nameLabel = new Label("Name:");
        styleLabel(nameLabel);
        TextField nameField = new TextField();
        styleInput(nameField);

        // Father's Name field
        Label fatherNameLabel = new Label("Father's Name:");
        styleLabel(fatherNameLabel);
        TextField fatherNameField = new TextField();
        styleInput(fatherNameField);

        // Email field
        Label emailLabel = new Label("Email:");
        styleLabel(emailLabel);
        TextField emailField = new TextField();
        styleInput(emailField);

        // City ComboBox field
        Label cityLabel = new Label("City:");
        styleLabel(cityLabel);
        ComboBox<String> cityComboBox = new ComboBox<>();
        cityComboBox.getItems().addAll("Lahore", "Islamabad", "Gujranwala", "Karachi", "Quetta");
        cityComboBox.setStyle("-fx-background-color: #8B3F3F; -fx-text-fill: white; -fx-border-color: #B56464;");

        // Address field
        Label addressLabel = new Label("Address:");
        styleLabel(addressLabel);
        TextArea addressArea = new TextArea();
        addressArea.setPrefRowCount(3);
        addressArea.setWrapText(true);
        styleInput(addressArea);

        // Gender field
        Label genderLabel = new Label("Gender:");
        styleLabel(genderLabel);
        ToggleGroup genderGroup = new ToggleGroup();
        RadioButton maleButton = new RadioButton("Male");
        RadioButton femaleButton = new RadioButton("Female");
        styleRadioButton(maleButton);
        styleRadioButton(femaleButton);
        HBox genderBox = new HBox(10, maleButton, femaleButton);

        // Image upload field
        Label imageLabel = new Label("Upload Image:");
        styleLabel(imageLabel);
        Button uploadButton = new Button("Choose File");
        styleButton(uploadButton);
        ImageView imageView = new ImageView();
        imageView.setFitWidth(100);
        imageView.setFitHeight(100);
        imageView.setPreserveRatio(true);
        imageView.setStyle("-fx-border-color: #8B3F3F;");
        HBox imageBox = new HBox(10, uploadButton, imageView);

        FileChooser fileChooser = new FileChooser();
        uploadButton.setOnAction(e -> {
            File selectedFile = fileChooser.showOpenDialog(primaryStage);
            if (selectedFile != null) {
                Image image = new Image(selectedFile.toURI().toString());
                imageView.setImage(image);
            }
        });

        // Add elements to the grid layout
        formGrid.addRow(0, nameLabel, nameField);
        formGrid.addRow(1, fatherNameLabel, fatherNameField);
        formGrid.addRow(2, emailLabel, emailField);
        formGrid.addRow(3, cityLabel, cityComboBox); // Updated to use ComboBox for city
        formGrid.addRow(4, addressLabel, addressArea);
        formGrid.addRow(5, genderLabel, genderBox);
        formGrid.addRow(6, imageLabel, imageBox);

        // Submit button
        Button submitButton = new Button("Submit");
        styleButton(submitButton);
        submitButton.setOnAction(e -> {
            String name = nameField.getText();
            String fatherName = fatherNameField.getText();
            String email = emailField.getText();
            String city = cityComboBox.getValue(); // Get selected city from ComboBox
            String address = addressArea.getText();
            String gender = maleButton.isSelected() ? "Male" : (femaleButton.isSelected() ? "Female" : "Not Selected");

            if (name.isEmpty() || fatherName.isEmpty() || email.isEmpty() || city == null || address.isEmpty() || gender.equals("Not Selected")) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Please fill all fields!");
                alert.showAndWait();
                return;
            }

            submittedData.add(new String[]{name, fatherName, email, city, address, gender});
            showDataScreen();
        });

        // Main layout
        VBox mainLayout = new VBox(10, bannerPane, formGrid, submitButton);
        mainLayout.setPadding(new Insets(20));
        mainLayout.setStyle("-fx-background-color: #5E2A2A;"); // Update background color to match the form

        primaryStage.setScene(new Scene(mainLayout, 600, 600));
        primaryStage.show();
    }

    private void showDataScreen() {
        Stage dataStage = new Stage();
        VBox dataLayout = new VBox(10);
        dataLayout.setPadding(new Insets(20));
        dataLayout.setStyle("-fx-background-color: #5E2A2A;");

        Label dataLabel = new Label("Submitted Data:");
        dataLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: gold;");
        dataLayout.getChildren().add(dataLabel);

        for (String[] entry : submittedData) {
            String entryString = String.format(
                    "Name: %s\nFather's Name: %s\nEmail: %s\nCity: %s\nAddress: %s\nGender: %s\n",
                    entry[0], entry[1], entry[2], entry[3], entry[4], entry[5]
            );
            Label entryLabel = new Label(entryString);
            entryLabel.setStyle("-fx-background-color: #8B3F3F; -fx-text-fill: white; -fx-padding: 10px;");
            dataLayout.getChildren().add(entryLabel);
        }

        Scene dataScene = new Scene(dataLayout, 400, 400);
        dataStage.setScene(dataScene);
        dataStage.show();
    }

    private void styleLabel(Label label) {
        label.setStyle("-fx-text-fill: gold; -fx-font-weight: bold;");
    }

    private void styleInput(Control control) {
        control.setStyle("-fx-background-color: #8B3F3F; -fx-text-fill: white; -fx-border-color: #B56464;");
    }

    private void styleButton(Button button) {
        button.setStyle("-fx-background-color: #B56464; -fx-text-fill: white; -fx-font-weight: bold; -fx-border-radius: 5px;");
    }

    private void styleRadioButton(RadioButton radioButton) {
        radioButton.setStyle("-fx-text-fill: white;");
    }
}
