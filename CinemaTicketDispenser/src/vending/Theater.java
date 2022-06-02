package vending;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;

public class Theater implements Serializable{
    
    private String number;
    private String price;
    private HashSet<Seat> seatSet;
    private ArrayList<Session> sessions;
    private Film film;
    
    public Theater(String fileFilm, String fileTheater) throws Exception{
        this.film = new Film(fileFilm);
        this.number = film.getTheater();
        this.price = film.getPrice();
        this.seatSet = loadSeats(fileTheater);
        this.sessions = film.getSessions();
    }
    
    public String getNumber(){
        return number;
    }
    
    public String getPrice(){
        return price;
    }
    
    public void addSession(Session session){
        this.sessions.add(session);
    }
    
    public Film getFilm(){
        return film;
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
    
    public HashSet<Seat> loadSeats(String filename) throws Exception{
        BufferedReader br = new BufferedReader(new FileReader(filename));
        HashSet<Seat> seats = new HashSet<>();
        String[] line;
        int i = 0;
        while((line = br.readLine().split("\\s+")) != null){
            for(int j = 0; j < line.length; j++){
                if(line[j] == "*"){
                    seats.add(new Seat(i, j));
                }
            }
            i++;
        }
        return seats;
    }   
}
