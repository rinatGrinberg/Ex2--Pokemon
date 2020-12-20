package gameClient;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class LoginFrame extends JFrame {

    private JPanel panel;

    private JLabel id_label;
    private JLabel l_label;
    private JLabel clarification_label;

    private JTextField id_;
    private JTextField level_;
    private JButton play;

    private static boolean status;
    private static int level;
    private static int idd;


    LoginFrame(String a) {
        super(a);

        level = -1;
        status = false;

        panel = new JPanel(new GridLayout(3, 2));

        id_label = new JLabel();
        l_label = new JLabel();
        clarification_label = new JLabel();
        clarification_label.setText("please enter id between length 9(only number) and level between 0-23");
        id_label.setText("Enter ID");
        l_label.setText("Enter Level");
        id_label.setFont(new Font("Arial", Font.BOLD,15));
        l_label.setFont(new Font("Arial", Font.BOLD,15));
        id_ = new JTextField();
        level_ = new JTextField();

        play = new JButton("Play");
        play.setBackground(Color.pink);
        play.setFont(new Font("Arial", Font.BOLD, 20));

        play.setEnabled(false);

        id_.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                String text = level_.getText();
                String text_id = id_.getText();
                int l = !text.equals("") ? Integer.parseInt(text) : -1;

                if ((e.getKeyChar() >= '0' && e.getKeyChar() <= '9') || e.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
                    id_.setEditable(true);

                    boolean flag = (l >= 0) && (l <= 23) && (text.length() > 0) && (text_id.length() == 9);
                    play.setEnabled(flag);

                }
                else {
                    id_.setEditable(false);
                    play.setEnabled(false);
                    play.setHideActionText(true);
                }
            }


            @Override
            public void keyReleased(KeyEvent e) {
                String text = level_.getText();
                String text_id = id_.getText();
                int l = !text.equals("") ? Integer.parseInt(text) : -1;
                if ((e.getKeyChar() >= '0' && e.getKeyChar() <= '9') || e.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
                    id_.setEditable(true);

                    boolean flag = (l >= 0) && (l <= 23) && (text.length() > 0) && (text_id.length() == 9);
                    play.setEnabled(flag);

                }
                else {
                    id_.setEditable(false);
                    play.setEnabled(false);
                }
            }
        });

        level_.addKeyListener(new KeyAdapter() {

            @Override
            public void keyPressed(KeyEvent e) {
//                super.keyPressed(e);

                String text = level_.getText();
                String text_id = id_.getText();
                int l = !text.equals("") ? Integer.parseInt(text) : -1;

                if ((e.getKeyChar() >= '0' && e.getKeyChar() <= '9') || e.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
                    level_.setEditable(true);
                    boolean flag = (l >= 0) && (l <= 23) && (text.length() > 0) && (text_id.length() == 9);
                    play.setEnabled(flag);

                }
                else {
                    level_.setEditable(false);
                    play.setEnabled(false);
                }

            }

            @Override
            public void keyReleased(KeyEvent e) {
                String text = level_.getText();
                String text_id = id_.getText();
                int l = !text.equals("") ? Integer.parseInt(text) : -1;

                if ((e.getKeyChar() >= '0' && e.getKeyChar() <= '9') || e.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
                    level_.setEditable(true);

                    boolean flag = (l >= 0) && (l <= 23) && (text.length() > 0) && (text_id.length() == 9);

                    play.setEnabled(flag);

                }
                else {
                    level_.setEditable(false);
                    play.setEnabled(false);
                }
            }

        });

        play.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String id = id_.getText();
                String level = level_.getText();
                idd=Integer.parseInt(id);

//                System.out.println(id);
//                System.out.println(level);

                setLevel(Integer.parseInt(level));

                setStatus(true);
                setVisible(false);
                System.out.println("play button pressed");



            }
        });

        panel.add(id_label);
        panel.add(id_);

        panel.add(l_label);
        panel.add(level_);
        panel.add(play);
        panel.add(new JLabel(new ImageIcon("pokemon.jpg")));

        add(panel, BorderLayout.CENTER);

        //setSize(400, 350);

        setBounds(750,250,800,500);
        setIconImage(new ImageIcon("pokemon2.jpg").getImage());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);


    }

    public static void main(String[] args) {
        LoginFrame loginFrame = new LoginFrame("test");
    }

    public  int getLevel() { return level;}
    public  void setLevel(int l) { level = l;}

    public  boolean getStatus() {return status;}
    public  void setStatus(boolean f) {status = f;}

    public  int  getid(){return idd;}


}
