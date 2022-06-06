
package cinematicketdispenser;

import java.util.ArrayList;
import sienens.CinemaTicketDispenser;

public class MainMenu extends Operation {
   
    private String title;
    private ArrayList<Operation> operationList;

    public MainMenu(CinemaTicketDispenser disp, Multiplex mult) {
        super(disp, mult);
        this.title = mult.translate("MENU PRINCIPAL");
        this.operationList = new ArrayList<>();
    }

    public ArrayList<Operation> getOperationList() {
        return operationList;
    }

    @Override
    public String getTitle() {
        return title;
    }
    
    @Override
    public boolean doOperation() throws Exception {
        operationList.add(new IdiomSelection(getDisp(), getMult()));
        operationList.add(new MovieTicketSale(getDisp(), getMult()));
        return true;
    } 
}
