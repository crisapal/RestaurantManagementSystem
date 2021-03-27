package PrensentationLayer;

import BusinessLayer.MenuItem;
import BusinessLayer.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Observable;

public class ViewWaiter extends Observable {
    public static int id = 1;
    private JFrame frame;
    private Restaurant restaurant;
    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");


    private JPanel panou = new JPanel();
    private JPanel panouPrincipal = new JPanel();

    private JPanel panouViewOrder = new JPanel();
    private JPanel panouCreateBill = new JPanel();
    private JPanel panouAddOrder = new JPanel();

    private JButton addOrderButton = new JButton(" Add order!");
    private JButton createBillButton = new JButton("Create Bill");
    private JButton viewOrderButton = new JButton(("View Order!"));

    private JButton backView = new JButton("Go BACK TO MAIN MENU!");
    private JButton backBill = new JButton("Go BACK TO MAIN MENU!");
    private JButton backAdd = new JButton("Go BACK TO MAIN MENU!");


    public ViewWaiter(Restaurant restaurant) {
        this.frame = new JFrame();
        this.restaurant = restaurant;

        panouPrincipal.add(addOrderButton);
        panouPrincipal.add(viewOrderButton);
        panouPrincipal.add(createBillButton);
        panou.add(panouViewOrder);
        panou.add(panouCreateBill);
        panou.add(panouAddOrder);
        panou.add(panouPrincipal);


        ///////
        panouPrincipal.setVisible(true);
        panouViewOrder.setVisible(false);
        panouCreateBill.setVisible(false);
        panouAddOrder.setVisible(false);

        /////////
        frame.setBounds(50, 150, 600, 600);
        frame.setContentPane(panou);
        frame.pack();
        frame.setTitle("APLICATIE WAITER");
        frame.setSize(600, 450);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        ///////////////
        addOrderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addOrderAction();
                panouPrincipal.setVisible(false);
                panouAddOrder.setVisible(true);
                backToWaiterMenuButton(backAdd);
            }
        });

        createBillButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createBillAction();
                panouPrincipal.setVisible(false);
                panouCreateBill.setVisible(true);
                backToWaiterMenuButton(backBill);
            }
        });

        viewOrderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showAction();
                panouPrincipal.setVisible(false);
                panouViewOrder.setVisible(true);
                backToWaiterMenuButton(backView);
            }
        });
        frame.setVisible(true);
    }

    public void backToWaiterMenuButton(JButton back) {
        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panouAddOrder.setVisible(false);
                panouCreateBill.setVisible(false);
                panouViewOrder.setVisible(false);
                panouPrincipal.setVisible(true);
                ////
                panouAddOrder.removeAll();
                panouCreateBill.removeAll();
                panouViewOrder.removeAll();
            }
        });
    }

    public void showAction() {
        JButton seeProducts = new JButton("Select order to see the food!");
        JPanel panelLeft = new JPanel();
        JPanel panelRight = new JPanel();

        JTable j;
        String[] columnNames = {"ID", "DATE", "TABLE"};
        String[][] data = restaurant.getOrder();
        j = new JTable(data, columnNames);
        JScrollPane sp = new JScrollPane(j);
        sp.setPreferredSize(new Dimension(350, 400));
        ///set layout

        panouViewOrder.setLayout(new BoxLayout(panouViewOrder, BoxLayout.LINE_AXIS));
        panelRight.setLayout(new BoxLayout(panelRight, BoxLayout.PAGE_AXIS));
        panelLeft.add(sp);
        panelRight.add(seeProducts);
        panelRight.add(backView);
        panouViewOrder.add(panelLeft);
        panouViewOrder.add(panelRight);

        seeProducts.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (j.getSelectedRow() >= 0 && j.getSelectedRow() < 1000)
                    JOptionPane.showMessageDialog(new JFrame(), data[j.getSelectedRow()][3].toString());
                else
                    JOptionPane.showMessageDialog(new JFrame(), "WRONG SELECTION!");
            }
        });

    }

    public void addOrderAction() {
        JPanel panelLeft = new JPanel();
        JPanel panelRight = new JPanel();
        JLabel tableNo = new JLabel("\n\nAdd the number of the table!");
        JLabel addNewOrder = new JLabel("\t\tAdd a new order!\n");
        JTextField tableNumber = new JTextField(1);
        JButton saveOrder = new JButton("Save this order!");
        JLabel selectMenu = new JLabel("Select from menu!");
        panelLeft.setLayout(new BoxLayout(panelLeft, BoxLayout.PAGE_AXIS));
        panelRight.setLayout(new BoxLayout(panelRight, BoxLayout.PAGE_AXIS));
        panouAddOrder.add(panelLeft);
        panouAddOrder.add(panelRight);
        panelRight.add(addNewOrder);
        panelLeft.add(tableNo);
        panelLeft.add(tableNumber);
        panelRight.add(saveOrder);
        panelRight.add(backAdd);
        panelLeft.add(selectMenu);
        addJTable(panelLeft, saveOrder, tableNumber);


    }

    public void createBillAction() {
        JLabel selectOrders = new JLabel("Select orders to make a new bill for each!");
        JPanel panelLeft = new JPanel();
        JPanel panelRight = new JPanel();
        JButton bill = new JButton("Create a new BILL!");
        panelLeft.setLayout(new BoxLayout(panelLeft, BoxLayout.PAGE_AXIS));
        panelRight.setLayout(new BoxLayout(panelRight, BoxLayout.PAGE_AXIS));
        panelLeft.add(selectOrders);
        billJTable(panelLeft, bill);
        panelRight.add(bill);
        panelRight.add(backBill);
        panouCreateBill.add(panelLeft);
        panouCreateBill.add(panelRight);


    }


    public void addJTable(JPanel basePanel, JButton saveOrder, JTextField tableNumber) {
        // ScrollPane for Table
        JScrollPane scrollPane = new JScrollPane();

        basePanel.add(scrollPane);

        // Table
        final JTable table = new JTable();
        scrollPane.setPreferredSize(new Dimension(350, 300));
        scrollPane.setViewportView(table);
        // Model for Table
        DefaultTableModel model = new DefaultTableModel() {
            public Class<?> getColumnClass(int column) {
                switch (column) {
                    case 0:
                        return Boolean.class;
                    case 1:
                        return String.class;
                    case 2:
                        return String.class;
                    default:
                        return String.class;
                }
            }
        };
        table.setModel(model);
        model.addColumn("Select");
        model.addColumn("Name");
        model.addColumn("Price");
        model.addColumn("Category");


        String[][] data = restaurant.getMenu();

        for (int i = 0; i < data.length; i++) {
            model.addRow(new Object[0]);
            model.setValueAt(false, i, 0);
            if (data[i][0] != null) {
                model.setValueAt(data[i][0], i, 1);
                model.setValueAt(data[i][1], i, 2);
                model.setValueAt(data[i][2], i, 3);
            } else
                break;
        }

        ///here I should send my order to the menu's map and to the order list - i want to make a list with all the active orders and also, a map between menu and the order itself
        saveOrder.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ArrayList<MenuItem> comanda = new ArrayList<>();
                Order order;
                for (int i = 0; i < model.getRowCount() - 1; i++) {
                    if (model.getValueAt(i, 0).toString().equals("true")) { ///if the box was checked
                        if (data[i][2].equals("BaseProduct"))
                            comanda.add(new BaseProduct(data[i][0].toString(), Double.valueOf(data[i][1])));
                        else
                            comanda.add(new CompositeProduct(data[i][0].toString(), Double.valueOf(data[i][1])));
                    }
                }
                order = new Order(id++, new Date(), Integer.valueOf(tableNumber.getText()));
                try {
                    if (restaurant.createNewOrder(order, comanda)) {
                        JOptionPane.showMessageDialog(new JFrame(), "Comanda s- a creat cu succes!");
                    }

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(new JFrame(), "Nu s- a putut creea aceasta comanda! Numarul mesei nu e ok!");

                }
                ///OBSERVER FUNCTIONS
                setChanged();
                notifyObservers(generateMessage(order, comanda));
            }
        });
    }

    private String generateMessage(Order order, ArrayList<MenuItem> itemsInOrder) {
        String message = "# " + formatter.format(order.getDate()) + " -  NEW ORDER: ";
        int ok = 0;
        for (MenuItem item : itemsInOrder) {
            if (item.getClass().getSimpleName().equals("CompositeProduct")) {
                message += item.getName() + " ";
                ok = 1;
            }
        }
        if (ok == 1) {
            return message + " - for TABLE: " + order.getTableNumber();
        } else
            return null;
    }


    public void billJTable(JPanel basePanel, JButton bill) {
        // ScrollPane for Table
        JScrollPane scrollPane = new JScrollPane();

        basePanel.add(scrollPane);

        // Table
        final JTable table = new JTable();
        scrollPane.setPreferredSize(new Dimension(250, 400));

        scrollPane.setViewportView(table);
        // Model for Table
        DefaultTableModel model = new DefaultTableModel() {
            public Class<?> getColumnClass(int column) {
                switch (column) {
                    case 0:
                        return Boolean.class;
                    case 1:
                        return String.class;
                    case 2:
                        return String.class;
                    default:
                        return String.class;
                }
            }
        };
        table.setModel(model);
        model.addColumn("Select");
        model.addColumn("ID");
        model.addColumn("DATE");
        model.addColumn("Table Number");

        String[][] data = restaurant.getOrder();

        for (int i = 0; i < data.length; i++) {
            model.addRow(new Object[0]);
            model.setValueAt(false, i, 0);
            if (data[i][0] != null) {
                model.setValueAt(data[i][0], i, 1);
                model.setValueAt(data[i][1], i, 2);
                model.setValueAt(data[i][2], i, 3);
            } else
                break;
        }

        bill.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ArrayList<Order> orders = new ArrayList<>();
                int ok = 0;
                for (int i = 0; i < model.getRowCount() - 1; i++) {
                    if (model.getValueAt(i, 0).toString().equals("true")) {
                        try {
                            ok = 1;
                            orders.add(new Order(Integer.valueOf(data[i][0]), new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").parse(data[i][1].toString()), Integer.valueOf(data[i][2])));
                        } catch (Exception ex) {
                            System.out.println("SOMETHING IS WRONG!");
                        }
                    }
                }
                if (ok == 1)
                    JOptionPane.showMessageDialog(new JFrame(), "YOUR BILL WAS CREATED SUCCESSFULLY!\nCheck in text files by ID!");
                restaurant.generateBill(orders);
                panouCreateBill.removeAll();
                panouPrincipal.setVisible(true);
            }
        });
    }
}