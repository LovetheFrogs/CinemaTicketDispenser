package vending;

import java.util.ArrayList;
import java.util.HashSet;

public class Theater implements Serializable{
    
    private int number;
    private int price;
    private HashSet<Seat> seatSet;
    private ArrayList<Session> sessions;
    private Film filmList;
    
    public Theater(int number, int price, Film filmList){
        this.number = number;
        this.price = price;
        this.seatSet = new HashSet<>();
        this.filmList = filmList;
    }
    
    public int getNumber(){
        return number;
    }
    
    public int getPrice(){
        return price;
    }
    
    public void addSession(Session session){
        this.sessions.add(session);
    }
    
    public Film getFilm(){
        return filmList;
    }
    
    public ArrayList<Session> getSessionList(){
        return sessions;
    }
    
    public HashSet<Seat> getSeatSet(){
        return seatSet;
    }
    
    public int getMaxRows(){
        int max = 0;
        for(Seat element: seatSet){
            if(element.getRow() > max)
                max = element.getRow();
        }
        
        return max;
    }
    
    public int getMaxCols(){
        int max = 0;
        for(Seat element: seatSet){
            if(element.getCol() > max)
                max = element.getCol();
        }
        
        return max;
    }
    
    public void loadSeats(){
        for(int i = 0; i < sessions.size(); i++){
            sessions.get(i).resetSeats();
        }
    }
    
}
