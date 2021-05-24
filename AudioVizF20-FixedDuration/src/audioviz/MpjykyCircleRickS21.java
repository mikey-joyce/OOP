/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package audioviz;

import static java.lang.Integer.min;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.scene.shape.ArcType;
import java.util.concurrent.ThreadLocalRandom;

/**
 *
 * @author Mikey Joyce
 */
public class MpjykyCircleRickS21 implements Visualizer{
    private final String name;
    
    private Integer numOfBands;
    private AnchorPane vizPane;
    private ImageView baseAudioImage;
    
    private final Double bandHeightPercentage;
    private final Double minArcRadius;
    
    private Double width;
    private Double height;
    
    private Double bandWidth;
    private Double bandHeight;
    private Double halfBandHeight;
    
    private final Double startHue;
    private final Double otherHue;
    
    private Arc[] arcs;
    
    private final String image;
    private boolean imageFlag;
    
    public MpjykyCircleRickS21(){
        name = "MpjykyCircleRickS21";
        image = "MpjykyCircleRick.png";
        imageFlag = false;
        
        bandHeightPercentage = 1.3;
        minArcRadius = 107.0;
        width = 0.0;
        height = 0.0;
        bandWidth = 0.0;
        bandHeight = 0.0;
        halfBandHeight = 0.0;
        startHue = 260.0;
        otherHue = 80.0;
    }
    
    @Override
    public void setup(Integer numOfBands, AnchorPane vizPane, VBox box){
        destroy();
        
        this.numOfBands = numOfBands;
        this.vizPane = vizPane;
        
        imageFlag = true;
        baseAudioImage = new ImageView();
        baseAudioImage.setId("currentImage");
        
        this.vizPane.getChildren().add(baseAudioImage);
        baseAudioImage.setImage(new Image(getClass().getResourceAsStream(image)));
        
        baseAudioImage.setFitHeight(225);
        baseAudioImage.setFitWidth(300);
        
        baseAudioImage.setX(220);
        baseAudioImage.setY(0);
        
        height = vizPane.getHeight();
        width = vizPane.getWidth();
        
        bandWidth = width / numOfBands;
        bandHeight = height * bandHeightPercentage;
        halfBandHeight = bandHeight / 2;
        
        arcs = new Arc[numOfBands];
        double arcLength = -1*360/numOfBands;
        
        for(int i=0; i<numOfBands; i++){
            Arc arc = new Arc();
            arc.setCenterX((width/2)-5);
            arc.setCenterY((height/2)-65);
            arc.setCenterY((height/2)-65);
            arc.setRadiusX(minArcRadius);
            arc.setRadiusY(minArcRadius);
            arc.setStartAngle(i*arcLength);
            arc.setLength(arcLength);
            arc.setType(ArcType.ROUND);
            arc.setStroke(Color.hsb(otherHue, 1.0, 1.0, 1.0));
            arc.setStrokeWidth(2);
            arc.setFill(Color.hsb(startHue, 1.0, 1.0, 1.0));
            vizPane.getChildren().addAll(arc);
            arc.toBack();
            arcs[i] = arc;
        }
    }
    
    @Override
    public void destroy(){
        if(arcs != null){
            for(Arc arc: arcs){
                vizPane.getChildren().remove(arc);
            }
            
            arcs = null;
        }
         
        if(imageFlag == true){
            vizPane.getChildren().remove(baseAudioImage);
            imageFlag = false;
        }
    }
    
    @Override
    public String getVizName(){
        return name;
    }
    
    @Override
    public void visualize(double timestamp, double lenght, float[] magnitudes, float[] phases){
        if(arcs == null){
            return;
        }
        
        Integer num = min(arcs.length, magnitudes.length);
        
        for (int i = 0; i < num; i++) {
            int randomNum = ThreadLocalRandom.current().nextInt(7, 17);
            arcs[i].setRadiusX(((60.0 + magnitudes[i])/60.0) * (1.2*bandHeight) + minArcRadius + randomNum);
            arcs[i].setRadiusY( ((60.0 + magnitudes[i])/60.0) * (1.2*bandHeight) + minArcRadius + randomNum);
            arcs[i].setFill(Color.hsb(startHue - (magnitudes[i] * -6.0), 1.0, 1.0, 1.0));
            arcs[i].setStroke(Color.hsb(otherHue + (magnitudes[i] * -6.0), 1.0, 1.0, 1.0));
        }
    }
    
    @Override
    public String getImage(){
        return image;
    }
}
