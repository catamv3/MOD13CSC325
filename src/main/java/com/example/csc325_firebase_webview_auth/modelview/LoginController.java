package com.example.csc325_firebase_webview_auth.modelview;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class LoginController {

    @FXML
    protected void loginBtnAction(ActionEvent event) throws Exception {



        Parent root = FXMLLoader.load(getClass().getResource("/com/example/csc325_firebase_webview_auth/AccessFBView.fxml").toURI().toURL());
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("/dev/smartstacks/smartstacks/styling/style.css").toExternalForm());
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    protected void createAccountAction(ActionEvent event) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/dev/smartstacks/smartstacks/view/create-account.fxml").toURI().toURL());
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("/dev/smartstacks/smartstacks/styling/style.css").toExternalForm());
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    public void exitOnAction(ActionEvent actionEvent) {
        System.exit(0);
    }
}


