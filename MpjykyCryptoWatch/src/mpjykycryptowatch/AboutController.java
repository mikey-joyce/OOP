/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mpjykycryptowatch;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.Event;
import javafx.fxml.Initializable;

/**
 * FXML Controller class
 *
 * @author mikeyJoyce
 */
public class AboutController extends Switchable implements Initializable {

    /**
     * Initializes the controller class.
     */
    public void goToHome(Event event){
        Switchable.switchTo("HomePage");
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
