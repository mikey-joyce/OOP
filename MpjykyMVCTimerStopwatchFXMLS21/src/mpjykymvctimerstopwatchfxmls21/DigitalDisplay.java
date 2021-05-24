/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mpjykymvctimerstopwatchfxmls21;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.scene.chart.XYChart;
import javafx.scene.text.Font;
import javafx.util.Duration;

/**
 *
 * @author mikeyJoyce
 */
public class DigitalDisplay extends AbstractModel{
    private KeyFrame keyframe1;
    private final Double timerFontSize;
    private final Double dataFontSize;
    private final String defaultStart;
    private final String lapDefaultStart;
    private final String avgLapDefaultStart;
    private XYChart.Series<String, Number> recordSeries;
    private XYChart.Series<String, Number> avgSeries;
    ArrayList<Double> meanData;
    private final DecimalFormat myFormat; //for my timer
    
    private double finalInput;
    private int centiSeconds;
    private int seconds;
    private int minutes;
    private int newRecordCentiSeconds;
    private int newRecordSeconds;
    private int newRecordMinutes;
    private int recordCount;
    
    
    DigitalDisplay(Double input){
        super();
        timerFontSize = 50.0;
        dataFontSize = 20.0;
        defaultStart = "--:--.--";
        lapDefaultStart = "Lap -: " + defaultStart;
        avgLapDefaultStart = "Average Lap Time: " + defaultStart;
        recordSeries = new XYChart.Series<>();
        avgSeries = new XYChart.Series<>();
        meanData = new ArrayList<>();
        recordSeries.setName("Recorded Times");
        avgSeries.setName("Average of Recorded Times");
        myFormat = new DecimalFormat("#00.00");
        secondsElapsed = 0.0;
        tickTimeInSeconds = 0.01;
        finalInput = 0.0;
        centiSeconds = 0;
        seconds = 0;
        minutes = 0;
        newRecordCentiSeconds = 0;
        newRecordSeconds = 0;
        newRecordMinutes = 0;
        recordCount = 0;
        finalInput = input;
    }
    
    public void calculateTotalTime(){
        boolean wasItRunning = false;
        int zeroFlag = 0;

        if(isRunning()){
            timeline.pause();
            wasItRunning = true;
        }
        
        keyframe = new KeyFrame(Duration.millis(10), 
            (ActionEvent actionEvent) -> {
                centiSeconds++;
                newRecordCentiSeconds++;
                
                if(centiSeconds == 100){
                    centiSeconds = 0;
                    newRecordCentiSeconds = 0;
                    seconds++;
                    newRecordSeconds++;
                }

                firePropertyChange("TotalTime", null, calculateTimes(centiSeconds, seconds, minutes));

                if(seconds == 60){
                    seconds = 0;
                    newRecordSeconds = 0;
                    minutes++;
                    newRecordMinutes++;
                }
        });
        
        updateTimer();
        
        timeline = new Timeline(keyframe, keyframe1);
        timeline.setCycleCount(Animation.INDEFINITE);
        
        if(wasItRunning){
            timeline.play();
        }
    }
    
    public void updateTimer(){
        keyframe1 = new KeyFrame(Duration.millis(1000 * tickTimeInSeconds), 
            (ActionEvent actionEvent) -> {
                secondsElapsed += tickTimeInSeconds;

                if(finalInput <= secondsElapsed){
                    firePropertyChange("TimerText", null, "Timer: 00:00");
                }
                else{
                    firePropertyChange("TimerText", null, "Timer:  " + myFormat.format(finalInput - secondsElapsed));
                }
        });
    }
    
    @Override
    public void reset(){
        super.reset();
        recordSeries.getData().clear();
        avgSeries.getData().clear();
        meanData.clear();
        firePropertyChange("TimerText", null, ("Timer:  " + myFormat.format(finalInput)));
        firePropertyChange("TotalTime", null, defaultStart);
        firePropertyChange("LapTime", null, lapDefaultStart);
        firePropertyChange("AvgLapTime", null, avgLapDefaultStart);
        recordCount = 0;
        centiSeconds = 0;
        seconds = 0;
        minutes = 0;
        newRecordCentiSeconds = 0;
        newRecordSeconds = 0;
        newRecordMinutes = 0;
    }
    
