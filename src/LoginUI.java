import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class LoginUI {

    public static void main(String[] args){

        JFrame frame = new JFrame("Hotel Relax");
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new GridLayout(1,2));

        // ===== LEFT SIDE (IMAGE STYLE) =====
        JPanel left = new JPanel(){
            protected void paintComponent(Graphics g){
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;

                GradientPaint gp = new GradientPaint(
                        0,0,new Color(255,230,180),
                        0,getHeight(),new Color(200,180,140)
                );
                g2.setPaint(gp);
                g2.fillRect(0,0,getWidth(),getHeight());

                g2.setColor(new Color(30,60,90));
                g2.setFont(new Font("Segoe UI",Font.BOLD,40));
                g2.drawString("Hotel & Resort", 60, 150);

                g2.setFont(new Font("Segoe UI",Font.PLAIN,20));
                g2.drawString("Enjoy Your Vacation", 60, 200);
            }
        };

        // ===== RIGHT SIDE (LOGIN FORM) =====
        JPanel right = new JPanel(new GridBagLayout());
        right.setBackground(new Color(40,70,110));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(12,12,12,12);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel title = new JLabel("SIGN IN", JLabel.CENTER);
        title.setFont(new Font("Segoe UI",Font.BOLD,22));
        title.setForeground(Color.WHITE);

        JTextField user = new JTextField("Username");
        user.setForeground(Color.GRAY);

        JPasswordField pass = new JPasswordField("Password");
        pass.setForeground(Color.GRAY);
        pass.setEchoChar((char)0);

        JCheckBox show = new JCheckBox("Show Password");
        show.setForeground(Color.WHITE);
        show.setBackground(new Color(40,70,110));

        JButton login = new JButton("LOGIN");
        login.setBackground(new Color(255,200,80));
        login.setForeground(Color.BLACK);
        login.setFont(new Font("Segoe UI",Font.BOLD,14));

        // ===== INPUT STYLE =====
        user.setBorder(BorderFactory.createEmptyBorder(8,8,8,8));
        pass.setBorder(BorderFactory.createEmptyBorder(8,8,8,8));

        // ===== PLACEHOLDER =====
        user.addFocusListener(new FocusAdapter(){
            public void focusGained(FocusEvent e){
                if(user.getText().equals("Username")){
                    user.setText("");
                    user.setForeground(Color.BLACK);
                }
            }
            public void focusLost(FocusEvent e){
                if(user.getText().isEmpty()){
                    user.setText("Username");
                    user.setForeground(Color.GRAY);
                }
            }
        });

        pass.addFocusListener(new FocusAdapter(){
            public void focusGained(FocusEvent e){
                if(String.valueOf(pass.getPassword()).equals("Password")){
                    pass.setText("");
                    pass.setForeground(Color.BLACK);
                    pass.setEchoChar('•');
                }
            }
            public void focusLost(FocusEvent e){
                if(pass.getPassword().length==0){
                    pass.setText("Password");
                    pass.setForeground(Color.GRAY);
                    pass.setEchoChar((char)0);
                }
            }
        });

        // ===== SHOW PASSWORD =====
        show.addActionListener(e -> {
            if(show.isSelected()){
                pass.setEchoChar((char)0);
            } else {
                if(!String.valueOf(pass.getPassword()).equals("Password")){
                    pass.setEchoChar('•');
                }
            }
        });

        // ===== ADD COMPONENTS =====
        gbc.gridx=0; gbc.gridy=0;
        right.add(title,gbc);

        gbc.gridy=1;
        right.add(user,gbc);

        gbc.gridy=2;
        right.add(pass,gbc);

        gbc.gridy=3;
        right.add(show,gbc);

        gbc.gridy=4;
        right.add(login,gbc);

        // ===== LOGIN ACTION =====
        login.addActionListener(e -> {
            String username = user.getText();
            String password = new String(pass.getPassword());

            if(username.equals("Deba007") && password.equals("402949")){
                frame.dispose();
                HotelGUI.main(new String[]{});
            } else {
                JOptionPane.showMessageDialog(frame,"Wrong login!");
            }
        });

        frame.add(left);
        frame.add(right);

        frame.setVisible(true);
    }
}