/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mpjykymvctimerstopwatchfxmls21;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.animation.FillTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.TranslateTransition;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author Mikey Joyce
 * @reference https://www.youtube.com/watch?v=maX5ymmQixM
 * @reference https://stackoverflow.com/questions/6456219/java-checking-if-parseint-throws-exception
 * @reference https://stackoverflow.com/questions/2808535/round-a-double-to-2-decimal-places
 * @reference https://stackoverflow.com/questions/49159286/make-a-dark-mode-with-javafx
 */
public class MpjykyMVCTimerStopwatchFXMLS21Controller implements Initializable, PropertyChangeListener{

    @FXML
    private VBox root;
    
    @FXML
    private StackPane gaugePane;
    
    @FXML
    private StackPane togglePane;
    
    @FXML
    private ImageView faceImageView;
    
    @FXML
    private ImageView dialImageView;
    
    @FXML
    private HBox buttonBox;
    
    @FXML
    private Button startButton;
    
    @FXML
    private Button recordButton;
    
    @FXML
    private StackPane timePane;
    
    @FXML
    private VBox dataBox;
    
    @FXML
    private StackPane timerCountDownPane;
    
    @FXML
    private StackPane lapPane;
    
    @FXML
    private StackPane avgLapPane;
    
    @FXML
    private Pane chartPane;
    
    @FXML
    private HBox recordBox;
    
    @FXML
    private HBox avgBox;
    
    @FXML
    private ImageView recordArrowImage;
    
    @FXML
    private ImageView avgArrowImage;
    
    @FXML
    private LineChart<String, Number> recordChart;
    
    @FXML
    private AreaChart<String, Number> avgChart;
    
    @FXML
    private CategoryAxis recordXAxis;
    
    @FXML
    private CategoryAxis avgXAxis;
    
    @FXML
    private NumberAxis recordYAxis;
    
    @FXML
    private NumberAxis avgYAxis;
    
    DigitalDisplay digitalModel;
    AnalogDisplay analogModel;
    
    private final String styleDark = "mpjykymvctimerstopwatchfxmls21.css";
    private final String styleLight = "lightmode.css";
    private final String lightClock = "clockface.png";
    private final String darkClock = "darkMode-clockface.png";
    private final String lightArrow = "lightModeArrow.png";
    private final String darkArrow = "darkModeArrow.png";
    
    private Text totalTimeText = new Text();
    private Text timerText = new Text();
    private Text lapText = new Text();
    private Text avgLapText = new Text();
    
    private TextInputDialog startInput;
    
    private boolean slideFlag;
    private boolean startStopFlag;
    private boolean initialButtonLayout;
    
    private ToggleSwitch toggle;
    Alert error;
    
    public void onOpen(){
        root.getStylesheets().add(this.getClass().getResource(styleLight).toExternalForm());
        
        error = new Alert(Alert.AlertType.INFORMATION);
        error.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
        slideFlag = true;
        startStopFlag = false;
        initialButtonLayout = true;
        
        toggle = new ToggleSwitch();
        togglePane.getChildren().add(toggle);
        
        //Keep this block in controller
        timePane.getChildren().add(totalTimeText);
        timerCountDownPane.getChildren().add(timerText);
        lapPane.getChildren().add(lapText);
        avgLapPane.getChildren().add(avgLapText);
        
        totalTimeText.setFont(digitalModel.getFont(true));
        timerText.setFont(digitalModel.getFont(false));
        lapText.setFont(digitalModel.getFont(false));
        avgLapText.setFont(digitalModel.getFont(false));
        
        totalTimeText.setText(digitalModel.getStartingString(0));
        //timerText.setText(digitalModel.getStartingString(1));
        lapText.setText(digitalModel.getStartingString(2));
        avgLapText.setText(digitalModel.getStartingString(3));
        
        error.setHeaderText("Time is up!");
        error.setContentText("No more records...");
        
        //Keep the rest of the code here
        recordChart.setAnimated(false);
        recordChart.getData().add(digitalModel.getSeries(true));

        avgChart.setAnimated(false);
        avgChart.getData().add(digitalModel.getSeries(false));
        
        recordXAxis.setAnimated(false);
        avgXAxis.setAnimated(false);
        
        recordYAxis.setAnimated(false);
        avgYAxis.setAnimated(false);
        recordYAxis.setLabel("Time (s)");
        avgYAxis.setLabel("Time (s)");
    }
    
    public void slide(){
        TranslateTransition slide1 = new TranslateTransition();
        TranslateTransition slide2 = new TranslateTransition();
        slide1.setDuration(Duration.seconds(1));
        slide2.setDuration(Duration.seconds(1));
        slide1.setNode(recordBox);
        slide2.setNode(avgBox);
        
        if(slideFlag){
            slide1.setToX(-600);
            slide2.setToX(0);
            slide1.play();
            slide2.play();
            slideFlag = false;
        }
        else{
            slide1.setToX(0);
            slide2.setToX(600);
            slide1.play();
            slide2.play();
            slideFlag = true;
        }
    }
    
    public void startButtonAction(){
        if(!startStopFlag){
            startButton.setText("Stop");
            recordButton.setText("Record");
            startStopFlag = true;
            initialButtonLayout = false;
            analogModel.calculateRotation();
            digitalModel.calculateTotalTime();
            analogModel.start();
            digitalModel.start();
        }
        else{
            startButton.setText("Start");
            recordButton.setText("Reset");
            startStopFlag = false;
            digitalModel.pauseTimeline();
            analogModel.pauseTimeline();
        }
    }
    