    public void updateRecords(){
        recordCount++;
        meanData.add(parseCurrentRecord());
        
        if(finalInput <= secondsElapsed){
            firePropertyChange("Error", null, null);
        }
        else{
            if(setAvgLapTime()){
                if(recordCount < 10){
                    firePropertyChange("LapTime", null, ("Lap 0" + recordCount + ": " + calculateTimes(newRecordCentiSeconds, newRecordSeconds, newRecordMinutes)));
                }
                else{
                    firePropertyChange("LapTime", null, ("Lap " + recordCount + ": " + calculateTimes(newRecordCentiSeconds, newRecordSeconds, newRecordMinutes)));
                }
            }
        }

        avgSeries.getData().add(new XYChart.Data<>(String.valueOf(recordCount), getMean()));
        recordSeries.getData().add(new XYChart.Data<>(String.valueOf(recordCount), getMeanDataNode(1)));
        
        newRecordCentiSeconds = 0;
        newRecordSeconds = 0;
        newRecordMinutes = 0;
    }
    
    public boolean setAvgLapTime(){
        int avgMinutes = 0;
        int avgSeconds = 0;
        int avgCenti = 0;
        double myMean = getMean();
        
        while((myMean - 60) > 0){
            avgMinutes++;
            myMean -= 60;
        }
        
        String temp = String.valueOf(round(myMean, 2));
        int decimalIndex = temp.indexOf(".");
        
        if(isParseable(temp.substring(0, decimalIndex)) && isParseable(temp.substring(decimalIndex+1))){
            firePropertyChange("AvgLapTime", null, ("Average Lap Time: " + calculateTimes(Integer.parseInt(temp.substring(decimalIndex+1)), Integer.parseInt(temp.substring(0, decimalIndex)), avgMinutes)));
            return true;
        }
        
        return false;
    }
    
    public double parseCurrentRecord(){
        int minToSeconds = (newRecordMinutes * 60) + newRecordSeconds;
        String currentRecord = String.valueOf(minToSeconds) + "." + String.valueOf(newRecordCentiSeconds);
        return Double.parseDouble(currentRecord);
    }
    
    public double getMean(){
        double total=0;
        
        for(int i = 0; i < meanData.size(); i++){
            total += meanData.get(i);
        }
        
        return total/(meanData.size());
    }
    
    public double getMeanDataNode(int node){
        return meanData.get(meanData.size() - node);
    }
    
    public String calculateTimes(int centiSeconds, int seconds, int minutes){
        if(minutes < 10){
            if(seconds >= 10 && seconds <= 59){
                if(centiSeconds >= 10 && centiSeconds <=99){
                    return ("0" + minutes + ":" + seconds + "." + centiSeconds);
                }
                else{
                    return ("0" + minutes + ":" + seconds + ".0" + centiSeconds);
                }
            }
            else{
                if(centiSeconds >= 10 && centiSeconds <= 99){
                    return ("0" + minutes + ":0" + seconds + "." + centiSeconds);
                }
                else{
                    return ("0" + minutes + ":0" + seconds + ".0" + centiSeconds);
                }
            }
        }
        else{
            if(seconds >= 10 && seconds <= 60){
                if(centiSeconds >= 10 && centiSeconds <= 99){
                    return (minutes + ":" + seconds + "." + centiSeconds);
                }
                else{
                    return (minutes + ":" + seconds + ".0" + centiSeconds);
                }
            }
            else{
                if(centiSeconds >= 10 && centiSeconds <= 99){
                    return (minutes + ":0" + seconds + "." + centiSeconds);
                }
                else{
                    return (minutes + ":0" + seconds + ".0" + centiSeconds);
                }
            }
        }
    }
    
    //Start of reference from https://stackoverflow.com/questions/2808535/round-a-double-to-2-decimal-places
    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
    //End of reference from https://stackoverflow.com/questions/2808535/round-a-double-to-2-decimal-places
    
    public Font getFont(boolean flag){
        if(flag){
            return new Font("System", timerFontSize);
        }
        
        return new Font("System", dataFontSize);
    }
    
    public String getStartingString(int flag){
        switch(flag){
            case 1:
                return "Timer: ";
            case 2:
                return lapDefaultStart;
            case 3:
                return avgLapDefaultStart;
            default:
                return defaultStart;
        }
    }
    
    public XYChart.Series getSeries(boolean flag){
        if(flag){
            return recordSeries;
        }
        
        return avgSeries;
    }
}
