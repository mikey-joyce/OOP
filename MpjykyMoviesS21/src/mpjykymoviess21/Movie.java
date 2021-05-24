/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mpjykymoviess21;

import java.text.*;
import java.util.*;

/**
 *
 * @author mikeyJoyce
 * @reference https://stackoverflow.com/questions/22326339/how-create-date-object-with-values-in-java/22326675
 * @reference https://www.tutorialspoint.com/how-to-check-if-a-given-character-is-a-number-letter-in-java
 * @reference https://www.callicoder.com/how-to-add-subtract-days-hours-minutes-seconds-to-date-java/
 */
public class Movie {
    //fields
    private String name;
    private String director;
    private String summary;
    private float rating = 0.0f;
    private double revenue = 0.0;
    private Genre genre;
    //start of reference from https://stackoverflow.com/questions/22326339/how-create-date-object-with-values-in-java/22326675
    DateFormat myFormat = new SimpleDateFormat("MMM dd, yyyy");
    private Date releaseDate = new GregorianCalendar(1888, Calendar.OCTOBER, 14).getTime();
    //end of reference from https://stackoverflow.com/questions/22326339/how-create-date-object-with-values-in-java/22326675
    private String runtime;
    private int version;
    public static int numOfMovies = 0;
    
    
    
    //start of reference from https://stackoverflow.com/questions/22326339/how-create-date-object-with-values-in-java/22326675
    public static Date parseDate(String date) {
        try {
            return new SimpleDateFormat("MMM dd, yyyy").parse(date);
        } catch (ParseException e) {
            return null;
        }
    }
    //end of reference from https://stackoverflow.com/questions/22326339/how-create-date-object-with-values-in-java/22326675
    
    public Movie(){
        this.name = "";
        this.director = "";
        this.version = 0;
        this.numOfMovies++;
    }
    
    public Movie(String name, String director, String runtime){
        this();
        this.name = name;
        this.director = director;
        this.runtime = runtime;
    }
    
    public Movie(String name, String director, String summary, Genre genre, Date releaseDate, String runtime){
        this(name, director, runtime);
        this.summary = summary;
        this.genre = genre;
        this.releaseDate = releaseDate;
        this.version = 1;
    }
    
    public Movie(String name, String director, String summary, float rating, double revenue, Genre genre, Date releaseDate, String runtime){
        this(name, director, summary, genre, releaseDate, runtime);
        this.rating = rating;
        this.revenue = revenue;
    }
    
    public void incrementVersion(){
        this.version++;
    }
    
    public void setName(String name){
        this.name = name;
        incrementVersion();
    }
    
    public void setDirector(String director){
        this.director = director;
    }
    
    public void setRating(float rating){
        this.rating = rating;
        incrementVersion();
    }
    
    public void setRevenue(double revenue){
        this.revenue = revenue;
        incrementVersion();
    }
    
    public void setReleaseDate(Date releaseDate){
        this.releaseDate = releaseDate;
        incrementVersion();
    }
    
    public void setGenre(Genre genre){
        this.genre = genre;
    }
    
    public void setSummary(String summary){
        this.summary = summary;
        incrementVersion();
    }
    
    public void setRuntime(String runtime){
        this.runtime = runtime;
    }
    
    public String getName(){
        return this.name;
    }
    
    public String getDirector(){
        return this.director;
    }
    
    public float getRating(){
        return this.rating;
    }
    
    public double getRevenue(){
        return this.revenue;
    }
    
    public Date getReleaseDate(){
        return this.releaseDate;
    }
    
    public Genre getGenre(){
        return this.genre;
    }
    
    public String getSummary(){
        return this.summary;
    }
    
    public int getVersion(){
        return this.version;
    }
    
    public String getRuntime(){
        return this.runtime;
    }
    
    public void playMovie(){
        System.out.println("The runtime of " + this.name + " is " + this.runtime);
        calculateEndTime();
    }
    
    public void print(){
        System.out.println("Name: " + this.getName());
        System.out.println("Director: " + this.getDirector());
        System.out.println("Summary: " + this.getSummary());
        System.out.println("Genre: " + this.getGenre());
        System.out.println("Rating: " + this.getRating());
        printRevenue();
        printReleaseDate();
        System.out.println("Runtime: " + this.getRuntime());
        System.out.println("Version: " + this.getVersion());
        this.playMovie();
    }
    
    public void calculateEndTime(){
        String tempHour="";
        String tempMinute="";
        int flag=0;
        //parse the runtime string into separate strings with only numbers
        // start of reference from https://www.tutorialspoint.com/how-to-check-if-a-given-character-is-a-number-letter-in-java
        for(int i=0; i<this.runtime.length(); i++){
            if(Character.isDigit(this.runtime.charAt(i)) && flag==0){
                tempHour += this.runtime.charAt(i);
            } else if(Character.isDigit(this.runtime.charAt(i)) && flag!=0){
                tempMinute += this.runtime.charAt(i);
            } else{
                flag++;
            }
        }
        //end of reference from https://www.tutorialspoint.com/how-to-check-if-a-given-character-is-a-number-letter-in-java
        
        //parse the strings into ints
        int hour = Integer.parseInt(tempHour);
        int minute = Integer.parseInt(tempMinute);
        
        //get current date and time
        //start of reference from https://www.callicoder.com/how-to-add-subtract-days-hours-minutes-seconds-to-date-java/
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy h:mm a");
        Date current = new Date();
        
        Calendar myCal = Calendar.getInstance();
        myCal.setTime(current);
        
        myCal.add(Calendar.HOUR, hour);
        myCal.add(Calendar.MINUTE, minute);
        
        Date endTime = myCal.getTime();
        
        System.out.println(this.name + " will end at " + dateFormat.format(endTime));
        //end of reference from https://www.callicoder.com/how-to-add-subtract-days-hours-minutes-seconds-to-date-java/
    }
    
    public void printRevenue(){
        double revenue = getRevenue();
        System.out.println(String.format("Revenue: %,.2f", revenue));
    }
    
    public void printReleaseDate(){
        System.out.println("Release Date: " + myFormat.format(this.releaseDate));
    }
}