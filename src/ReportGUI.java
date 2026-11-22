import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.*;
import java.time.*;
import java.time.format.*;

public class ReportGUI {

    static DateTimeFormatter formatter =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static void open(ArrayList<Booking> bookings){

        JFrame f=new JFrame("Booking Report");
        f.setSize(900,500);
        f.setLayout(new BorderLayout());

        // ===== TABLE =====
        DefaultTableModel model=new DefaultTableModel(
                new String[]{"Name","Room","Type","Total","Date"},0);

        JTable table=new JTable(model);
        table.setRowHeight(25);

        f.add(new JScrollPane(table),BorderLayout.CENTER);

        // ===== FILTER PANEL =====
        JPanel top=new JPanel();

        JTextField fromDate=new JTextField(10);
        JTextField toDate=new JTextField(10);

        fromDate.setToolTipText("yyyy-MM-dd");
        toDate.setToolTipText("yyyy-MM-dd");

        JButton filterBtn=new JButton("Filter");
        JButton resetBtn=new JButton("Reset");

        top.add(new JLabel("From:"));
        top.add(fromDate);
        top.add(new JLabel("To:"));
        top.add(toDate);
        top.add(filterBtn);
        top.add(resetBtn);

        f.add(top,BorderLayout.NORTH);

        // ===== TOTAL =====
        JLabel totalLabel=new JLabel("Total Income: 0");

        JPanel bottom=new JPanel();
        bottom.add(totalLabel);

        f.add(bottom,BorderLayout.SOUTH);

        // ===== LOAD FUNCTION =====
        Runnable loadAll = () -> {
            model.setRowCount(0);
            double total=0;

            for(Booking b:bookings){
                model.addRow(new Object[]{
                        b.getGuest().getName(),
                        b.getRoom().getRoomNumber(),
                        b.getRoom().getType(),
                        b.getTotal(),
                        b.getDateTime()
                });
                total+=b.getTotal();
            }

            totalLabel.setText("Total Income: "+total);
        };

        loadAll.run();

        // ===== FILTER ACTION =====
        filterBtn.addActionListener(e -> {

            try{
                LocalDate from = LocalDate.parse(fromDate.getText());
                LocalDate to = LocalDate.parse(toDate.getText());

                model.setRowCount(0);
                double total=0;

                for(Booking b:bookings){

                    LocalDateTime dt = LocalDateTime.parse(b.getDateTime(), formatter);
                    LocalDate d = dt.toLocalDate();

                    if((d.isEqual(from) || d.isAfter(from)) &&
                            (d.isEqual(to) || d.isBefore(to))){

                        model.addRow(new Object[]{
                                b.getGuest().getName(),
                                b.getRoom().getRoomNumber(),
                                b.getRoom().getType(),
                                b.getTotal(),
                                b.getDateTime()
                        });

                        total+=b.getTotal();
                    }
                }

                totalLabel.setText("Total Income: "+total);

            }catch(Exception ex){
                JOptionPane.showMessageDialog(f,"Enter date format: yyyy-MM-dd");
            }
        });

        // ===== RESET =====
        resetBtn.addActionListener(e -> loadAll.run());

        f.setVisible(true);
    }
}