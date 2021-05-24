/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mpjykytimerstopwatchs21;

import java.text.DecimalFormat;
import java.util.Optional;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.util.Duration;

/**
 *
 * @author mikeyJoyce
 */
public class AnalogStopWatch {
    TextInputDialog startInput;
    public Timeline timeline;
    public ImageView dialImageView;
    public ImageView handImageView;
    
    public double secondsElapsed = 0.0;
    public double tickTimeInSeconds = 0.01;
    public double angleDeltaPerSeconds = 6.0;
    
    public BorderPane root;
    public StackPane myStackPane;
    
    public Image dialImage;
    public Image handImage;
    public String dialImageName = "clockface.png";
    public String handImageName = "hand.png";
    
    public Label officialTimeLabel = new Label("00:00.00");
    public DecimalFormat myFormat = new DecimalFormat("#00.00"); //for my timer
    public Label timerLabel = new Label("Timer: ");
    public Label lapLabel = new Label("          Lap Time");
    public Label totalLabel = new Label("  Total Time");
    public Label lapTimeLabel1 = new Label("Rec 00 +00:00.00");
    public Label lapTimeLabel2 = new Label("Rec 00 +00:00.00");
    public Label lapTimeLabel3 = new Label("Rec 00 +00:00.00");
    public Label totalTimeLabel1 = new Label("00:00.00");
    public Label totalTimeLabel2 = new Label("00:00.00");
    public Label totalTimeLabel3 = new Label("00:00.00");
    
    public HBox myButtons;
    public HBox lapTotalTimes;
    public VBox lapTimes;
    public VBox totalTimes;
    public VBox officialTimeAndTimer;
    public Button recordButton;
    public Button startButton;
    
    public double width;
    public double height;
    public double input = -1.0;
    public int recordCount = 0;
    public int positionCounter = 1;
    
    public int centiSeconds = 0;
    public int seconds = 0;
    public int minutes = 0;
    public int newRecordCentiSeconds = 0;
    public int newRecordSeconds = 0;
    public int newRecordMinutes = 0;
    
    public AnalogStopWatch(){
        this.startInput = new TextInputDialog("");
        this.startInput.setHeaderText("Set up the start time:");
        this.startInput.setContentText("Enter a positive integer:");
        
        this.root = new BorderPane();
        this.root.setPadding(new Insets(15, 15, 15, 15));
        
        this.myStackPane = new StackPane();
        this.root.setTop(this.myStackPane);
        this.dialImageView = new ImageView();
        this.handImageView = new ImageView();
        this.myStackPane.getChildren().addAll(this.dialImageView, this.handImageView);
        
        this.dialImage = new Image(getClass().getResourceAsStream(dialImageName));
        this.handImage = new Image(getClass().getResourceAsStream(handImageName));
        
        this.width = dialImage.getWidth();
        this.height = dialImage.getHeight();
        
        this.dialImageView.setImage(this.dialImage);
        this.handImageView.setImage(this.handImage);
        
        this.myButtons = new HBox();
        this.recordButton = new Button("Record");
        this.startButton = new Button("Start");
        this.myButtons.getChildren().addAll(this.recordButton, this.startButton);
        this.myButtons.setAlignment(Pos.CENTER);
        this.root.setLeft(this.recordButton);
        this.root.setRight(this.startButton);
        
        this.myButtons.toFront();
        
        this.officialTimeAndTimer = new VBox(10);
        this.officialTimeAndTimer.getChildren().addAll(this.officialTimeLabel, this.timerLabel);
        this.officialTimeAndTimer.setAlignment(Pos.CENTER);
        this.root.setCenter(this.officialTimeAndTimer);
        
        this.lapTimes = new VBox();
        this.totalTimes = new VBox();
        this.lapTimes.setAlignment(Pos.CENTER_LEFT);
        this.totalTimes.setAlignment(Pos.CENTER_RIGHT);
        this.lapTimes.getChildren().addAll(this.lapLabel, this.lapTimeLabel1, this.lapTimeLabel2, this.lapTimeLabel3);
        this.totalTimes.getChildren().addAll(this.totalLabel, this.totalTimeLabel1, this.totalTimeLabel2, this.totalTimeLabel3);
        this.lapTotalTimes = new HBox(150);
        this.lapTotalTimes.getChildren().addAll(lapTimes, totalTimes);
        this.lapTotalTimes.setAlignment(Pos.CENTER);
        this.root.setBottom(this.lapTotalTimes);
        
        officialTimeLabel.setFont(new Font(30));
        
        Alert error = new Alert(Alert.AlertType.INFORMATION);
        error.setHeaderText("Time is up!");
        error.setContentText("No more records...");
        
        this.startButton.setOnAction((ActionEvent event) -> {
            if(!isRunning()){
                this.timeline.play();
                this.startButton.setText("Stop");
                this.recordButton.setText("Record");
            }
            else{
                this.timeline.pause();
                this.startButton.setText("Start");
                this.recordButton.setText("Reset ");
            }
        });
        
        this.recordButton.setOnAction((ActionEvent event) -> {
            if(isRunning()){
                this.recordCount++;
                if(this.input <= this.secondsElapsed){
                    error.showAndWait();
                }
                else{
                    if(this.recordCount < 10){
                        if(positionCounter == 3){
                            this.lapTimeLabel3.setText("Rec 0" + this.recordCount + "+" + calculateTimes(this.newRecordCentiSeconds, this.newRecordSeconds, this.newRecordMinutes));
                            this.totalTimeLabel3.setText(this.officialTimeLabel.getText());
                            positionCounter = 1;
                        }
                        else if(positionCounter == 2){
                            this.lapTimeLabel2.setText("Rec 0" + this.recordCount + "+" + calculateTimes(this.newRecordCentiSeconds, this.newRecordSeconds, this.newRecordMinutes));
                            this.totalTimeLabel2.setText(this.officialTimeLabel.getText());
                            positionCounter++;
                        }
                        else{
                            this.lapTimeLabel1.setText("Rec 0" + this.recordCount + "+" + calculateTimes(this.newRecordCentiSeconds, this.newRecordSeconds, this.newRecordMinutes));
                            this.totalTimeLabel1.setText(this.officialTimeLabel.getText());
                            positionCounter++;
                        }
                    }
                    else{
                        if(positionCounter == 3){
                            this.lapTimeLabel3.setText("Rec " + this.recordCount + "+" + calculateTimes(this.newRecordCentiSeconds, this.newRecordSeconds, this.newRecordMinutes));
                            this.totalTimeLabel3.setText(this.officialTimeLabel.getText());
                            positionCounter = 1;
                        }
                        else if(positionCounter == 2){
                            this.lapTimeLabel2.setText("Rec " + this.recordCount + "+" + calculateTimes(this.newRecordCentiSeconds, this.newRecordSeconds, this.newRecordMinutes));
                            this.totalTimeLabel2.setText(this.officialTimeLabel.getText());
                            positionCounter++;
                        }
                        else{
                            this.lapTimeLabel1.setText("Rec " + this.recordCount + "+" + calculateTimes(this.newRecordCentiSeconds, this.newRecordSeconds, this.newRecordMinutes));
                            this.totalTimeLabel1.setText(this.officialTimeLabel.getText());
                            positionCounter++;
                        }
                    }
                }
            }
            else{
                this.timeline.pause();
                this.recordButton.setText("Record");
                this.startButton.setText("Start");
                this.handImageView.setRotate(0);
                this.secondsElapsed = 0;
                this.officialTimeLabel.setText("00:00.00");
                this.timerLabel.setText("Timer: " + this.input);
                this.lapTimeLabel1.setText("Rec 00 +00:00.00");
                this.lapTimeLabel2.setText("Rec 00 +00:00.00");
                this.lapTimeLabel3.setText("Rec 00 +00:00.00");
                this.totalTimeLabel1.setText("00:00.00");
                this.totalTimeLabel2.setText("00:00.00");
                this.totalTimeLabel3.setText("00:00.00");
                this.secondsElapsed = 0;
                this.minutes = 0;
                this.seconds = 0;
                this.centiSeconds = 0;
                
            }
            
            this.newRecordCentiSeconds = 0;
            this.newRecordSeconds = 0;
            this.newRecordMinutes = 0;
        });
    }
    
