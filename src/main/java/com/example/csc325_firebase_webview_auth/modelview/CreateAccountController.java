package com.example.csc325_firebase_webview_auth.modelview;

import com.example.csc325_firebase_webview_auth.App;
import com.example.csc325_firebase_webview_auth.models.Person;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class CreateAccountController implements Initializable {


    @FXML
    public PasswordField tfConfirmPassword, tfPassword;
    @FXML
    private TextField tfUsername, tfName, tfMajor, tfAge;
    public Label labelSuccessMsg;
    public Button createAccountBtn;
    private static boolean userHasRegisteredStatus = false;
    private boolean flag = false;

    private AccessFBView access = new AccessFBView();



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        List<TextField> textFieldList = List.of(tfUsername, tfPassword, tfConfirmPassword, tfName, tfMajor,tfAge);
        createAccountBtn.setDisable(true); // initially disable add button

        // Add a key pressed listener to each text field
        textFieldList.forEach(textField -> {
            textField.setOnKeyPressed(event -> {
                if (event.getCode() != KeyCode.TAB && flag) {
                    textField.setStyle("-fx-border-color: #12c812 ; -fx-border-width: 1px ;");
                    flag = false;
                }
            });
        });

        tfUsername.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (tfUsername.getText().matches("[a-zA-Z0-9]{2,16}")) { // username regex
                tfUsername.setBorder(null);
                tfName.setEditable(true);
            } else {
                tfUsername.setTooltip(new Tooltip("Username must be between 2 and 16 characters.")); // will appear when hovering over textfield
                tfUsername.setStyle("-fx-border-color: red ; -fx-border-width: 1px ;");
                tfUsername.setVisible(true);
                tfName.setEditable(false);
                tfMajor.setEditable(false);
                tfAge.setEditable(false);
                tfPassword.setEditable(false);
                tfConfirmPassword.setEditable(false);
                tfUsername.requestFocus();
                flag = true;
            }
        });

        tfName.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (tfName.getText().matches("[A-Za-z]{2,12}[\\s][A-Za-z]{2,13}")) {
                tfMajor.setEditable(true);
                tfName.setBorder(null);
            } else {
                tfName.setTooltip(new Tooltip("Email must contain an @ symbol.")); // will appear when hovering over textfield
                tfName.setStyle("-fx-border-color: red ; -fx-border-width: 1px ;");
                tfName.setVisible(true);
                tfMajor.setEditable(false);
                tfAge.setEditable(false);
                tfPassword.setEditable(false);
                tfConfirmPassword.setEditable(false);
                tfName.requestFocus();
                flag = true;
            }
        });

        tfMajor.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (tfMajor.getText().toUpperCase().matches("[A-Z]{3}")) {
                tfAge.setEditable(true);
                tfName.setBorder(null);
            } else {
                tfMajor.setTooltip(new Tooltip("Majors must be 3 characters. Ex: \'CSC\' Oor \'PSY\'")); // will appear when hovering over textfield
                tfMajor.setStyle("-fx-border-color: red ; -fx-border-width: 1px ;");
                tfMajor.setVisible(true);
                tfAge.setEditable(false);
                tfPassword.setEditable(false);
                tfConfirmPassword.setEditable(false);
                tfMajor.requestFocus();
                flag = true;
            }
        });

        tfAge.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (tfAge.getText().matches("^(1[3-9]|[2-9][0-9])$")) {
                tfPassword.setEditable(true);
                tfConfirmPassword.setEditable(true);
                tfName.setBorder(null);
            } else {
                tfAge.setTooltip(new Tooltip("Invalid age. must be at least 13 and no more than 99 years old.")); // will appear when hovering over textfield
                tfAge.setStyle("-fx-border-color: red ; -fx-border-width: 1px ;");
                tfAge.setVisible(true);
                tfPassword.setEditable(false);
                tfConfirmPassword.setEditable(false);
                tfMajor.requestFocus();
                flag = true;
            }
        });

        tfPassword.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (tfPassword.getText().matches("[a-zA-Z0-9]{8,16}")) { // password regex
                tfPassword.setBorder(null);
            } else {
                tfPassword.setTooltip(new Tooltip("Password must be between 8 and 16 characters. No special characters allowed.")); // will appear when hovering over textfield
                tfPassword.setStyle("-fx-border-color: red ; -fx-border-width: 1px ;");
                tfPassword.setVisible(true);
                tfPassword.requestFocus();
                flag = true;
            }
        });

        tfConfirmPassword.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (tfConfirmPassword.getText().equals(tfPassword.getText())) { // simple check to see this field's text matches the password typed above
                tfConfirmPassword.setBorder(null);
                createAccountBtn.setDisable(false);
            } else {
                tfConfirmPassword.setTooltip(new Tooltip("Password must match.")); // will appear when hovering over textfield
                tfConfirmPassword.setStyle("-fx-border-color: red ; -fx-border-width: 1px ;");
                tfConfirmPassword.setVisible(true);
                tfConfirmPassword.requestFocus();
                flag = true;
            }
        });

    }

    @FXML
    protected void createAccountAction(ActionEvent event) throws IOException, InterruptedException {
        Person newUser = new Person(tfUsername.getText(), tfPassword.getText(), tfName.getText(), tfMajor.getText(), Integer.parseInt(tfAge.getText()));
        access.addData();
        UserSession.getInstance(tfUsername.getText(), tfPassword.getText(), "REGISTERED");
        userHasRegisteredStatus = true;

        // perform db operation
        if (dbConnection.insertUser(newUser)) {
            Thread.sleep(1500);
            returnToLogin(event);
        }

    }

    private void returnToLogin(ActionEvent event) throws IOException {

        Scene scene = new Scene(App.loadFXML("login-screen.fxml"));
        scene.getStylesheets().add(getClass().getResource("lightTheme.css").toExternalForm());
        scene.getStylesheets().add("lightTheme.css");
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    public static boolean userHasRegistered() {
        return userHasRegisteredStatus;
    }

}
