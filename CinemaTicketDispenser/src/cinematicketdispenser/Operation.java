package cinematicketdispenser;

import sienens.CinemaTicketDispenser;

public abstract class Operation {
    
    private Multiplex multiplex;
    private CinemaTicketDispenser dispenser;
    
    public Operation(CinemaTicketDispenser dispenser, Multiplex multiplex){
        this.dispenser = dispenser;
        this.multiplex = multiplex;
    }
    
    public abstract boolean doOperation();
    
    public abstract String getTitle();

    public Multiplex getMultiplex() {
        return multiplex;
    }

    public CinemaTicketDispenser getDispenser() {
        return dispenser;
    }
    
}
