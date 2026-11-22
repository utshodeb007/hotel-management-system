import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.*;

public class HotelGUI {

    static ArrayList<Room> rooms=new ArrayList<>();
    static ArrayList<Booking> bookings=new ArrayList<>();

    static DefaultTableModel model;
    static JComboBox<Integer> roomBox;

    static JLabel totalRoomsLabel, availableLabel, incomeLabel;

    public static void main(String[] args){

        for(int i=500;i<=520;i++){
            if(i<=505) rooms.add(new Room(i,"Standard"));
            else if(i<=510) rooms.add(new Room(i,"Deluxe"));
            else rooms.add(new Room(i,"VIP"));
        }

        JFrame f=new JFrame("Hotel System");
        f.setExtendedState(JFrame.MAXIMIZED_BOTH);
        f.setLayout(new BorderLayout());
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // ===== TOP DASHBOARD =====
        JPanel topPanel=new JPanel(new GridLayout(1,3,10,10));
        topPanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

        totalRoomsLabel=box("Total Rooms","0");
        availableLabel=box("Available Rooms","0");
        incomeLabel=box("Total Income","0");

        topPanel.add(totalRoomsLabel);
        topPanel.add(availableLabel);
        topPanel.add(incomeLabel);

        f.add(topPanel,BorderLayout.NORTH);

        // ===== TABLE =====
        model=new DefaultTableModel(
                new String[]{"Name","Room","Type","Rent","VAT","Discount","Total","Date"},0);

        JTable table=new JTable(model);
        table.setRowHeight(25);

        f.add(new JScrollPane(table),BorderLayout.CENTER);

        // ===== FORM BOX DESIGN =====
        JPanel form=new JPanel(new GridLayout(2,4,10,10));
        form.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

        JTextField name=new JTextField();
        JTextField phone=new JTextField();
        JTextField discount=new JTextField();

        roomBox=new JComboBox<>();
        refresh();

        JLabel info=new JLabel("Select room", JLabel.CENTER);

        roomBox.addActionListener(e->{
            Integer r=(Integer)roomBox.getSelectedItem();
            if(r==null) return;
            for(Room rm:rooms){
                if(rm.getRoomNumber()==r){
                    info.setText("Type: "+rm.getType()+" | Price: "+rm.getPrice());
                }
            }
        });

        JButton book=new JButton("Book");
        JButton del=new JButton("Delete");
        JButton report=new JButton("Report");

        // Boxes
        form.add(createBox("Name",name));
        form.add(createBox("Phone",phone));
        form.add(createBox("Room",roomBox));
        form.add(createBox("Discount",discount));

        form.add(createBox("Room Info",info));

        JPanel empty1=new JPanel();
        form.add(empty1);

        JPanel btnPanel=new JPanel(new GridLayout(1,3,5,5));
        btnPanel.add(book);
        btnPanel.add(del);
        btnPanel.add(report);

        form.add(btnPanel);

        JPanel empty2=new JPanel();
        form.add(empty2);

        f.add(form,BorderLayout.SOUTH);

        // ===== LOAD =====
        DataManager.load(bookings,rooms);

        for(Booking b:bookings){
            model.addRow(new Object[]{
                    b.getGuest().getName(),
                    b.getRoom().getRoomNumber(),
                    b.getRoom().getType(),
                    b.getRoom().getPrice(),
                    b.getTax(),
                    b.getDiscount(),
                    b.getTotal(),
                    b.getDateTime()
            });
        }

        updateDashboard();

        // ===== BOOK =====
        book.addActionListener(e->{
            int r=(int)roomBox.getSelectedItem();
            double d=0;
            try{ d=Double.parseDouble(discount.getText()); }catch(Exception ex){}

            for(Room rm:rooms){
                if(rm.getRoomNumber()==r){
                    Guest g=new Guest(name.getText(),phone.getText());
                    rm.bookRoom();
                    Booking b=new Booking(g,rm,d);
                    bookings.add(b);

                    model.addRow(new Object[]{
                            g.getName(),r,rm.getType(),
                            rm.getPrice(),b.getTax(),
                            b.getDiscount(),b.getTotal(),b.getDateTime()
                    });

                    DataManager.save(bookings);
                    refresh();
                    updateDashboard();
                }
            }
        });

        // ===== DELETE =====
        del.addActionListener(e->{
            int row=table.getSelectedRow();
            if(row>=0){
                int r=(int)model.getValueAt(row,1);
                for(Room rm:rooms){
                    if(rm.getRoomNumber()==r) rm.freeRoom();
                }
                bookings.remove(row);
                model.removeRow(row);
                DataManager.save(bookings);
                refresh();
                updateDashboard();
            }
        });

        // ===== REPORT =====
        report.addActionListener(e->ReportGUI.open(bookings));

        f.setVisible(true);
    }

    // ===== BOX DESIGN =====
    static JLabel box(String title,String value){
        JLabel label=new JLabel("<html><center>"+title+"<br><b>"+value+"</b></center></html>");
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        return label;
    }

    static JPanel createBox(String title,JComponent comp){
        JPanel panel=new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder(title));
        panel.add(comp,BorderLayout.CENTER);
        return panel;
    }

    static void updateDashboard(){
        int total=rooms.size();
        int available=0;
        double income=0;

        for(Room r:rooms){
            if(!r.isBooked()) available++;
        }

        for(Booking b:bookings){
            income+=b.getTotal();
        }

        totalRoomsLabel.setText("<html><center>Total Rooms<br><b>"+total+"</b></center></html>");
        availableLabel.setText("<html><center>Available Rooms<br><b>"+available+"</b></center></html>");
        incomeLabel.setText("<html><center>Total Income<br><b>"+income+"</b></center></html>");
    }

    static void refresh(){
        roomBox.removeAllItems();
        for(Room r:rooms){
            if(!r.isBooked()){
                roomBox.addItem(r.getRoomNumber());
            }
        }
    }
}