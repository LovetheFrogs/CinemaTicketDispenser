package cinematicketdispenser;

import javax.naming.CommunicationException;
import sienens.CinemaTicketDispenser;
import urjc.UrjcBankServer;
import vending.MultiplexState;

public class PerformPayment extends Operation {
    
    private int price;
    private String title;
    private int numOfTickets;
    private String movieName;

    public PerformPayment(int price, int tkNum, String name, CinemaTicketDispenser disp, Multiplex mult) {
        super(disp, mult);
        this.price = price;
        this.title = mult.translate("REALIZAR PAGO");
        this.numOfTickets = tkNum;
        this.movieName = name;
    }

    public int getPrice() {
        return price;
    }

    @Override
    public String getTitle() {
        return title;
    }
   
    @Override
    public boolean doOperation() throws CommunicationException, Exception {
        boolean paid = false;
        boolean error = false;
        char c = ' ';
        mult.clearOptions(0, disp);
        disp.setMessageMode();
        disp.setTitle(mult.translate("INSERTE LA TARJETA DE CRÉDITO"));
        disp.setDescription(createDescription());        
        while (!paid && !error && c != '0') {
            c = disp.waitEvent(30);
            if (c == '1') {
                paid = createInvoice(); 
                error = !paid;
            }
            else paid = (c == 'F');
        }
        return paid;
    }

    private String createDescription() {
        return Integer.toString(this.numOfTickets) + mult.translate(" ENTRADAS PARA {0}: {1}€") + this.movieName + ": " + Integer.toString(price) + "€";
    }

    private boolean createInvoice() throws CommunicationException, Exception {
        double prc = price * 0.7;
        disp.retainCreditCard(false);
        UrjcBankServer bank = new UrjcBankServer();
        if (bank.comunicationAvaiable()) {
            if (MultiplexState.getInstance().getInscribedList().contains(disp.getCardNumber())) {
                disp.setDescription(Integer.toString(this.numOfTickets) + mult.translate(" ENTRADAS PARA {0}: {1}€") + this.movieName + ": " + Integer.toString((int) prc) + "€. " + mult.translate("SOCIO"));
                return bank.doOperation(disp.getCardNumber(), ((int) prc));
            }
            return bank.doOperation(disp.getCardNumber(), price);
        }
        return false;
    }
}
