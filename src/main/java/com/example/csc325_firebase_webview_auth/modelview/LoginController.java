package com.example.csc325_firebase_webview_auth.modelview;

import com.example.csc325_firebase_webview_auth.App;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.IndexedCell;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginController {

    @FXML
    private Button createAccountButton = new Button();




    @FXML
    protected void loginBtnAction(ActionEvent event) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/com/example/csc325_firebase_webview_auth/AccessFBView.fxml").toURI().toURL());
        Scene scene = new Scene(root);
        //scene.getStylesheets().add(getClass().getResource("/dev/smartstacks/smartstacks/styling/style.css").toExternalForm());
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    protected void createAccountAction(ActionEvent event) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/com/example/csc325_firebase_webview_auth/create-account.fxml").toURI().toURL());
        Scene scene = new Scene(root);
        App.setTheme(scene);
        //scene.getStylesheets().add(getClass().getResource("/dev/smartstacks/smartstacks/styling/lightTheme.css").toExternalForm());
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    public void exitOnAction(ActionEvent actionEvent) {
        System.exit(0);
    }


}


