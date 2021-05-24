/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mpjykytimerstopwatchs21;

import java.io.IOException;
import java.util.Optional;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextInputDialog;
import javafx.stage.Stage;

/**
 *
 * @author Mikey Joyce
 * @reference https://stackoverflow.com/questions/6456219/java-checking-if-parseint-throws-exception
 */
public class MpjykyTimerStopwatchS21 extends Application { 
    @Override
    public void start(Stage primaryStage) throws IOException {
        AnalogStopWatch myWatch = new AnalogStopWatch();
        
        myWatch.validateInput();
        
        Scene myScene = new Scene(myWatch.getRoot(), 350, 500);
        primaryStage.setTitle("Stop Watch");
        primaryStage.setScene(myScene);
        primaryStage.show();
        
        myWatch.setUpTimer();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}
