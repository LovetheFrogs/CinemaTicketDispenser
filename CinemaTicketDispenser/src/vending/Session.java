package vending;

import java.io.Serializable;
import java.util.HashSet;

public class Session implements Serializable{
    private final String hour;
    private HashSet<Seat> occupiedSeatSet;
    
    public Session(String hour){
        this.hour = hour;
        this.occupiedSeatSet = new HashSet<>();
    }
    
    public boolean isOccupied(int x, int y){
        for (Seat seat : this.occupiedSeatSet) {
            if (seat.getRow() == x && seat.getCol() == y)
                return true;
        }
        return false;
    }
    
    public void occupiesSeat(int x, int y){
        Seat toAdd = new Seat(x, y);
        this.occupiedSeatSet.add(toAdd);
    }
    
    public String getHour(){
        return this.hour;
    }
    
    public void unoccupiesSeat(int x, int y){
        Seat toRemove = new Seat(x, y);
        this.occupiedSeatSet.remove(toRemove);
    }
    
    public void resetSeats(){
        occupiedSeatSet = new HashSet<>(); 
    }
}
