package com.example.csc325_firebase_webview_auth.modelview;

import com.example.csc325_firebase_webview_auth.App;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.WriteResult;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class CreateAccountController implements Initializable {


    @FXML
    public PasswordField tfConfirmPassword, tfPassword;
    @FXML
    private TextField tfUsername, tfName, tfMajor, tfAge;
    public Label labelSuccessMsg;
    public Button createAccountButton;
    private static boolean userHasRegisteredStatus = false;
    private boolean flag = false;

    private Scene scene;
    private Stage stage;

    private AccessFBView access = new AccessFBView();
    private LoginController loginController;

    @FXML
    private Text outputLabel;




    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        List<TextField> textFieldList = List.of(tfUsername, tfPassword, tfConfirmPassword, tfName, tfMajor,tfAge);
        createAccountButton.setDisable(true); // initially disable add button
        outputLabel.setText("Enter \na \nusername");
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
                outputLabel.setText("Enter \nyour full \nname");
                tfUsername.setBorder(null);
                tfName.setEditable(true);
            } else {
                tfUsername.setTooltip(new Tooltip("Username must be between 2 and 16 characters.")); // will appear when hovering over textfield
                tfUsername.setStyle("-fx-border-color: red ; -fx-border-width: 1px ;");
                outputLabel.setText("INVALID USERNAME");
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
                outputLabel.setText("Enter \nyour \nmajor");
                tfName.setBorder(null);
                tfMajor.setEditable(true);
            } else {
                tfName.setTooltip(new Tooltip("Name can be between 2-12 letters, first and last name.")); // will appear when hovering over textfield
                tfName.setStyle("-fx-border-color: red ; -fx-border-width: 1px ;");
                outputLabel.setText("INVALID \nNAME \nENTRY");
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
                outputLabel.setText("Enter \nyour \nage");
                tfMajor.setBorder(null);
                tfAge.setEditable(true);
            } else {
                tfMajor.setTooltip(new Tooltip("Majors must be 3 characters. Ex: \'CSC\' Oor \'PSY\'")); // will appear when hovering over textfield
                tfMajor.setStyle("-fx-border-color: red ; -fx-border-width: 1px ;");
                outputLabel.setText("INVALID \nMAJOR \nENTRY");
                tfMajor.setVisible(true);
                tfAge.setEditable(false);
                tfPassword.setEditable(false);
                tfConfirmPassword.setEditable(false);
                tfMajor.requestFocus();
                flag = true;
            }
        });

        tfAge.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (tfAge.getText().matches("(1[3-9]||[2-9][0-9])$")) {
                outputLabel.setText("Enter \na \npassword");
                tfAge.setBorder(null);
                tfPassword.setEditable(true);
                tfConfirmPassword.setEditable(true);

            } else {
                tfAge.setTooltip(new Tooltip("Invalid age. must be at least 13 and no more than 99 years old.")); // will appear when hovering over textfield
                tfAge.setStyle("-fx-border-color: red ; -fx-border-width: 1px ;");
                outputLabel.setText("INVALID \nAGE \nENTRY");
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
                outputLabel.setText("REENTER \nPASSWORD");
            } else {
                tfPassword.setTooltip(new Tooltip("Password must be between 8 and 16 characters. No special characters allowed.")); // will appear when hovering over textfield
                tfPassword.setStyle("-fx-border-color: red ; -fx-border-width: 1px ;");
                outputLabel.setText("INVALID \nPASSWORD \nENTRY");
                tfPassword.setVisible(true);
                tfPassword.requestFocus();
                flag = true;
            }
        });

        tfConfirmPassword.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (tfConfirmPassword.getText().equals(tfPassword.getText())) { // simple check to see this field's text matches the password typed above
                tfConfirmPassword.setBorder(null);
                createAccountButton.setDisable(false);
            } else {
                tfConfirmPassword.setTooltip(new Tooltip("Password must match.")); // will appear when hovering over textfield
                tfConfirmPassword.setStyle("-fx-border-color: red ; -fx-border-width: 1px ;");
                outputLabel.setText("PASSWORDS \nDO NOT \nMATCH");
                tfConfirmPassword.setVisible(true);
                tfConfirmPassword.requestFocus();
                createAccountButton.setDisable(false);
                flag = true;
            }
        });

    }

    @FXML
    protected void createAccountAction(ActionEvent event) throws IOException, InterruptedException {
        DocumentReference docRef = App.fstore.collection("References").document(UUID.randomUUID().toString());

        Map<String, Object> data = new HashMap<>();
        data.put("username", tfUsername.getText());
        data.put("password", tfPassword.getText());
        data.put("name", tfName.getText());
        data.put("major", tfMajor.getText());
        data.put("age", Integer.parseInt(tfAge.getText()));
        //asynchronously write data
        ApiFuture<WriteResult> result = docRef.set(data);

        scene = App.getScene();
        scene = new Scene(App.loadFXML("AccessFBView.fxml"));
        App.setScene(scene);
        App.setStage();
    }

    @FXML
    private void returnToLogin(ActionEvent event) throws IOException {
        App.setScene(new Scene(App.loadFXML("login-screen.fxml")));
        App.setStage();
    }

    public static boolean userHasRegistered() {
        return userHasRegisteredStatus;
    }

}
