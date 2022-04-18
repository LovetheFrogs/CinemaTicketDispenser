package vending;

import auxclasses.Pair;
import java.util.HashSet;

public class Session {
    private Pair hour;
    private HashSet<Seat> occupiedSeatSet;
    
    public Session(int x, int y){
        this.hour = new Pair(x, y);
        this.occupiedSeatSet = new HashSet<>();
    }
    
    public boolean isOccupied(int x, int y){
        Seat toCheck = new Seat(x, y);
        return this.occupiedSeatSet.contains(toCheck);
    }
    
    public void occupiesSeat(int x, int y){
        Seat toAdd = new Seat(x, y);
        this.occupiedSeatSet.add(toAdd);
    }
    
    public Pair getHour(){
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
