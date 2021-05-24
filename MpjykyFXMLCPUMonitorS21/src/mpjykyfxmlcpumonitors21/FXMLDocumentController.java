/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mpjykyfxmlcpumonitors21;

import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicInteger;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 *
 * @author Mikey Joyce
 * @reference CHALLENGE SHEET PROF WERGELES GAVE US
 * @reference https://stackoverflow.com/questions/27921491/how-to-lock-the-divider-in-splitpane-javafx
 */
public class FXMLDocumentController implements Initializable {
    
    //Start of rotation: 235 degrees
    //End of rotation: 125/485 degrees
    
    @FXML
    private SplitPane main;
    
    @FXML
    private AnchorPane left;
    
    @FXML
    private AnchorPane right;
    
    @FXML 
    private StackPane gauge;
    
    @FXML
    private ImageView dialImageView;
    
    @FXML
    private ImageView handImageView;
    
    @FXML
    private Button startStopButton;
    
    @FXML
    private Button recordButton;
    
    @FXML
    private LineChart<String, Number> recordChart;
    
    @FXML
    private AreaChart<String, Number> cpuLoadChart;
    
    @FXML
    private CategoryAxis recordXAxis;
    
    @FXML
    private CategoryAxis cpuXAxis;

    @FXML
    private NumberAxis recordYAxis;
    
    @FXML
    private NumberAxis cpuYAxis;
    
    @FXML
    private HBox peakHBox;
    
    @FXML
    private HBox meanHBox;
    
    @FXML
    private StackPane peakPane;
    
    @FXML
    private StackPane meanPane;
    
    @FXML
    private StackPane gaugePane;
    
    @FXML
    private Label peakLabel;
    
    @FXML
    private Label meanLabel;
    
    DecimalFormat myFormat = new DecimalFormat("#00.00");
    
    XYChart.Series<String, Number> recordSeries = new XYChart.Series<>();
    XYChart.Series<String, Number> cpuSeries = new XYChart.Series<>();
    private AtomicInteger tick1 = new AtomicInteger(0);
    private AtomicInteger tick2 = new AtomicInteger(0);
    
    Timeline timeline;
    
    ArrayList<Double> meanData = new ArrayList<Double>();
    
    String defaultText = "00.00%";
    Text peakText = new Text(defaultText);
    Text meanText = new Text(defaultText);
    Text gaugeText = new Text(defaultText);
    
    boolean startStopFlag = false;
    boolean graphFlag = false;
    boolean initialButtonLayout = true;
    
    double tickTimeInSeconds = 0.01;
    int secondsElapsed = 0;
    int keyframeLoopCounter = 0;
    
    double currentCPUUsage = 0;
    double meanCPUUsageValue = 0;
    double peakCPUUsage = 0;
    
    public void onOpen(){
        
        peakPane.getChildren().add(peakText);
        meanPane.getChildren().add(meanText);
        gaugePane.getChildren().add(gaugeText);


        peakText.setFont(new Font("System", (double) 20.0));
        meanText.setFont(new Font("System", (double) 20.0));
        gaugeText.setFont(new Font("System", (double) 30.0));

        StackPane.setAlignment(peakText, Pos.CENTER);
        StackPane.setAlignment(meanText, Pos.CENTER);
        StackPane.setAlignment(gaugeText, Pos.TOP_CENTER);

        //start of reference from https://stackoverflow.com/questions/27921491/how-to-lock-the-divider-in-splitpane-javafx
        left.maxWidthProperty().bind(main.widthProperty().multiply(0.47));
        right.maxWidthProperty().bind(main.widthProperty().multiply(0.47));
        //end of reference from https://stackoverflow.com/questions/27921491/how-to-lock-the-divider-in-splitpane-javafx

        recordXAxis.setAnimated(false);

        cpuXAxis.setAnimated(false);

        recordYAxis.setAutoRanging(false);
        recordYAxis.setAnimated(false);
        recordYAxis.setLabel("CPU Usage (%)");
        recordYAxis.setUpperBound(100);
        recordYAxis.setLowerBound(0);

        cpuYAxis.setAutoRanging(false);
        cpuYAxis.setAnimated(false);
        cpuYAxis.setLabel("CPU Usage (%)");
        cpuYAxis.setUpperBound(100);
        cpuYAxis.setLowerBound(0);

        recordSeries.setName("History of Recorded CPU");
        cpuSeries.setName("History of CPU Usage");
        
    }
    
