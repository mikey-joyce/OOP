/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mpjykycryptowatch;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author mikeyJoyce
 * 
 * @reference Prof Wergeles: https://www.youtube.com/watch?v=Vp6SgexAkZQ
 * @reference https://stackoverflow.com/questions/24565539/in-need-of-javafx-help-writing-to-a-txt-file
 */
public class LoginController extends Switchable implements Initializable {

    @FXML
    private Text labelStatus;
    
    @FXML
    private TextField usernameTextField;
    
    @FXML
    private PasswordField passwordTextField;
    
    @FXML
    private Button loginButton;
    
    @FXML
    private Text loginText;
    
    @FXML
    private Text registerButton;
    
    private Stage stage;
    private Scene loginPage;
    private Scene homePage;
    private HomePageController homePageController;
    
    private String userName="";
    private String password="";
    
    private LoginModel login;
    
    @FXML
    private void Login(ActionEvent event) throws Exception{
        if(loginButton.getText().equals("Login")){
            if(login.verifyLogin(usernameTextField.getText(), passwordTextField.getText())){
                userName = usernameTextField.getText();
                labelStatus.setText("Login Success!");
                goToHome();
            }
            else{
                labelStatus.setText("Login Failed!");
            }
        }
        else{
            if(usernameTextField.getText().equals("") || passwordTextField.getText().equals("")){
                labelStatus.setText("Username or password was left blank");
            }
            else{
                userName = usernameTextField.getText();
                password = passwordTextField.getText();
                
                login.writeNewUser(userName, password);
                
                usernameTextField.setText("");
                passwordTextField.setText("");
                goToHome();
            }
        }
    }
    
    //Start of reference from Prof Wergeles code https://www.youtube.com/watch?v=Vp6SgexAkZQ
    public void start(Stage stage){
        this.stage = stage;
        loginPage = stage.getScene();
    }
    
    public void goToHome(){
        Switchable.switchTo("HomePage");
        
        HomePageController controller = (HomePageController) Switchable.getControllerByName("HomePage");
        
        if(controller != null){
            controller.getUser().setText(userName);
        }
    }
    //End of reference from Prof Wergeles code https://www.youtube.com/watch?v=Vp6SgexAkZQ
    
    @FXML
    private void goToRegister(Event event){
        loginButton.setText("Register");
        loginText.setText("Register Account");
        registerButton.setText("");
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        login = new LoginModel();
        loginButton.setText("Login");
        loginText.setText("Sign in");
    }    
    
}
