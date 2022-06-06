package vending;

public class Ticket {

    private String title;
    private String price;
    private Seat seat;
    private String theater;
    private String session;

    public Seat getSeat() {
        return seat;
    }

    public void setSeat(Seat seat) {
        this.seat = seat;
    }

    public Ticket(String title, String price, Seat seat, String theater, String session) {
        this.title = title;
        this.price = price;
        this.seat = seat;
        this.theater = theater;
        this.session = session;
    }

    public String getTheater() {
        return theater;
    }

    public void setTheater(String theater) {
        this.theater = theater;
    }

    public String getSession() {
        return session;
    }

    public void setSession(String session) {
        this.session = session;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
