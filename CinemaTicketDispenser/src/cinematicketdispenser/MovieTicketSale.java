package cinematicketdispenser;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashSet;
import javax.naming.CommunicationException;
import sienens.CinemaTicketDispenser;
import vending.*;

public class MovieTicketSale extends Operation {
    
    private String title;
    private ArrayList<Ticket> tickets;
    
    public MovieTicketSale(CinemaTicketDispenser disp, Multiplex mult) {
        super(disp, mult);
        this.title = "VENTA DE TICKETS";
        this.tickets = new ArrayList<>();
    }
    
    @Override
    public String getTitle() {
        return this.title;
    }
    
    @Override
    public boolean doOperation() throws Exception {
        char opt = ' ';
        this.tickets = new ArrayList<>();
        Theater tht = null;
        Session ses = null;
        ArrayList<Seat> seatsSelected = new ArrayList<>();
        boolean paid = false;
        while (!paid && opt != 'F' && opt != 0){
            printMovieTicketSale();
            opt = disp.waitEvent(30);
            if (opt == 'A') tht = selectTheater();
            else if (opt == 'B' && tht != null) ses = selectSession(tht);
            else if (opt == 'C' && ses != null) {
                seatsSelected = selectSeats(tht, ses, seatsSelected);
                for (Seat seat : seatsSelected) {
                    addTicket(tht, seat, ses);
                }
            }
            else if (opt == 'D' && seatsSelected.size() > 0){
                disp.setMenuMode();
                paid = performPayment(this.tickets);
            }
        }
        return paid;
    }

    private void printMovieTicketSale(){
        disp.setMenuMode();
        disp.setTitle(mult.translate("ELIGE OPCIÓN"));
        disp.setOption(0, mult.translate("SELECIONAR PELÍCULA"));
        disp.setOption(1, mult.translate("SELECCIONAR SESIÓN"));
        disp.setOption(2, mult.translate("SELECIONAR BUTACAS"));
        disp.setOption(3, mult.translate("REALIZAR PAGO"));
        disp.setOption(4, mult.translate(""));
        disp.setOption(5, mult.translate("CANCELAR"));
        disp.setDescription(mult.translate(""));
        disp.setImage("");
    }
    
    private Theater selectTheater() throws Exception {
        int i = 0;
        disp.setTitle(mult.translate("ELIJA PELÍCULA"));
        ArrayList<Theater> tList = MultiplexState.getInstance().getTheaterList();
        for (Theater theater : tList) {
            disp.setOption(i, theater.getFilm().getName());
            i++;
        }
        mult.clearOptions(i, disp);
        int c = getNumOfOpt(disp.waitEvent(30));
        if (c != 5) return tList.get(c);
        return null;
    }

    private Session selectSession(Theater tht) {
        int i = 0;
        mult.clearOptions(i, disp);
        disp.setTitle(mult.translate("SELECCIONE SESIÓN"));
        String posterPath = "./files/" + tht.getFilm().getPoster();
        disp.setImage(posterPath);
        disp.setDescription(tht.getFilm().getDescription());
        ArrayList<Session> sList = tht.getFilm().getSessions();
        for (Session session : sList) {
            disp.setOption(i, session.getHour());
            i++;
        }
        int c = getNumOfOpt(disp.waitEvent(30));
        if (c != 5) return sList.get(c);
        return null;
    }
    
    private ArrayList<Seat> selectSeats(Theater tht, Session ses, ArrayList<Seat> sList) {
        char c = '9';
        disp.setTitle(mult.translate("SELECCIONE BUTACAS"));
        presentSeats(tht, ses);
        disp.setOption(0, mult.translate("ACEPTAR"));
        disp.setOption(1, mult.translate("CANCELAR"));
        while ((c != 'A' && c != 'B' && c != 0)){
            c = disp.waitEvent(30);
            if (c != 'A' && c != 'B') {
                Seat seat = decodeSeat(c);
                sList = decideSeat(sList, ses, seat, tht);
            }
        }
        if (c != 'B') return sList;
        return null;
    }
    
