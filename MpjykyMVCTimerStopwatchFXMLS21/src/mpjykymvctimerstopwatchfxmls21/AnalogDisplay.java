/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mpjykymvctimerstopwatchfxmls21;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.util.Duration;

/**
 *
 * @author mikeyJoyce
 */
public class AnalogDisplay extends AbstractModel{
    private double angleDeltaPerSeconds;
    
    AnalogDisplay(){
        super();
        tickTimeInSeconds = 0.01;
        angleDeltaPerSeconds = 6.0;
        secondsElapsed = 0.0;
    }
    
    public void calculateRotation(){
        boolean wasItRunning = false;
        
        if(isRunning()){
            timeline.pause();
            wasItRunning = true;
        }
       
        keyframe = new KeyFrame(Duration.millis(1000 * tickTimeInSeconds), 
            (ActionEvent actionEvent) -> {
                secondsElapsed += tickTimeInSeconds;
                //calculates rotation
                firePropertyChange("SetRotate", null, (secondsElapsed * angleDeltaPerSeconds));
        });
        
        timeline = new Timeline(keyframe);
        timeline.setCycleCount(Animation.INDEFINITE);
        
        if(wasItRunning){
            timeline.play();
        }
    }
    
    @Override
    public void reset(){
        super.reset();
        firePropertyChange("SetRotate", null, 0);
    }
}
