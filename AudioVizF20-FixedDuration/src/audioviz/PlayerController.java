/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package audioviz;

import java.io.File;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.animation.Interpolator;
import javafx.animation.RotateTransition;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;

/**
 * FXML Controller class
 *
 * @author Professor Wergeles 
 * 
 * Music: 
 * http://www.bensound.com/royalty-free-music
 * http://www.audiocheck.net/testtones_sinesweep20-20k.php
 * 
 * 
 * References: 
 * http://stackoverflow.com/questions/11994366/how-to-reference-primarystage
 * 
 * @author #2 Mikey Joyce
 * Mikey Joyce References:
 * @reference https://docs.oracle.com/javafx/2/api/javafx/animation/RotateTransition.html
 */
public class PlayerController implements Initializable {

    @FXML
    private VBox root;
    
    @FXML
    private AnchorPane vizPane;

    @FXML
    private MediaView mediaView;

    @FXML
    private Text filePathText;

    @FXML
    private Text lengthText;

    @FXML
    private Text currentText;

    @FXML
    private Text bandsText;

    @FXML
    private Text visualizerNameText;

    @FXML
    private Text errorText;
    
    @FXML
    private Text volumePercentage;

    @FXML
    private Menu visualizersMenu;

    @FXML
    private Menu bandsMenu;

    @FXML
    private Slider timeSlider;
    
    @FXML
    private Slider volumeSlider;
    
    @FXML
    private ImageView pausePlayImage;
    
    @FXML
    private ImageView openImage;
    
    @FXML
    private ImageView soundOnOff;
    
    @FXML
    private ImageView baseAudioImage;
    
    @FXML
    private ImageView restartImage;

    private Media media;
    private MediaPlayer mediaPlayer;

    private Integer numOfBands = 40;
    private final Double updateInterval = 0.05;

    private ArrayList<Visualizer> visualizers;
    private Visualizer currentVisualizer;
    private final Integer[] bandsList = {1, 2, 3, 4, 5, 6, 8, 9, 15, 18, 20, 24, 30, 36, 40, 45, 60, 72, 90, 120, 180, 360};

    private int currentStatus = 0;
    private Integer song_duration;
    
    private final String pauseFile = "MpjykyPause.png";
    private final String playFile = "MpjykyPlay.png";
    private final String soundOnFile = "MpjykySoundOn.png";
    private final String soundOffFile = "MpjykySoundOff.png";
    private final String darkMode = "player.css";
    
    private File currentFile;
    
    private boolean soundFlag;
    private double lastSoundSetting;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        root.getStylesheets().add(this.getClass().getResource(darkMode).toExternalForm());
        numOfBands = 60;
        bandsText.setText(Integer.toString(numOfBands));

        visualizers = new ArrayList<>();
        visualizers.add(new EllipseVisualizer1());
        visualizers.add(new EllipseVisualizer2());
        visualizers.add(new EllipseVisualizer3());
        visualizers.add(new MpjykyCircleRickS21());
        
        for (Visualizer visualizer : visualizers) {
            MenuItem menuItem = new MenuItem(visualizer.getVizName());
            menuItem.setUserData(visualizer);
            menuItem.setOnAction((ActionEvent event) -> {
                selectVisualizer(event);
            });
            visualizersMenu.getItems().add(menuItem);
        }
        
        currentVisualizer = visualizers.get(0);
        visualizerNameText.setText(currentVisualizer.getVizName());

        for (Integer bands : bandsList) {
            MenuItem menuItem = new MenuItem(Integer.toString(bands));
            menuItem.setUserData(bands);
            menuItem.setOnAction((ActionEvent event) -> {
                selectBands(event);
            });
            bandsMenu.getItems().add(menuItem);
        }
        
