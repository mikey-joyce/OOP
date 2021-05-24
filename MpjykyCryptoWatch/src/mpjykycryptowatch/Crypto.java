/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mpjykycryptowatch;

/**
 *
 * @author mikeyJoyce
 */
public class Crypto {
    private String name;
    private String id;
    private String image;
    
    public Crypto(String name, String id, String image){
        this.name = name;
        this.id = id;
        this.image = image;
    }
    
    public String getName(){
        return this.name;
    }
    
    public String getID(){
        return this.id;
    }
    
    public String getImage(){
        return this.image;
    }
    
    public void setName(String name){
        this.name = name;
    }
    
    public void setID(String id){
        this.id = id;
    }
    
    public void setImage(String image){
        this.image = image;
    }
}
