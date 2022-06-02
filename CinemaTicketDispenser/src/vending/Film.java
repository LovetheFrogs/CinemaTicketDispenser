package vending;

import java.io.Serializable;

public class Film implements Serializable{
    
    private final String theater;
    private final String name;
    private final String poster;
    private final String description;
    private final String price;

    public Film(String fileFilm) throws Exception {
        BufferedReader br = new BufferedReader(new FileReader(fileFilm));
        
        this.theater = br.readLine().replace("Theater: ", "");
        br.readLine();
        
        this.name = br.readLine().replace("Title: ", "");
        br.readLine();
        
        this.description = br.readLine();
        br.readLine();
        
        String sessions = br.readLine().replace("Sessions: ", "");
        
        br.readLine();
        
        this.poster = br.readLine().replace("Poster: ", "");
        br.readLine();
        
        this.price = br.readLine().replace("Price: ", "");
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
}
