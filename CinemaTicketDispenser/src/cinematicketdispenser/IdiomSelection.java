package cinematicketdispenser;

public class IdiomSelection extends Operation{
    
    public IdiomSelection(CinemaTicketDispenser dispenser, Multiplex multi){
        super(dispenser, multi);
    }
    
    @Override
    public String getTitle(){
        return "Cambiar el idioma";
    }
    
    @Override
    public boolean doOperation(){
        
        return true;
    }
}