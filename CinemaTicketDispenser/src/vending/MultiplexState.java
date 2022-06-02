package vending;

import java.io.Serializable;
import java.util.ArrayList;

public class MultiplexState implements Serializable{
    
    private ArrayList<Theater> theaterList;
    private MultiplexState instance;
    
    private MultiplexState(){
        this.theaterList = new ArrayList<>();
    }
    
    public MultiplexState getInstance(){
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
    
}
