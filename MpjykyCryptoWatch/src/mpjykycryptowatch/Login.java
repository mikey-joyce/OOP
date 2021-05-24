/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mpjykycryptowatch;

import java.io.FileNotFoundException;
import java.io.IOException;

/**
 *
 * @author mikeyJoyce
 */
public abstract class Login {
    public abstract boolean verifyLogin(String username, String password)  throws FileNotFoundException, IOException;
    public abstract void writeNewUser(String username, String password);
}
