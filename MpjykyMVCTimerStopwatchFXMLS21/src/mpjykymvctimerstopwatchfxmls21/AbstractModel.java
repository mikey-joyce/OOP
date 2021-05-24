/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mpjykymvctimerstopwatchfxmls21;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;

/**
 *
 * @author mikeyJoyce
 * @reference https://www.oracle.com/technical-resources/articles/java/java-se-app-design-with-mvc.html
 * @reference https://stackoverflow.com/questions/6456219/java-checking-if-parseint-throws-exception
 */

//Start of reference from https://www.oracle.com/technical-resources/articles/java/java-se-app-design-with-mvc.html
public abstract class AbstractModel{

    protected PropertyChangeSupport propertyChangeSupport;
    protected Timeline timeline;
    protected KeyFrame keyframe;
    protected double secondsElapsed;
    protected double tickTimeInSeconds;

    public AbstractModel(){
        propertyChangeSupport = new PropertyChangeSupport(this);
        secondsElapsed = 0.0;
        tickTimeInSeconds = 0.01;
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        propertyChangeSupport.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        propertyChangeSupport.removePropertyChangeListener(listener);
    }

    protected void firePropertyChange(String propertyName, Object oldValue, Object newValue) {
        propertyChangeSupport.firePropertyChange(propertyName, oldValue, newValue);
    }
    //End of reference from https://www.oracle.com/technical-resources/articles/java/java-se-app-design-with-mvc.html
    
    protected boolean isRunning(){
        if(this.timeline != null){
            if(this.timeline.getStatus() == Animation.Status.RUNNING){
                return true;
            }
        }
        return false;
    }
    
    //start of reference https://stackoverflow.com/questions/6456219/java-checking-if-parseint-throws-exception
    public static Boolean isParseable(String input){
        try{
            Integer.parseInt(input);
            return true;
        }
        catch(final NumberFormatException e){
            return false;
        }
    }
    //end of reference https://stackoverflow.com/questions/6456219/java-checking-if-parseint-throws-exception
    
    public void reset(){
        timeline.stop();
        secondsElapsed = 0;
    }
    
    public void pauseTimeline(){
        timeline.pause();
    }
    
    public void start(){
        timeline.play();
    }
}