package vending;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.lang.System.Logger;
import java.lang.System.Logger.Level;
import java.util.ArrayList;
import java.util.HashSet;

public class MultiplexState implements Serializable{
    
    private ArrayList<Long> inscribedList;
    private ArrayList<Theater> theaterList;
    private static MultiplexState instance;

    public ArrayList<Long> getInscribedList() {
        return inscribedList;
    }
    
    private MultiplexState() throws Exception{
        this.theaterList = loadMoviesAndSessions();
        this.inscribedList = loadInscribedList();
    }
    
    public static MultiplexState getInstance() throws Exception{
        if(instance == null){
            instance = loadMultiplexState();
        }
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
        int numOfFiles = new File("./files").list().length;
        for(int i = 1; i <= (numOfFiles / 3); i++){
            String moviePath = "./files/Movie" + i + ".txt";
            tList.add(new Theater(moviePath));
        }
        return tList;
    }
    
    private static MultiplexState loadMultiplexState() throws Exception {
        try {
            FileInputStream fileStream = new FileInputStream("./system.file");
            ObjectInputStream objectStream = new ObjectInputStream(fileStream);
            MultiplexState multState = (MultiplexState) objectStream.readObject();
            objectStream.close();
            return multState;
        } catch (FileNotFoundException ex) {
            File file = new File("./system.file");
            try {
                file.createNewFile();
            } catch (IOException ex1) {
                java.util.logging.Logger.getLogger(MultiplexState.class.getName()).log(java.util.logging.Level.SEVERE, null, ex1);
            }
        } catch (IOException | ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MultiplexState.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        return new MultiplexState();
    }

    private ArrayList<Long> loadInscribedList() throws IOException {
        ArrayList<Long> iList = new ArrayList<>();
        BufferedReader br = new BufferedReader(new FileReader("./inscribed.txt"));
        String lineRead;
        while((lineRead = br.readLine()) != null){
            iList.add(Long.parseLong(lineRead.replace(" ", "")));
        }
        return iList;
    }
}
