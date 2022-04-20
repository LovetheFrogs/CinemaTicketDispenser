package cinematicketdispenser;

import java.util.ArrayList;
import sienens.CinemaTicketDispenser;

public class MainMenu extends Operation{
    
    private ArrayList<Operation> operationList;
    
    @Override
    public boolean doOperation(){
        presentMenu();
        return true;
    }
    
    @Override
    public String getTitle(){
        return "test";
    }
    
    public void presentMenu(){
        CinemaTicketDispenser dispenser = getDispenser();
        dispenser.setTitle("Multicines mfz - Bienvenido");
        dispenser.setOption(0, "Selecionar película.");
        dispenser.setOption(1, "Seleccionar sesión.");
        dispenser.setOption(2, "Seleccionar butacas.");
        dispenser.setOption(3, "Pagar entradas.");
        
    }
    
}
