package vending;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;

public class MultiplexState implements Serializable{
    
    private ArrayList<Theater> theaterList;
    private MultiplexState instance;
    
    private MultiplexState() throws Exception{
        this.theaterList = loadMoviesAndSessions();
    }
    
    public MultiplexState getInstance() throws Exception{
        if(instance == null)
            instance = new MultiplexState();
        return instance;
    }
    
    public Theater getTheater(int i){
        return theaterList.get(i);
    }
    
    public int getNumberOfTheaters(){
        return theaterList.size();
    }
    
    public ArrayList<Theater> getTheaterList(){
        return theaterList;
    }
    
    public ArrayList<Theater> loadMoviesAndSessions() throws Exception{
        ArrayList<Theater> tList = new ArrayList<>();
        int numOfFiles = new File("files/").list().length;
        for(int i = 1; i <= (numOfFiles / 3); i++){
            String moviePath = "files/Movie" + i;
            String theaterPath = "files/Theater" + i;
            theaterList.add(new Theater(moviePath, theaterPath));
        }
        return tList;
    }
}
