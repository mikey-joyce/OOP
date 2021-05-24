/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mpjykyhelloworlds21;

import java.util.*;
import java.text.*;

/**
 *
 * @author mikeyJoyce
 * @reference https://beginnersbook.com/2013/05/current-date-time-in-java/
 * @reference https://www.baeldung.com/java-get-day-of-week
 */
public class MpjykyHelloWorldS21 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        System.out.println("Hellow World!");
        int myCourseNum=3330;
        invokeMe(myCourseNum);
    }
    
    public static void invokeMe(int num){
        System.out.println("My course number is " + num);

        // start of reference from https://beginnersbook.com/2013/05/current-date-time-in-java/
        DateFormat myFormat = new SimpleDateFormat("MM/dd/yyyy HH:mma");
        Date currentDate = new Date();
        System.out.println("Today's date is " + myFormat.format(currentDate));
        // end of reference from https://beginnersbook.com/2013/05/current-date-time-in-java/
        
        //start of reference from https://www.baeldung.com/java-get-day-of-week
        Locale myArea = new Locale("en", "US");
        DateFormat dayOfWeek = new SimpleDateFormat("EEEE", myArea);
        System.out.println("Today is: " + dayOfWeek.format(currentDate));
        // end of reference from https://www.baeldung.com/java-get-day-of-week
    }
}
