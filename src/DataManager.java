import java.io.*;
import java.util.*;

public class DataManager {

    public static void save(ArrayList<Booking> list){
        try(FileWriter fw=new FileWriter("data.txt")){
            for(Booking b:list){
                fw.write(
                        b.getGuest().getName()+","+
                                b.getGuest().getPhone()+","+
                                b.getRoom().getRoomNumber()+","+
                                b.getRoom().getType()+","+
                                b.getDiscount()+","+
                                b.getTax()+","+
                                b.getTotal()+","+
                                b.getDateTime()+"\n"
                );
            }
        }catch(Exception e){ e.printStackTrace(); }
    }

    public static void load(ArrayList<Booking> list,ArrayList<Room> rooms){
        try{
            File f=new File("data.txt");
            if(!f.exists()) return;

            BufferedReader br=new BufferedReader(new FileReader(f));
            String line;

            while((line=br.readLine())!=null){

                String[] d=line.split(",");

                Guest g=new Guest(d[0],d[1]);
                int roomNo=Integer.parseInt(d[2]);
                double discount=Double.parseDouble(d[4]);

                for(Room r:rooms){
                    if(r.getRoomNumber()==roomNo){
                        r.bookRoom();
                        list.add(new Booking(g,r,discount));
                    }
                }
            }
            br.close();
        }catch(Exception e){ e.printStackTrace(); }
    }
}