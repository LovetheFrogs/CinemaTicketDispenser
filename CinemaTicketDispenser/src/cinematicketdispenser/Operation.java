package cinematicketdispenser;

import sienens.CinemaTicketDispenser;

public abstract class Operation {
    
    protected CinemaTicketDispenser disp;
    protected Multiplex mult;

    public Operation(CinemaTicketDispenser disp, Multiplex mult) {
        this.disp = disp;
        this.mult = mult;
    }

    public CinemaTicketDispenser getDisp() {
        return disp;
    }

    public Multiplex getMult() {
        return mult;
    }
    
    public abstract boolean doOperation() throws Exception;
    
    public abstract String getTitle();
    
}