    private boolean performPayment(ArrayList<Ticket> tickets) throws CommunicationException, Exception {
        int price = computePrice(tickets);
        boolean toReturn = new PerformPayment(price, this.tickets.size(), this.tickets.get(0).getTitle(), getDisp(), getMult()).doOperation();
        serializeMultiplexState();
        if (toReturn) {
            if (MultiplexState.getInstance().getInscribedList().contains(disp.getCardNumber()))
                printTickets(true);
            else
                printTickets(false);
        }
        if (!disp.expelCreditCard(30)) disp.retainCreditCard(true);
        return toReturn;
    }

    private void presentSeats(Theater tht, Session sesh){
        int rows = tht.getMaxRows();
        int cols = tht.getMaxCols();
        disp.setTheaterMode(rows, cols);
        HashSet<Seat> sSet = tht.getSeatSet();
        for(int i = 1; i <= rows; i++){
            for(int j = 1; j <= cols; j++)
                disp.markSeat(i, j, 0);
        }
        for (Seat seat : sSet) {
            disp.markSeat(seat.getRow(), seat.getCol(), interpretateBoolean(sesh.isOccupied(seat.getRow(), seat.getCol())));
        }
    }
    
    public static int getNumOfOpt(char opt){
        int result;
        result = switch (opt) {
            case 'A' -> 0;
            case 'B' -> 1;
            case 'C' -> 2;
            case 'D' -> 3;
            case 'E' -> 4;
            case 'F' -> 5;
            default -> -1;
        };
        return result;
    }

    private int interpretateBoolean(boolean value) {
        if(true == value)
            return 1;
        else
            return 2;
    }
    
    private Seat decodeSeat(char deco){
        byte col = (byte)(deco & 0xFF);
        byte row = (byte)((deco & 0xFF00) >> 8);
        return new Seat(row, col);
    }
    
    // Consultar
    private ArrayList<Seat> decideSeat(ArrayList<Seat> sList, Session ses, Seat seat, Theater tht){
        if (ses.isOccupied(seat.getRow(), seat.getCol())) {
            Seat toRemove = null;
            for (Seat st : sList) {
                if (st.equals(seat)) {
                    toRemove = st;
                    disp.markSeat(seat.getRow(), seat.getCol(), 2);
                    ses.unoccupiesSeat(seat.getRow(), seat.getCol());
                }
            }
            if (toRemove != null) sList.remove(toRemove);
        } 
        else if (sList.size() != 4 && !ses.isOccupied(seat.getRow(), seat.getCol())) {
            ses.occupiesSeat(seat.getRow(), seat.getCol());
            disp.markSeat(seat.getRow(), seat.getCol(), 3);
            sList.add(seat);
        }
        return sList;
    }

    private void addTicket(Theater tht, Seat seat, Session ses) {
        this.tickets.add(new Ticket(tht.getFilm().getName(), tht.getFilm().getPrice(), seat, tht.getNumber(), ses.getHour()));
        
    }

    private int computePrice(ArrayList<Ticket> tickets) {
        if (!tickets.isEmpty())
            return (Integer.parseInt(tickets.get(0).getPrice()) * tickets.size());
        return -1;
    }
    
    private void serializeMultiplexState() throws FileNotFoundException, IOException, Exception {
        FileOutputStream fileStream = new FileOutputStream("./system.file");
        ObjectOutputStream objectStream = new ObjectOutputStream(fileStream);
        objectStream.writeObject(MultiplexState.getInstance());
        objectStream.close();
    }

    private void printTickets(boolean member) {
        for (int i = 0; i < this.tickets.size(); i++) {
            double prc = Integer.parseInt(this.tickets.get(i).getPrice()) * 0.7;
            ArrayList<String> ticket = new ArrayList<>();
            
            ticket.add(this.tickets.get(i).getTitle());
            ticket.add("========================");
            ticket.add(this.tickets.get(i).getTheater());
            ticket.add(this.tickets.get(i).getSession());
            if (member) ticket.add(Integer.toString(((int) prc)) + "€");
            else ticket.add(this.tickets.get(i).getPrice() + "€");
            ticket.add(mult.translate("BUTACA: {0}-{1}") + this.tickets.get(i).getSeat().getRow() + "-" + this.tickets.get(i).getSeat().getCol());
            if (member) ticket.add(mult.translate("ESSOCIO"));
            disp.print(ticket);
        }
    }
}
