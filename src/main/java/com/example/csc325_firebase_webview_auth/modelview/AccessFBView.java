package com.example.csc325_firebase_webview_auth.modelview;//package modelview;

import com.example.csc325_firebase_webview_auth.App;
import com.example.csc325_firebase_webview_auth.models.Person;
import com.example.csc325_firebase_webview_auth.viewmodel.AccessDataViewModel;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;

import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.concurrent.ExecutionException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import org.w3c.dom.Text;

public class AccessFBView implements Initializable {


    @FXML
    private TextField tfName;
    @FXML
    private TextField tfMajor;
    @FXML
    private TextField tfAge;

    @FXML
    private TextField tfUsername, tfPassword, tfConfirmPassword;
    @FXML
    private Button writeButton;
    @FXML
    private Button readButton, regButton, switchroot;
    @FXML
    private TextArea outputField;
    private boolean key;
    private ObservableList<Person> listOfUsers = FXCollections.observableArrayList();
    private Person person;

    public ObservableList<Person> getListOfUsers() {
        return listOfUsers;
    }

    private boolean flag;
    private Scene scene;

    @FXML
    private AnchorPane background;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        AccessDataViewModel accessDataViewModel = new AccessDataViewModel();
        tfName.textProperty().bindBidirectional(accessDataViewModel.userNameProperty());
        tfMajor.textProperty().bindBidirectional(accessDataViewModel.userMajorProperty());
        writeButton.disableProperty().bind(accessDataViewModel.isWritePossibleProperty().not());

        scene = App.getScene();
        background.requestFocus();
        background.setFocusTraversable(true);

        List<TextField> textFieldList = List.of(tfUsername, tfPassword, tfConfirmPassword, tfName, tfMajor,tfAge);
        regButton.setDisable(true); // initially disable add button

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
                tfName.setBorder(null);
                tfMajor.setEditable(true);
            } else {
                tfName.setTooltip(new Tooltip("Name can be between 2-12 letters, first and last name.")); // will appear when hovering over textfield
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
                tfMajor.setBorder(null);
                tfAge.setEditable(true);
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
            if (tfAge.getText().matches("(1[3-9]||[2-9][0-9])$")) {
                tfAge.setBorder(null);
                tfPassword.setEditable(true);
                tfConfirmPassword.setEditable(true);

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
                regButton.setDisable(false);
            } else {
                tfConfirmPassword.setTooltip(new Tooltip("Password must match.")); // will appear when hovering over textfield
                tfConfirmPassword.setStyle("-fx-border-color: red ; -fx-border-width: 1px ;");
                tfConfirmPassword.setVisible(true);
                tfConfirmPassword.requestFocus();
                regButton.setDisable(false);
                flag = true;
            }
        });

    }

    @FXML
    private void addRecord(ActionEvent event) {
        addData();
    }

    @FXML
    private void readRecord(ActionEvent event) {
        readFirebase();
    }

    @FXML
    private void regRecord(ActionEvent event) {
        registerUser();
    }

    @FXML
    private void switchToSecondary() throws IOException {
        App.setRoot("WebContainer.fxml");
    }

    public void addData() {

        DocumentReference docRef = App.fstore.collection("References").document(UUID.randomUUID().toString());

        Map<String, Object> data = new HashMap<>();
        data.put("username", tfUsername.getText());
        data.put("password", tfName.getText());
        data.put("name", tfName.getText());
        data.put("major", tfMajor.getText());
        data.put("age", Integer.parseInt(tfAge.getText()));
        //asynchronously write data
        ApiFuture<WriteResult> result = docRef.set(data);
    }

    public boolean readFirebase() {
        key = false;

        //asynchronously retrieve all documents
        ApiFuture<QuerySnapshot> future = App.fstore.collection("References").get();
        // future.get() blocks on response
        List<QueryDocumentSnapshot> documents;
        try {
            documents = future.get().getDocuments();
            if (documents.size() > 0) {
                System.out.println("Outing....");
                for (QueryDocumentSnapshot document : documents) {
                    outputField.setText(outputField.getText() + document.getData().get("name") +
                            " , Major: " + document.getData().get("major") +
                            " , Age: " + document.getData().get("age") +
                            " , Username: " + document.getData().get("username") +
                            " , Password: " + document.getData().get("password") + " \n ");
                    System.out.println(document.getId() + " => " + document.getData().get("name"));
                    person = new Person(document.getData().get("username").toString(),document.getData().get("password").toString(), String.valueOf(document.getData().get("name")),
                            document.getData().get("major").toString(),
                            Integer.parseInt(document.getData().get("age").toString()));
                    listOfUsers.add(person);
                }
            } else {
                System.out.println("No data");
            }
            key = true;

        } catch (InterruptedException | ExecutionException ex) {
            ex.printStackTrace();
        }
        return key;
    }

    public void sendVerificationEmail() {
        try {
            UserRecord user = App.fauth.getUser("name");
            //String url = user.getPassword();

        } catch (Exception e) {
        }
    }

    public boolean registerUser() {
        UserRecord.CreateRequest request = new UserRecord.CreateRequest()
                .setEmail("user@example.com")
                .setEmailVerified(false)
                .setPassword("secretPassword")
                .setPhoneNumber("+11234567890")
                .setDisplayName("John Doe")
                .setDisabled(false);

        UserRecord userRecord;
        try {
            userRecord = App.fauth.createUser(request);
            System.out.println("Successfully created new user: " + userRecord.getUid());
            return true;

        } catch (FirebaseAuthException ex) {
            // Logger.getLogger(FirestoreContext.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }

    }


    public void setScene(Scene scene) {
        this.scene = scene;
    }

    public Scene getScene() {
        return scene;
    }
}
