/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mpjykycryptowatch;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 *
 * @author mikeyJoyce
 * @reference https://stackoverflow.com/questions/24565539/in-need-of-javafx-help-writing-to-a-txt-file
 */
public class LoginModel extends Login{
    
    public LoginModel(){}
    
    public boolean verifyLogin(String username, String password) throws FileNotFoundException, IOException{
        try (FileInputStream myStream = new FileInputStream(username + ".txt")) {
            BufferedReader br = new BufferedReader(new InputStreamReader(myStream));
            
            if(br.readLine().equals(username)){
                if(br.readLine().equals(password)){
                    myStream.close();
                    return true;
                }
            }
        }catch (FileNotFoundException f){
        }
        
        return false;
    }
    
    //Start of reference from https://stackoverflow.com/questions/24565539/in-need-of-javafx-help-writing-to-a-txt-file
    public void writeNewUser(String username, String password){
        File myFile = new File(username + ".txt");
        
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(myFile, true))){
            bw.write(username);
            bw.newLine();
            bw.write(password);
            bw.newLine();
        } 
        catch (IOException e){
            e.printStackTrace();
        }
    }
    //End of reference from https://stackoverflow.com/questions/24565539/in-need-of-javafx-help-writing-to-a-txt-file
}
