package cinematicketdispenser;

import static cinematicketdispenser.Language.*;
import sienens.CinemaTicketDispenser;

public class Multiplex {
    private Language idiom;
    
    public Multiplex(){
        this.idiom = ESP;
    }
    
    public void start() throws Exception{
        char c = ' ';
        CinemaTicketDispenser disp = new CinemaTicketDispenser();
        MainMenu mainMenu = new MainMenu(disp, this);
        mainMenu.doOperation();
        while (true && c != 0) {
            disp.setTitle(translate("DISPENSADOR DE TICKETS"));
            clearOptions(0, disp);
            disp.setOption(0, translate(mainMenu.getOperationList().get(0).getTitle()));
            disp.setOption(1, translate(mainMenu.getOperationList().get(1).getTitle()));
            disp.setOption(5, translate("CANCELAR"));
            c = disp.waitEvent(30);
            if (c == 'A') mainMenu.getOperationList().get(0).doOperation();
            else if (c == 'B') mainMenu.getOperationList().get(1).doOperation();
            else if (c == 'F') System.exit(0);
        }
    }

    public Language getIdiom() {
        return idiom;
    }

    public void setIdiom(Language idiom) {
        this.idiom = idiom;
    }
    
    public void clearOptions(int i, CinemaTicketDispenser disp) {
        disp.setMenuMode();
        for (int aux = i; aux < 5; aux++){
            disp.setOption(aux, "");
        }
        disp.setImage("");
        disp.setDescription("");
    }
    
    public String translate(String text) {
       return java.util.ResourceBundle.getBundle("languages/" + getIdiom()).getString(text);
    }
}
