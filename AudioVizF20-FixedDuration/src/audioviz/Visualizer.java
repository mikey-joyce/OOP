/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package audioviz;

import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

/**
 *
 * @author Professor Wergeles 
 */
public interface Visualizer {
    public void setup(Integer numBands, AnchorPane vizPane, VBox box);//Mikey Joyce modified this function
    public void destroy();
    public String getVizName();
    public void visualize(double timestamp, double lenght, float[] magnitudes, float[] phases);
    public String getImage();
}