    public void recordButtonAction(){
        if(startStopFlag && initialButtonLayout == false){
            digitalModel.updateRecords();
        }
        else if(!startStopFlag && initialButtonLayout == false){
            digitalModel.reset();
            analogModel.reset();
        }
    }
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        TextInputDialog input = new TextInputDialog("");
        input.setHeaderText("Set up the start time:");
        input.setContentText("Enter a positive integer:");
        
        digitalModel = new DigitalDisplay(validateInput(input));
        analogModel = new AnalogDisplay();
        
        onOpen();
        
        digitalModel.addPropertyChangeListener(this);
        analogModel.addPropertyChangeListener(this);
        
        toggle.switchedOnProperty().addListener((obs, oldState, newState) -> {
            if(newState){
                root.getStylesheets().remove(this.getClass().getResource(styleLight).toExternalForm());
                root.getStylesheets().add(this.getClass().getResource(styleDark).toExternalForm());
                faceImageView.setImage(new Image(getClass().getResourceAsStream(darkClock)));
                recordArrowImage.setImage(new Image(getClass().getResourceAsStream(darkArrow)));
                avgArrowImage.setImage(new Image(getClass().getResourceAsStream(darkArrow)));
                totalTimeText.setFill(Color.WHITE);
                timerText.setFill(Color.WHITE);
                lapText.setFill(Color.WHITE);
                avgLapText.setFill(Color.WHITE);
            }
            else{
                root.getStylesheets().remove(this.getClass().getResource(styleDark).toExternalForm());
                root.getStylesheets().add(this.getClass().getResource(styleLight).toExternalForm());
                faceImageView.setImage(new Image(getClass().getResourceAsStream(lightClock)));
                recordArrowImage.setImage(new Image(getClass().getResourceAsStream(lightArrow)));
                avgArrowImage.setImage(new Image(getClass().getResourceAsStream(lightArrow)));
                totalTimeText.setFill(Color.BLACK);
                timerText.setFill(Color.BLACK);
                lapText.setFill(Color.BLACK);
                avgLapText.setFill(Color.BLACK);
            }
        });
    }
    
    /*OLD OR REFERENCED CODE BELOW*/
    
    public double validateInput(TextInputDialog startInput){
        int inputInt = 0;
        double returnVal = -1;
        DecimalFormat myFormat = new DecimalFormat("#00.00");
        
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText("Error");
        alert.setContentText("Only positive integers are allowed");
        alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
        
        //validate input
        while(inputInt <= 0){
            Optional<String> input = startInput.showAndWait();

            if(AbstractModel.isParseable(input.get())){
                inputInt = Integer.parseInt(input.get());
                if(inputInt > 0){
                     returnVal = (double)inputInt;
                }
                else{
                    inputInt = 0;
                    alert.showAndWait();
                }
            }
            else{
                alert.showAndWait();
            }
        }
        
        timerText.setText("Timer:  " + myFormat.format(returnVal));
        return returnVal;
    }
    
    //Start of reference from https://www.youtube.com/watch?v=maX5ymmQixM
    private static class ToggleSwitch extends Parent{
        
        private BooleanProperty switchedOn = new SimpleBooleanProperty(false);
        private TranslateTransition switchAnimation = new TranslateTransition(Duration.seconds(0.25));
        private FillTransition fillAnimation = new FillTransition(Duration.seconds(0.25));
        private ParallelTransition animation = new ParallelTransition(switchAnimation, fillAnimation);
        
        public BooleanProperty switchedOnProperty(){
            return switchedOn;
        }
        
        public ToggleSwitch(){
            Rectangle background = new Rectangle(100, 50);
            background.setArcWidth(50);
            background.setArcHeight(50);
            background.setFill(Color.LIGHTSALMON);
            background.setStroke(Color.LIGHTGRAY);
            
            Circle trigger = new Circle(25);
            trigger.setCenterX(25);
            trigger.setCenterY(25);
            trigger.setFill(Color.WHITE);
            trigger.setStroke(Color.LIGHTGRAY);
            
            switchAnimation.setNode(trigger);
            fillAnimation.setShape(background);
            
            getChildren().addAll(background, trigger);
            
            switchedOn.addListener((obs, oldState, newState) -> {
                boolean isOn = newState.booleanValue();
                switchAnimation.setToX(isOn ? 100 - 50 : 0);
                fillAnimation.setFromValue(isOn ? Color.LIGHTSALMON : Color.LIGHTGREEN);
                fillAnimation.setToValue(isOn ? Color.LIGHTGREEN : Color.LIGHTSALMON);
                animation.playFromStart();
            });

            setOnMouseClicked(event -> {
               switchedOn.set(!switchedOn.get()); 
            });
        }
    }
    //End of reference from https://www.youtube.com/watch?v=maX5ymmQixM
    
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if(evt.getPropertyName().equals("TotalTime")){
            totalTimeText.setText(evt.getNewValue().toString());
        }
        else if(evt.getPropertyName().equals("LapTime")){
            lapText.setText(evt.getNewValue().toString());
        }
        else if(evt.getPropertyName().equals("TimerText")){
            timerText.setText(evt.getNewValue().toString());
        }
        else if(evt.getPropertyName().equals("AvgLapTime")){
            avgLapText.setText(evt.getNewValue().toString());
        }
        else if(evt.getPropertyName().equals("SetRotate")){
            dialImageView.setRotate(Double.valueOf(evt.getNewValue().toString()));
        }
        else if(evt.getPropertyName().equals("Error")){
            error.showAndWait();
        }
    }
}
