package cinematicketdispenser;

import sienens.CinemaTicketDispenser;

public class IdiomSelection extends Operation {

    private String title;
    
    public IdiomSelection(CinemaTicketDispenser disp, Multiplex mult) {
        super(disp, mult);
        this.title = "CAMBIAR EL IDIOMA";
    }
    
    @Override
    public String getTitle() {
        return this.title;
    }
    
    @Override
    public boolean doOperation() throws Exception {
        boolean done = false;
        char c = ' ';
        disp.setOption(0, mult.translate("ESPAÑOL"));
        disp.setOption(1, mult.translate("CATALÁN"));
        disp.setOption(2, mult.translate("EUSKERA"));
        disp.setOption(3, mult.translate("INGLÉS"));
        while (c != 'F' && !done) {
            c = disp.waitEvent(30);
            if (c == 'A') mult.setIdiom(Language.ESP);
            else if (c == 'B') mult.setIdiom(Language.CAT);
            else if (c == 'C') mult.setIdiom(Language.EUK);
            else if (c == 'D') mult.setIdiom(Language.ING);
            done = true;
        }
        return true;
    }
}