    public void startStopButtonAction(){
        if(!startStopFlag){
            startStopButton.setText("Stop");
            recordButton.setText("Record");
            startStopFlag = true;
            animateGauge();
            timeline.play();
            initialButtonLayout = false;
        }
        else{
            startStopButton.setText("Start");
            recordButton.setText("Reset");
            startStopFlag = false;
            timeline.pause();
        }
    }
    
    public void recordButtonAction() throws InterruptedException{
        if(startStopFlag && initialButtonLayout == false){
            recordSeries.getData().add(new XYChart.Data<>(String.valueOf(tick1.incrementAndGet()), getMeanDataNode(1)));
        }
        else if(!startStopFlag && initialButtonLayout == false){
            recordSeries.getData().clear();
            cpuSeries.getData().clear();
            peakText.setText(defaultText);
            meanText.setText(defaultText);
            gaugeText.setText(defaultText);
            meanCPUUsageValue = 0.0;
            peakCPUUsage = 0.0;
            currentCPUUsage = 0.0;
            tick1 = new AtomicInteger(0);
            tick2 = new AtomicInteger(0);
            handImageView.setRotate(235);
        }
    }
    
    public void animateGauge(){
        boolean wasItRunning = false;
        
        if(isRunning()){
            timeline.pause();
            wasItRunning = true;
        }
        
        KeyFrame keyframe1 = new KeyFrame(Duration.millis(1000), (ActionEvent actionEvent) -> {
            secondsElapsed += tickTimeInSeconds;
            
            if(keyframeLoopCounter == 2){
                keyframeLoopCounter = 0;
            }
            
            keyframeLoopCounter++;
            
            currentCPUUsage = getCPUUsage();
            
            if(isCPUValid(currentCPUUsage)){
                gaugeText.setText(String.valueOf(myFormat.format(currentCPUUsage * 100)) + "%");
                double rotation = (double)((485-235) * currentCPUUsage) + 235;
                handImageView.setRotate(rotation);
                
                if(currentCPUUsage > peakCPUUsage){
                    peakCPUUsage = currentCPUUsage;
                    peakText.setText(String.valueOf(myFormat.format(peakCPUUsage * 100)) + "%");
                }
                
                meanData.add(currentCPUUsage * 100);
                cpuSeries.getData().add(new XYChart.Data<>(String.valueOf(tick2.incrementAndGet()), getMean()));
                meanText.setText(String.valueOf(myFormat.format(getMean())) + "%");
            }
            else{
                gaugeText.setText("N/A");
            }
        });
        
        timeline = new Timeline(keyframe1);
        timeline.setCycleCount(Animation.INDEFINITE);
        
        if(wasItRunning){
            timeline.play();
        }
    }
    
    public void setUpGraph(){
        recordChart.setAnimated(false);
        recordChart.getData().add(recordSeries);

        cpuLoadChart.setAnimated(false);
        cpuLoadChart.getData().add(cpuSeries);
    }
    
    public boolean isCPUValid(Double value){
        return !(value == null || value < 0.0);
    }
    
    public double getMeanDataNode(int node){
        return meanData.get(meanData.size() - node);
    }
    
    public double getMean(){
        double total=0;
        
        for(int i = 0; i < meanData.size(); i++){
            total += meanData.get(i);
        }
        
        return total/(meanData.size());
    }
    
    //START OF REFERENCE FROM CHALLENGE SHEET
    public double getCPUUsage(){
        OperatingSystemMXBean operatingSystemMXBean = ManagementFactory.getOperatingSystemMXBean();
        double value = 0;
        for(Method method : operatingSystemMXBean.getClass().getDeclaredMethods()) {
            method.setAccessible(true);
            if (method.getName().startsWith("getSystemCpuLoad") && Modifier.isPublic(method.getModifiers())) { 
                try {
                    value = (double) method.invoke(operatingSystemMXBean); 
                } 
                catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
                    value = 0; 
                }
            
                return value;
            }
        }
    
        return value;
    }
    //END OF REFERENCE FROM CHALLENGE SHEET
    
    public boolean isRunning(){
        if(timeline != null){
            if(timeline.getStatus() == Animation.Status.RUNNING){
                return true;
            }
        }
        
        return false;
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        onOpen();
    }
}
