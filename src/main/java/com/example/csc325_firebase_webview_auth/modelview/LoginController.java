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
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.scene.text.Text;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    @FXML
    private Button createAccountButton = new Button();

    private AccessFBView accessFBView = new AccessFBView();
    private CreateAccountController createAccountController;

    private Scene scene;
    private Stage stage;

    @FXML
    private Text outputLabel;

    @FXML
    private TextField tfUsername, tfPassword;






    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ////anchorBackground.requestFocus();
        //stage = App.getStage();
        //scene = App.getScene();
        outputLabel.setText("Enter a username");

        tfUsername.setOnMouseClicked(e->{

            outputLabel.setText("Enter a username");
        });

        tfPassword.setOnMouseClicked(e->{
            outputLabel.setText("Enter a password");
        });




    }

    @FXML
    protected void loginBtnAction(ActionEvent event) throws Exception {
        scene = App.getScene();
        scene = new Scene(App.loadFXML("AccessFBView.fxml"));
        App.setScene(scene);
        App.setStage();


    }
    @FXML
    private void createAccountAction(ActionEvent event) throws Exception {
        scene = App.getScene();
        scene = new Scene(App.loadFXML("create-account.fxml"));
        App.setScene(scene);
        App.setStage();


    }


    public void exitOnAction(ActionEvent actionEvent) {
        System.exit(0);
    }

    public  void setScene(){
        scene = App.getScene();
    }

    public Scene getScene(){
        return scene;
    }





}


