import java.time.*;
import java.time.format.*;

public class Booking {

    private Guest guest;
    private Room room;
    private String dateTime;

    private double discount;
    private double tax;
    private double total;

    public Booking(Guest guest, Room room, double discount){

        this.guest = guest;
        this.room = room;

        double base = room.getPrice();

        this.tax = base * 0.05;
        this.discount = discount;

        this.total = base + tax - discount;

        dateTime = LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    public Guest getGuest(){ return guest; }
    public Room getRoom(){ return room; }

    public double getDiscount(){ return discount; }
    public double getTax(){ return tax; }
    public double getTotal(){ return total; }

    public String getDateTime(){ return dateTime; }
}