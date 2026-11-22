public class Room {

    private int number;
    private boolean booked=false;
    private String type;
    private int price;

    public Room(int n,String t){
        number=n;
        type=t;

        switch(t){
            case "VIP": price=8000; break;
            case "Deluxe": price=6500; break;
            default: price=5000;
        }
    }

    public int getRoomNumber(){ return number; }
    public boolean isBooked(){ return booked; }

    public void bookRoom(){ booked=true; }
    public void freeRoom(){ booked=false; }

    public String getType(){ return type; }
    public int getPrice(){ return price; }
}