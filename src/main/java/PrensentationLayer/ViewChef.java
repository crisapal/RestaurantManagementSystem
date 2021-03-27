package PrensentationLayer;

import BusinessLayer.Restaurant;

import javax.swing.*;
import java.util.Observable;
import java.util.Observer;

public class ViewChef extends JFrame implements Observer {  ///cumva implementat Observable din listeners
    private JPanel panouPrincipal = new JPanel();
    private Restaurant restaurant;

    public ViewChef(Restaurant restaurant) {
        this.restaurant = restaurant;


        ///////

        panouPrincipal.setLayout(new BoxLayout(panouPrincipal, BoxLayout.PAGE_AXIS));
        this.setBounds(700, 460, 600, 600);
        this.setContentPane(panouPrincipal);
        this.pack();
        this.setTitle("APP for CHEF");
        this.setSize(600, 300);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    @Override
    public void update(Observable o, Object arg) {
        try {
            if (o != null && arg.toString() != null) {
                this.setVisible(true);
                JOptionPane.showMessageDialog(null, "Heeey, COOK!\n" + arg.toString());
                panouPrincipal.add(new JLabel(arg.toString() + "\n"));

            }
        } catch (Exception ex) {
            System.out.println("\nPosibile erori din parte Observer");
        }
    }

}