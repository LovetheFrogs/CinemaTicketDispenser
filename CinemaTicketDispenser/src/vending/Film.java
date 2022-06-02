package vending;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

public class Film implements Serializable{
    
    private final String theater;
    private final String name;
    private final String poster;
    private final String description;
    private final ArrayList<Session> sessions;
    private final String price;

    public Film(String fileFilm) throws Exception {
        BufferedReader br = new BufferedReader(new FileReader(fileFilm));
        
        this.theater = br.readLine().replace("Theater: ", "");
        br.readLine();
        
        this.name = br.readLine().replace("Title: ", "");
        br.readLine();
        
        this.description = br.readLine();
        br.readLine();
        
        this.sessions = new ArrayList<>();
        String[] aux = br.readLine().replace("Sessions: ", "").split("\\s+");
        for (int i = 0; i < aux.length; i++){
            this.sessions.add(new Session(aux[i]));
        }
        br.readLine();
        
        this.poster = br.readLine().replace("Poster: ", "");
        br.readLine();
        
        this.price = br.readLine().replace("Price: ", "");
        br.close();
    }
        
    public String getName() {
        return name;
    }

    public String getPoster() {
        return poster;
    }

    public String getDescription() {
        return description;
    }
    
    public String getTheater(){
        return theater;
    }
    
    public String getPrice(){
        return price;
    }
    
    public ArrayList<Session> getSessions(){
        return sessions;
    }
}
