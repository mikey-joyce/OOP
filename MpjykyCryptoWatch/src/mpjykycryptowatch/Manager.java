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
public interface Manager {
    public int hasUserSaved(String user);
    public String parsePrice(String id, String response);
    public String callApi(Crypto myCrypto);
    public void deleteCryptoFromUser(String crypto, String file) throws FileNotFoundException, IOException;
}
