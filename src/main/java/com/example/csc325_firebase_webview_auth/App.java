package com.example.csc325_firebase_webview_auth;


import com.google.cloud.firestore.Firestore;
import com.google.firebase.auth.FirebaseAuth;
import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * JavaFX App
 */
public class App extends Application {
//https://www.youtube.com/watch?v=pmASVHuh1e0&t=579s
    public static Firestore fstore;
    //enabling firestore
    public static FirebaseAuth fauth;
    public static Scene scene;
    private final FirestoreContext contxtFirebase = new FirestoreContext();

    private static boolean lightMode = true;

    @Override
    public void start(Stage primaryStage) throws Exception {
        fstore = contxtFirebase.firebase();
        fauth = FirebaseAuth.getInstance();
        scene = new Scene(loadFXML("login-screen.fxml"));
        //scene.getStylesheets().add(getClass().getResource("lightTheme.css").toExternalForm());
        setTheme(scene);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    public static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml));
        return fxmlLoader.load();
    }


    public static void changeTheme() {

        scene.getStylesheets().clear(); // Remove existing stylesheets

        if (lightMode) {
            scene.getStylesheets().add(App.class.getResource("darkTheme.css").toExternalForm());
            lightMode = false;
        } else {
            scene.getStylesheets().add(App.class.getResource("lightTheme.css").toExternalForm());
            lightMode = true;
        }
    }

    public static void setTheme(Scene scene) {
        scene.getStylesheets().clear(); // Remove existing stylesheets

        if (lightMode) {
            scene.getStylesheets().add(App.class.getResource("lightTheme.css").toExternalForm());
        } else {
            scene.getStylesheets().add(App.class.getResource("darkTheme.css").toExternalForm());
        }
    }

    public static boolean getLightMode(){
        return lightMode;
    }
    public static void setLightMode(boolean b){
        lightMode = b;
    }

    public static void main(String[] args) {
        launch(args);
    }

}