    public void setUpTimer(){
        boolean wasItRunning = false;
        
        if(isRunning()){
            timeline.pause();
            wasItRunning = true;
        }
        
        KeyFrame keyframe1 = new KeyFrame(Duration.millis(1000 * this.tickTimeInSeconds), 
            (ActionEvent actionEvent) -> {
                this.secondsElapsed += this.tickTimeInSeconds;
                //calculates rotation
                this.handImageView.setRotate(this.secondsElapsed * this.angleDeltaPerSeconds);
                
                int temp = 1;
                
                if(this.input <= this.secondsElapsed){
                    this.timerLabel.setText("Timer: 00:00");
                }
                else{
                    this.timerLabel.setText("Timer:  " + myFormat.format(this.input - this.secondsElapsed));
                }
            });
        
        KeyFrame keyframe2 = new KeyFrame(Duration.millis(10 * this.tickTimeInSeconds), 
            (ActionEvent actionEvent) -> {
                this.centiSeconds++;
                this.newRecordCentiSeconds++;
                
                this.officialTimeLabel.setText(calculateTimes(this.centiSeconds, this.seconds, this.minutes));
                
                if(this.centiSeconds == 99){
                    this.centiSeconds = 0;
                    this.newRecordCentiSeconds = 0;
                    this.seconds++;
                    this.newRecordSeconds++;
                }
                
                if(this.seconds == 59){
                    this.seconds = 0;
                    this.newRecordSeconds = 0;
                    this.minutes++;
                    this.newRecordMinutes++;
                }
        });
        
        this.timeline = new Timeline(keyframe1, keyframe2);
        this.timeline.setCycleCount(Animation.INDEFINITE);
        
        if(wasItRunning){
            timeline.play();
        }
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
    
    public void setTickTimeInSeconds(int tick){
        this.tickTimeInSeconds = tick;
        setUpTimer();
    }
    
    public boolean isRunning(){
        if(this.timeline != null){
            if(this.timeline.getStatus() == Animation.Status.RUNNING){
                return true;
            }
        }
        
        return false;
    }
    
    public BorderPane getRoot(){
        return this.root;
    }
    
    public double getWidth(){
        return this.width;
    }
    
    public double getHeight(){
        return this.height;
    }
    
    public double getInput(){
        return this.input;
    }
    
    public void validateInput(){
        int inputInt = 0;
        
        Alert error = new Alert(Alert.AlertType.ERROR);
        error.setHeaderText("Error");
        error.setContentText("Only positive integers are allowed");
        
        //validate input
        while(inputInt <= 0){
            Optional<String> input = this.startInput.showAndWait();

            if(isParseable(input.get())){
                inputInt = Integer.parseInt(input.get());
                if(inputInt > 0){
                     this.input = (double)inputInt;
                }
                else{
                    inputInt = 0;
                    error.showAndWait();
                }
            }
            else{
                error.showAndWait();
            }
        }
        
        this.timerLabel.setText("Timer:  " + myFormat.format(this.input));
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
}
