package cinematicketdispenser;

import sienens.CinemaTicketDispenser;

public class Multiplex {
    
    public CinemaTicketDispenser dispenser;
    
    public void run(){
        MainMenu menu = new MainMenu();
        menu.doOperation();
    }
    
}
