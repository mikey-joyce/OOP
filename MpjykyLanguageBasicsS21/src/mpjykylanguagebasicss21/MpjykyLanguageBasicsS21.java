/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mpjykylanguagebasicss21;

import java.text.*;
import java.time.*;
import java.util.*;

/**
 *
 * @author mikeyJoyce
 * @reference https://stackoverflow.com/questions/2654025/how-to-get-year-month-day-hours-minutes-seconds-and-milliseconds-of-the-cur
 * @reference https://stackoverflow.com/questions/3680637/generate-a-random-double-in-a-range
 * @reference https://beginnersbook.com/2013/05/current-date-time-in-java/
 */
public class MpjykyLanguageBasicsS21 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        char c1 = 'c';
        char c2 = (char)99;
        short qualityScore = 61;
        float miles = (float)150;
        float milesPerGallon = (float)27.5;
        float gasPrice = (float)2.34;
        boolean sunny = false;
        boolean warm = false;
        
        //start of reference from https://stackoverflow.com/questions/2654025/how-to-get-year-month-day-hours-minutes-seconds-and-milliseconds-of-the-cur
        LocalDateTime now = LocalDateTime.now();
        int hour = now.getHour();
        //end of reference from https://stackoverflow.com/questions/2654025/how-to-get-year-month-day-hours-minutes-seconds-and-milliseconds-of-the-cur
        
        //start of reference from https://stackoverflow.com/questions/3680637/generate-a-random-double-in-a-range
        Random random = new Random();
        double grade = 4.0 * random.nextDouble();
        //end of reference from https://stackoverflow.com/questions/3680637/generate-a-random-double-in-a-range
        
        String greeting = "Hi";
        String myPawPrint = "mpjyky";
        
        if(c1 == c2){
            System.out.println(c1 + " and " + c2 + " are the same.");
        }
        else{
            System.out.println(c1 + " and " + c2 + " are NOT the same");
        }
        
        if(qualityScore >= 0 && qualityScore <= 60){
            System.out.println("The quality is bad.");
        }
        else{
            System.out.println("Good quality.");
        }
        
        float gasFee = (miles/milesPerGallon) * gasPrice;
        System.out.printf("Total gas fee = %.3f\n", gasFee);
        
        if(sunny == true && warm == true){
            System.out.println("Go swimming.");
        }
        else if(sunny == false && warm == true){
            System.out.println("Go hiking.");
        }
        else{
            System.out.println("Stay home and code.");
        }
        
        switch(hour){
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
                System.out.println("The current time is " + hour + " in the MORNING.");
                break;
            case 11:
            case 12:
            case 13:
            case 14:
            case 15:
            case 16:
                System.out.println("The current time is " + hour + " in the AFTERNOON.");
                break;
            case 17:
            case 18:
            case 19:
            case 20:
            case 21:
            case 22:
                System.out.println("The current time is " + hour + " in the EVENING.");
                break;
            case 23:
            case 0:
            case 1:
            case 2:
            case 3:
            case 4:
                System.out.println("The current time is " + hour + " in the NIGHT.");
                break;
            default:
                System.out.println("You have the wrong time.");
                break;
        }
        
        if(grade >= 0.00 && grade < 0.70){
            System.out.println("The student's GPA grade is an F in the class.");
        }
        else if(grade >= 0.70 && grade < 1.70){
            System.out.println("The student's GPA grade is a D- to D+ in the class.");
        }
        else if(grade >= 1.70 && grade < 2.70){
            System.out.println("The student's GPA grade is a C- to a C+ in the class.");
        }
        else if(grade >= 2.70 && grade < 3.70){
            System.out.println("The student's GPA grade is a B- to a B+ in the class.");
        }
        else if(grade >= 3.70){
            System.out.println("The student's GPA grade is a A- to a A+ in the class.");
        }
        
        for(int i=2; i<11 ; i++){
            if(i%3==0){
                System.out.println("Count: " + i);
            }
        }
        
        int countDown = 10;
        while(countDown > 0){
            System.out.println("Count down: " + countDown);
            countDown--;
        }
        System.out.println("Houston, we have a lift off!");
        
        invokeMe(greeting, myPawPrint);
    }
    
    public static void invokeMe(String greeting, String pawPrint){
        // start of reference from https://beginnersbook.com/2013/05/current-date-time-in-java/
        DateFormat myFormat = new SimpleDateFormat("MM/dd/yyyy hh:mma");
        Date currentDate = new Date();
        System.out.println(greeting + ", my pawprint is " + pawPrint + " and today's date is " + myFormat.format(currentDate));
        // end of reference from https://beginnersbook.com/2013/05/current-date-time-in-java/
    }
    
}