        soundOnOff.setImage(new Image(getClass().getResourceAsStream(soundOffFile)));
        soundFlag = true;
    }

    private void selectVisualizer(ActionEvent event) {
        MenuItem menuItem = (MenuItem) event.getSource();
        Visualizer visualizer = (Visualizer) menuItem.getUserData();
        changeVisualizer(visualizer);
    }

    private void selectBands(ActionEvent event) {
        MenuItem menuItem = (MenuItem) event.getSource();
        numOfBands = (Integer) menuItem.getUserData();
        if (currentVisualizer != null) {
            currentVisualizer.setup(numOfBands, vizPane, root);
        }
        if (mediaPlayer != null) {
            mediaPlayer.setAudioSpectrumNumBands(numOfBands);
        }
        bandsText.setText(Integer.toString(numOfBands));
    }

    private void changeVisualizer(Visualizer visualizer) {
        if (currentVisualizer != null) {
            currentVisualizer.destroy();
        }
        currentVisualizer = visualizer;
        currentVisualizer.setup(numOfBands, vizPane, root);
        visualizerNameText.setText(currentVisualizer.getVizName());
    }

    private void openMedia(File file) {
        filePathText.setText("");
        errorText.setText("");

        if (mediaPlayer != null) {
            mediaPlayer.dispose();
        }

        try {
            media = new Media(file.toURI().toString());
            mediaPlayer = new MediaPlayer(media);
            mediaView.setMediaPlayer(mediaPlayer);
            
            mediaPlayer.setOnReady(() -> {
                handleReady();
            });
            
            mediaPlayer.setOnEndOfMedia(() -> {
                handleEndOfMedia();
            });
            
            mediaPlayer.setAudioSpectrumNumBands(numOfBands);
            mediaPlayer.setAudioSpectrumInterval(updateInterval);
            
            mediaPlayer.setAudioSpectrumListener((double timestamp, double duration, float[] magnitudes, float[] phases) -> {
                handleVisualize(timestamp, duration, magnitudes, phases);
            });
            
            filePathText.setText(file.getPath());
            currentStatus = 0;
            pausePlayImage.setImage(new Image(getClass().getResourceAsStream(playFile)));
            
            // new lines of code 
            AudioFile audioFile = AudioFileIO.read(new File(file.getPath()));
            song_duration = audioFile.getAudioHeader().getTrackLength() * 1000;
            
        } catch (Exception ex) {
            errorText.setText(ex.toString());
        }
    }

    private void handleReady() {
        int minutes = (int)((song_duration/60000));
        double seconds = (song_duration - (minutes*60000))/1000;
        lengthText.setText(getTime(minutes, seconds));
        Duration ct = mediaPlayer.getCurrentTime();
        currentText.setText("00:00 s");
        currentVisualizer.setup(numOfBands, vizPane, root);
        timeSlider.setMin(0);
        timeSlider.setMax(song_duration);
        
        volumeSlider.setMin(0);
        volumeSlider.setMax(1);
        volumeSlider.setValue(0);
        if(volumeSlider.getValue() == 0){
            soundOnOff.setImage(new Image(getClass().getResourceAsStream(soundOffFile)));
            soundFlag = false;
        }
        else{
            soundOnOff.setImage(new Image(getClass().getResourceAsStream(soundOnFile)));
            soundFlag = true;
        }
        
        updateVolumePercentage();
    }

    private void handleEndOfMedia() {
        mediaPlayer.stop();
        mediaPlayer.seek(Duration.ZERO);
        timeSlider.setValue(0);
        
        volumeSlider.setValue(0);
        updateVolumePercentage();
        soundOnOff.setImage(new Image(getClass().getResourceAsStream(soundOffFile)));
        soundFlag = false;
        
        currentText.setText("00:00 s");
    }

    private String getTime(int minutes, double seconds){
        String myText = "";
        
        if(minutes < 10){
            myText += "0" + minutes + ":";
        }
        else{
            myText += minutes + ":";
        }
        
        if((int)seconds < 10){
            myText += "0" + (int)seconds + " s";
        }
        else{
            myText += (int)seconds + " s";
        }
        
        return myText;
    }
    
    private void handleVisualize(double timestamp, double duration, float[] magnitudes, float[] phases) {
        Duration ct = mediaPlayer.getCurrentTime();
        double totalTime = ct.toMillis();
        int minutes = (int)((totalTime/60000));
        double seconds = ((totalTime - (minutes*60000))/1000)%60;
        
        String myText = getTime(minutes, seconds);
        
        currentText.setText(myText);
        timeSlider.setValue(totalTime);

        currentVisualizer.visualize(timestamp, duration, magnitudes, phases);
    }

    @FXML
    private void handleOpen(Event event) {
        //Start of reference from https://docs.oracle.com/javafx/2/api/javafx/animation/RotateTransition.html
        RotateTransition rt = new RotateTransition(Duration.millis(500), openImage);
        rt.setInterpolator(Interpolator.LINEAR);
        rt.setByAngle(45);
        rt.setCycleCount(1);
        rt.play();
        //End of reference from https://docs.oracle.com/javafx/2/api/javafx/animation/RotateTransition.html
         
        Stage primaryStage = (Stage) vizPane.getScene().getWindow();
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(primaryStage);
        currentFile = file;
        
        if (file != null) {
            openMedia(file);
        }
        
        rt.setCycleCount(1);
        //Start of reference from https://docs.oracle.com/javafx/2/api/javafx/animation/RotateTransition.html
        rt.setByAngle(-45);
        rt.play();
        //End of reference from https://docs.oracle.com/javafx/2/api/javafx/animation/RotateTransition.html
    }

    @FXML
    private void handlePlayPause(Event event) {
        if (mediaPlayer != null) {
            if (currentStatus == 0) {
                currentStatus = 1;
                mediaPlayer.play();
                volumeSlider.setValue(mediaPlayer.getVolume());
                updateVolumePercentage();
                pausePlayImage.setImage(new Image(getClass().getResourceAsStream(pauseFile)));
                soundOnOff.setImage(new Image(getClass().getResourceAsStream(soundOnFile)));
                soundFlag = true;
            } else {
                currentStatus = 0;
                mediaPlayer.pause();
                pausePlayImage.setImage(new Image(getClass().getResourceAsStream(playFile)));
            }
        }
    }
    
    @FXML
    private void handleSliderMousePressed(Event event) {
        if (mediaPlayer != null) {
            mediaPlayer.pause();
            handlePlayPause(event);
        }
    }

    @FXML
    private void handleSliderMouseReleased(Event event) {
        if (mediaPlayer != null) {
            mediaPlayer.seek(new Duration(timeSlider.getValue()));
            currentVisualizer.setup(numOfBands, vizPane, root);
            mediaPlayer.play();
            handlePlayPause(event);
        }
    }

    @FXML
    private void handleVolumeSliderMouseReleased(MouseEvent event) {
        if (mediaPlayer != null) {
            mediaPlayer.setVolume(volumeSlider.getValue());
            updateVolumePercentage();
            if(soundFlag == false){
                soundOnOff.setImage(new Image(getClass().getResourceAsStream(soundOnFile)));
                soundFlag = true;
            }
            else if(mediaPlayer.getVolume() == 0 && soundFlag == true){
                soundOnOff.setImage(new Image(getClass().getResourceAsStream(soundOffFile)));
                soundFlag = true;
            }
        }
    }
    
    @FXML
    private void handleRestart(Event event){
        if (currentFile != null) {
            //Start of reference from https://docs.oracle.com/javafx/2/api/javafx/animation/RotateTransition.html
            RotateTransition rt = new RotateTransition(Duration.millis(750), restartImage);
            rt.setInterpolator(Interpolator.LINEAR);
            rt.setByAngle(360);
            rt.setCycleCount(1);
            rt.play();
            //End of reference from https://docs.oracle.com/javafx/2/api/javafx/animation/RotateTransition.html
            
            openMedia(currentFile);
            timeSlider.setValue(0);
        }
    }
    
    @FXML
    private void handleMute(Event event){
        if(soundFlag == true){
            lastSoundSetting = volumeSlider.getValue();
            volumeSlider.setValue(0);
            updateVolumePercentage();
            mediaPlayer.setVolume(0);
            soundOnOff.setImage(new Image(getClass().getResourceAsStream(soundOffFile)));
            soundFlag = false;
            return;
        }
        
        if(lastSoundSetting != 0){
            volumeSlider.setValue(lastSoundSetting);
            updateVolumePercentage();
            mediaPlayer.setVolume(lastSoundSetting);
            soundOnOff.setImage(new Image(getClass().getResourceAsStream(soundOnFile)));
            soundFlag = true;
        }
    }
    
    private void updateVolumePercentage(){
        DecimalFormat df = new DecimalFormat("#.##"); 
        double currentPercentage = volumeSlider.getValue() * 100;
        currentPercentage = Double.valueOf(df.format(currentPercentage));
        volumePercentage.setText(currentPercentage + "%");
    }
}
