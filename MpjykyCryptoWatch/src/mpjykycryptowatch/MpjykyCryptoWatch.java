/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mpjykycryptowatch;

import javafx.application.Application;
import static javafx.application.Application.STYLESHEET_MODENA;
import static javafx.application.Application.launch;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 *
 * THE FILE NAMED "Switchable.java" IN THIS PROJCET WAS REFERENCED FROM PROF WERGELES
 * 
 */

/**
 *
 * @author Mikey Joyce
 * 
 * 
 * @reference Prof Wergeles: https://www.youtube.com/watch?v=05FzRgfxE5k&list=PLpvL1C_oZsr_4BABZUnJN2jqhnN39Q8rx
 * @reference https://www.codota.com/code/java/methods/javafx.stage.Stage/setOnCloseRequest
 */
public class MpjykyCryptoWatch extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        //Start of reference from Prof Wergeles https://www.youtube.com/watch?v=05FzRgfxE5k&list=PLpvL1C_oZsr_4BABZUnJN2jqhnN39Q8rx
        HBox root = new HBox();
        root.setPrefSize(600, 400);
        root.setAlignment(Pos.CENTER);
        Text message = new Text("This is a failure!");
        message.setFont(Font.font(STYLESHEET_MODENA, 32));
        root.getChildren().add(message);
        
        Scene scene = new Scene(root);
        
        Switchable.scene = scene; 
        Switchable.switchTo("Login");
        
        stage.setScene(scene);
        stage.show();
        //End of reference from Prof Wergeles https://www.youtube.com/watch?v=05FzRgfxE5k&list=PLpvL1C_oZsr_4BABZUnJN2jqhnN39Q8rx
        
        stage.setResizable(false);
        
        //Start of reference from https://www.codota.com/code/java/methods/javafx.stage.Stage/setOnCloseRequest
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent e) {
                Platform.exit();
                System.exit(0);
            }
        });
        //End of reference from https://www.codota.com/code/java/methods/javafx.stage.Stage/setOnCloseRequest
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}
