/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mpjykygpacalculators21;

import java.io.IOException;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 *
 * @author Mikey Joyce
 * @reference https://stackoverflow.com/questions/6456219/java-checking-if-parseint-throws-exception
 * @reference https://stackoverflow.com/questions/32866937/how-to-check-if-textfield-is-empty
 */
public class MpjykyGPACalculatorS21 extends Application {
    
    @Override
    public void start(Stage primaryStage){
        //create text fields and text areas
        TextField course1Text = new TextField();
        TextField course2Text = new TextField();
        TextField course3Text = new TextField();
        TextField course4Text = new TextField();
        
        //set preferred widths for text field
        course1Text.setPrefWidth(350);
        course2Text.setPrefWidth(350);
        course3Text.setPrefWidth(350);
        course4Text.setPrefWidth(350);
        
        TextArea infoText = new TextArea();
        infoText.setPrefRowCount(3);
        infoText.setWrapText(true);
        infoText.setEditable(false);
        
        //create labels
        Label course1Label = new Label("Course 1: ");
        Label course2Label = new Label("Course 2: ");
        Label course3Label = new Label("Course 3: ");
        Label course4Label = new Label("Course 4: ");
        Label infoLabel = new Label("Information: ");
        
        //create buttons
        Button avgScoreButton = new Button("Calculate Average Score");
        Button gpaButton = new Button("Calculate GPA");
        Button alertButton = new Button("Alert");
        Button clearButton = new Button("Clear All");
        
        avgScoreButton.setMaxWidth(Double.MAX_VALUE);
        gpaButton.setMaxWidth(Double.MAX_VALUE);
        alertButton.setMaxWidth(Double.MAX_VALUE);
        clearButton.setMaxWidth(Double.MAX_VALUE);
        
        VBox myBox = new VBox(10);
        myBox.setAlignment(Pos.CENTER);
        myBox.getChildren().add(avgScoreButton);
        myBox.getChildren().add(gpaButton);
        myBox.getChildren().add(alertButton);
        myBox.getChildren().add(clearButton);
        
        GridPane myPane = new GridPane();
        myPane.setHgap(10);
        myPane.setVgap(10);
        myPane.setPadding(new Insets(15, 15, 15, 15));
        myPane.add(course1Label, 0, 0, 1, 1);
        myPane.add(course1Text, 1, 0, 1, 1);
        myPane.add(course2Label, 0, 1, 1, 1);
        myPane.add(course2Text, 1, 1, 1, 1);
        myPane.add(course3Label, 0, 2, 1, 1);
        myPane.add(course3Text, 1, 2, 1, 1);
        myPane.add(course4Label, 0, 3, 1, 1);
        myPane.add(course4Text, 1, 3, 1, 1);
        myPane.add(infoLabel, 0, 4, 2, 1);
        myPane.add(infoText, 0, 5, 2, 1);
        myPane.add(myBox, 0, 6, 2, 1);
        
        Scene myScene = new Scene(myPane, 500, 425);
        primaryStage.setTitle("GPA Calculator");
        primaryStage.setScene(myScene);
        primaryStage.show();
        
        //event listeners
        avgScoreButton.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event){
                int[] input = finalInput(course1Text.getText(), course2Text.getText(), course3Text.getText(), course4Text.getText());
                
                Alert error = new Alert(AlertType.ERROR);
                error.setHeaderText("Error");
                error.setContentText("Only integers between 0 and 100 are allowed");
                
                if(isInputValid(input, course1Text, course2Text, course3Text, course4Text)){
                    infoText.clear();
                    error.showAndWait();
                }
                else{
                    double average = (input[0] + input[1] + input[2] + input[3])/4;
                    String output = String.format("Your average score is:\n((%d+%d+%d+%d)/4) = %.2f", input[0], input[1], input[2], input[3], average);
                    infoText.setText(output);
                }
            }
        });
        
        gpaButton.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event){
                int[] input = finalInput(course1Text.getText(), course2Text.getText(), course3Text.getText(), course4Text.getText());
                
                Alert error = new Alert(AlertType.ERROR);
                error.setHeaderText("Error");
                error.setContentText("Only integers between 0 and 100 are allowed");
                
                if(isInputValid(input, course1Text, course2Text, course3Text, course4Text)){
                    infoText.clear();
                    error.showAndWait();
                }
                else{
                    double average = (input[0] + input[1] + input[2] + input[3])/4;
                    String output = "Your GPA is:\n";
                    
                    if(average<=100 && average>=87){
                        infoText.setText(output + "A");
                    }
                    else if(average<87 && average>=77){
                        infoText.setText(output + "B");
                    }
                    else if(average<77 && average>= 67){
                        infoText.setText(output + "C");
                    }
                    else if(average<67 && average>=60){
                        infoText.setText(output + "D");
                    }
                    else{
                        infoText.setText(output + "F");
                    }
                }
            }
        });
        
        alertButton.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event){
                Alert message = new Alert(AlertType.INFORMATION);
                message.setHeaderText("Alert");
                //start of reference from https://stackoverflow.com/questions/32866937/how-to-check-if-textfield-is-empty
                if(infoText.getText() == ""){
                    //end of reference from https://stackoverflow.com/questions/32866937/how-to-check-if-textfield-is-empty
                    message.setContentText("There is nothing to display.");
                }
                else{
                    message.setContentText(infoText.getText());
                }
                
                message.showAndWait();
            }
        });
        
        clearButton.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event){
                course1Text.clear();
                course2Text.clear();
                course3Text.clear();
                course4Text.clear();
                infoText.clear();
            }
        });
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
    public Boolean isInputValid(int[] input, TextField course1, TextField course2, TextField course3, TextField course4){
        Boolean inputFlag = false;
        
        if(!checkInputRange(input[0])){
            course1.clear();
            inputFlag = true;
        }
        if(!checkInputRange(input[1])){
            course2.clear();
            inputFlag = true;
        }
        if(!checkInputRange(input[2])){
            course3.clear();
            inputFlag = true;
        }
        if(!checkInputRange(input[3])){
            course4.clear();
            inputFlag = true;
        }
        
        return inputFlag;
    }
    
    public int[] finalInput(String course1, String course2, String course3, String course4){
        int[] input = new int[4];
        
        input[0] = parseInput(isParseable(course1), course1);
        input[1] = parseInput(isParseable(course2), course2);
        input[2] = parseInput(isParseable(course3), course3);
        input[3] = parseInput(isParseable(course4), course4);
        
        return input;
    }
    
    public Boolean checkInputRange(int input){
        if(input>=0 && input<=100){
            return true;
        }
        return false;
    }
    
    public int parseInput(Boolean parseable, String input){
        if(parseable){
            return Integer.parseInt(input);
        }
        else{
            return -1;
        }
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
