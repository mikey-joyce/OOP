/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mpjykymoviess21;

import java.util.Date;
import static mpjykymoviess21.Movie.parseDate;

/**
 *
 * @author mikeyJoyce
 */
public class MpjykyMoviesS21 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Movie movie1 = new Movie("Soul", "Pete Docter and Kemp Powers", "1h40m");
        movie1.setGenre(Genre.ANIMATION);
        movie1.setSummary("After landing the gig of a lifetime, a New York jazz pianist suddenly finds himself trapped in a strange land between Earth and the afterlife.");
        movie1.setRating((float) 8.1);
        movie1.setRevenue(82700000.00);
        Date tempReleaseDate = parseDate("Dec 25, 2020");
        movie1.setReleaseDate(tempReleaseDate);
        
        tempReleaseDate = parseDate("Jun 21, 2017");
        Movie movie2 = new Movie("Transformers: The Last Knight", "Michael Bay", "A deadly threat from Earth's history reappears and a hunt for a lost artifact takes place between Autobots and Decepticons, while Optimus Prime encounters his creator in space.", (float)5.2, 602800000.00, Genre.ACTION, tempReleaseDate, "2h34m");
        
        tempReleaseDate = parseDate("Jul 6, 1994");
        Movie movie3 = new Movie("Forrest Gump", "Robert Zemeckis", "The presidencies of Kennedy and Johnson, the events of Vietnam, Watergate and other historical events unfold through the perspective of an Alabama man with an IQ of 75, whose only desire is to be reunited with his childhood sweetheart.", Genre.DRAMA, tempReleaseDate, "2h22m");
        movie3.setRating((float)8.8);
        movie3.setRevenue(679800000.00);
        
        Movie movie4 = new Movie();
        movie4.setName("The Godfather");
        movie4.setDirector("Francis Ford Coppola");
        movie4.setSummary("An organized crime dynasty's aging patriarch transfers control of his clandestine empire to his reluctant son. ");
        movie4.setGenre(Genre.DRAMA);
        movie4.setRating((float)9.2);
        movie4.setRevenue(287258196.00);
        tempReleaseDate = parseDate("Mar 24, 1972");
        movie4.setReleaseDate(tempReleaseDate);
        movie4.setRuntime("2h55m");
        
        System.out.println("Movie 1:");
        System.out.println("Name: " + movie1.getName());
        System.out.println("Director: " + movie1.getDirector());
        System.out.println("Summary: " + movie1.getSummary());
        System.out.println("Genre: " + movie1.getGenre());
        System.out.println("Rating: " + movie1.getRating());
        movie1.printRevenue();
        movie1.printReleaseDate();
        System.out.println("Runtime: " + movie1.getRuntime());
        System.out.println("Version: " + movie1.getVersion());
        movie1.playMovie();
        System.out.println();
        
        System.out.println("Movie 2:");
        System.out.println("Name: " + movie2.getName());
        System.out.println("Director: " + movie2.getDirector());
        System.out.println("Summary: " + movie2.getSummary());
        System.out.println("Genre: " + movie2.getGenre());
        System.out.println("Rating: " + movie2.getRating());
        movie2.printRevenue();
        movie2.printReleaseDate();
        System.out.println("Runtime: " + movie2.getRuntime());
        System.out.println("Version: " + movie2.getVersion());
        movie2.playMovie();
        System.out.println();
        
        System.out.println("Movie 3: ");
        movie3.print();
        System.out.println();
        
        System.out.println("Movie 4: ");
        movie4.print();
        System.out.println();
        
        System.out.println("Number of Movies: " + Movie.numOfMovies);
    }

}
