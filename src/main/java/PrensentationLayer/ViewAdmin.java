package PrensentationLayer;

import BusinessLayer.BaseProduct;
import BusinessLayer.CompositeProduct;
import BusinessLayer.MenuItem;
import BusinessLayer.Restaurant;
import DataLayer.RestaurantSerializator;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ViewAdmin extends JFrame {
    private Restaurant restaurant;
    RestaurantSerializator serie;

    // main panel for the admin's functionality
    private JPanel panou = new JPanel();
    private JPanel panouPrincipal = new JPanel();
    private JPanel panouPrincipalA = new JPanel();
    private JPanel panouEditItems = new JPanel();
    private JPanel panouDeleteItems = new JPanel();
    private JPanel panouAddItems = new JPanel();
    private JPanel panouViewItems = new JPanel(); /// o sa acopere panou principal

    ///buttons to access the main menu
    private JButton addMIbutton = new JButton(" Add MenuItem");
    private JButton editMIbutton = new JButton(" Edit Menu");
    private JButton deleteMIbutton = new JButton(" Delete MenuItem");
    private JButton viewMIbutton = new JButton(("View Menu"));

    ///buttons to go back to the main menu
    private JButton backShow = new JButton("GO BACK TO MAIN MENU!");
    private JButton backEdit = new JButton("GO BACK TO MAIN MENU");
    private JButton backAdd = new JButton("GO BACK TO MAIN MENU");
    private JButton backDelete = new JButton("GO BACK TO MAIN MENU");


    public ViewAdmin(Restaurant restaurant, RestaurantSerializator serie) {
        this.restaurant = restaurant;
        this.serie = serie;
        ///add a picture to your panel
        addPictureinPanel(panouPrincipal);

        ///set the design of the main panel
        panouPrincipal.setLayout(new BoxLayout(panouPrincipal, BoxLayout.LINE_AXIS));
        panou.setLayout(new OverlayLayout(panou));
        panouPrincipalA.setLayout(new BoxLayout(panouPrincipalA, BoxLayout.PAGE_AXIS));
        panouEditItems.setVisible(false);
        panouAddItems.setVisible(false);
        panouViewItems.setVisible(false);
        panouDeleteItems.setVisible(false);
        panouPrincipal.setVisible(true);


        ///add functionality to the main method
        panouPrincipalA.add(addMIbutton);
        panouPrincipalA.add(editMIbutton);
        panouPrincipalA.add(deleteMIbutton);
        panouPrincipalA.add(viewMIbutton);
        panouPrincipal.add(panouPrincipalA);
        panou.add(panouAddItems);
        panou.add(panouViewItems);
        panou.add(panouDeleteItems);
        panou.add(panouEditItems);
        panou.add(panouPrincipal);

        ///set the layout
        this.setContentPane(panou);
        this.setBounds(700, 50, 700, 700);
        this.pack();
        this.setTitle("APLICATIE ADMINISTRATOR");
        this.setSize(600, 400);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        ///main menu LISTENERS for calling the panels of functionalities
        viewMIbutton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showAction();
                panouPrincipal.setVisible(false);
                panouViewItems.setVisible(true);
                backToAdminMenuButton(backShow);
            }
        });

        editMIbutton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editAction();
                panouPrincipal.setVisible(false);
                panouEditItems.setVisible(true);
                backToAdminMenuButton(backEdit);
            }
        });


        deleteMIbutton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteAction();
                panouPrincipal.setVisible(false);
                panouDeleteItems.setVisible(true);
                backToAdminMenuButton(backDelete);
            }
        });

        addMIbutton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addAction();
                panouPrincipal.setVisible(false);
                panouAddItems.setVisible(true);
                backToAdminMenuButton(backAdd);
            }
        });
    }


    public void backToAdminMenuButton(JButton back) {
        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panouViewItems.setVisible(false);
                panouAddItems.setVisible(false);
                panouDeleteItems.setVisible(false);
                panouEditItems.setVisible(false);
                panouPrincipal.setVisible(true);

                panouViewItems.removeAll();
                panouEditItems.removeAll();
                panouAddItems.removeAll();
                panouDeleteItems.removeAll();
            }
        });
    }

    ///main function for removing items from menu
    public void deleteAction() {
        JPanel rightPanelDel = new JPanel();
        JPanel leftPanelDel = new JPanel();
        JButton delCompositeProduct = new JButton("DELETE COMPOSITE PRODUCT"); ///main delete page sliders
        JButton delBaseProduct = new JButton("DELETE BASE PRODUCT");
        JPanel panelBaseProduct = new JPanel();
        JPanel panelCompositeProduct = new JPanel();

        ////setting up the panels for deleting components
        deleteBaseProduct(panelBaseProduct, panelCompositeProduct);
        deleteCompositeProduct(panelBaseProduct, panelCompositeProduct);


        ////adding buttons and panels for the delete function's features
        leftPanelDel.setLayout(new OverlayLayout(leftPanelDel));
        leftPanelDel.add(panelBaseProduct);
        leftPanelDel.add(panelCompositeProduct);
        rightPanelDel.add(delBaseProduct);
        rightPanelDel.add(delCompositeProduct);
        rightPanelDel.add(backDelete);
        panouDeleteItems.add(leftPanelDel);
        panouDeleteItems.add(rightPanelDel);

        delBaseProduct.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panelBaseProduct.setVisible(true);
                panelCompositeProduct.setVisible(false);
            }
        });

        delCompositeProduct.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panelBaseProduct.setVisible(false);
                panelCompositeProduct.setVisible(true);
            }
        });

        panouDeleteItems.setLayout(new BoxLayout(panouDeleteItems, BoxLayout.LINE_AXIS));
        rightPanelDel.setLayout(new BoxLayout(rightPanelDel, BoxLayout.PAGE_AXIS));
        leftPanelDel.setBackground(Color.GRAY);
    }

    public void deleteBaseProduct(JPanel panelBaseProduct, JPanel panelCompositeProduct) {
        JLabel base = new JLabel("Delete a base Item!\n");
        JButton addButtonBase = new JButton("DELETE ITEM!");
        JLabel baseItems = new JLabel("Select items to delete:");

        panelBaseProduct.setLayout(new BoxLayout(panelBaseProduct, BoxLayout.PAGE_AXIS));
        panelBaseProduct.add(base);
        panelBaseProduct.add(baseItems);
        deleteCompositeTable(panelBaseProduct, panelCompositeProduct, "BaseProduct", addButtonBase);
        panelBaseProduct.add(addButtonBase);

        panelBaseProduct.setVisible(false);
    }

    public void deleteCompositeProduct(JPanel panelBaseProduct, JPanel panelCompositeProduct) { ///to add or to delete it
        JLabel composite = new JLabel(("Delete a composite Item!\n"));
        JButton addButtonComposite = new JButton("DELETE ITEM!");
        JLabel compositeItems = new JLabel("Select items to delete:");

        panelCompositeProduct.setLayout(new BoxLayout(panelCompositeProduct, BoxLayout.PAGE_AXIS));
        panelCompositeProduct.add(composite);
        panelCompositeProduct.add(compositeItems);
        deleteCompositeTable(panelBaseProduct, panelCompositeProduct, "CompositeProduct", addButtonComposite);
        panelCompositeProduct.add(addButtonComposite);

        panelCompositeProduct.setVisible(false);
    }


    public void showAction() { ///FAC TABEL IN CARE VAD PRODUSELE
        JButton veziIngrediente = new JButton("Vezi Ingrediente!");

        JTable j;
        String[] columnNames = {"NUME ", "PRET", "CATEGORIE"};
        String[][] data = restaurant.getMenu();
        j = new JTable(data, columnNames);
        JScrollPane sp = new JScrollPane(j);
        sp.setPreferredSize(new Dimension(150, 350));

        ///set layout
        panouViewItems.setLayout(new BoxLayout(panouViewItems, BoxLayout.PAGE_AXIS));
        panouViewItems.add(sp);
        panouViewItems.add(veziIngrediente);
        panouViewItems.add(backShow);

        veziIngrediente.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (j.getModel().getValueAt(j.getSelectedRow(), 2).toString().equals("BaseProduct")) {
                        JFrame mesaj = new JFrame();
                        JOptionPane.showMessageDialog(mesaj, "Nu are ingrediente speciale");
                    } else if (j.getModel().getValueAt(j.getSelectedRow(), 2).toString().equals("CompositeProduct")) {
                        MenuItem menu = restaurant.findMenuItem(new CompositeProduct(j.getModel().getValueAt(j.getSelectedRow(), 0).toString(), Double.valueOf(j.getModel().getValueAt(j.getSelectedRow(), 1).toString())));
                        JOptionPane.showMessageDialog(new JFrame(), "Ingredientele pentru -" + menu);
                    }
                } catch (Exception ex) {
                    System.out.println("Nu s- a selectat nicio optiune sau caseta este goala");
                }
            }
        });
    }

    ///main functionality to the edit menu
    public void editAction() {
        JPanel rightPanelEdit = new JPanel();
        JPanel leftPanelEdit = new JPanel();
        JPanel baseProduct = new JPanel();
        JPanel compositeProduct = new JPanel();
        JButton editBaseProduct = new JButton("EDIT BASE PRODUCT");
        JButton editCompositeProduct = new JButton("EDIT COMPOSITE PRODUCT");
        leftPanelEdit.setLayout(new OverlayLayout(leftPanelEdit));


        ///set layout
        panouEditItems.setLayout(new BoxLayout(panouEditItems, BoxLayout.LINE_AXIS));
        rightPanelEdit.setLayout(new BoxLayout(rightPanelEdit, BoxLayout.PAGE_AXIS));
        leftPanelEdit.setBackground(Color.GRAY);

        editBaseProduct(baseProduct);
        editCompositeProduct(compositeProduct);

        leftPanelEdit.add(baseProduct);
        leftPanelEdit.add(compositeProduct);
        panouEditItems.add(leftPanelEdit);
        panouEditItems.add(rightPanelEdit);
        rightPanelEdit.add(editBaseProduct);
        rightPanelEdit.add(editCompositeProduct);
        rightPanelEdit.add(backEdit);
        baseProduct.setVisible(false);
        compositeProduct.setVisible(false);


        editBaseProduct.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                baseProduct.setVisible(true);
                compositeProduct.setVisible(false);
            }
        });

        editCompositeProduct.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                baseProduct.setVisible(false);
                compositeProduct.setVisible(true);
            }
        });


    }

    public void editBaseProduct(JPanel baseProduct) {
        JButton buttonEditBase = new JButton("Save the edit !");
        JLabel baseLabel = new JLabel(" Select and edit items:");

        JTable j;
        String[][] data = restaurant.getBaseMenu();
        String[] columnNames = {"NUME ", "PRET"};


        j = new JTable(data, columnNames);
        JScrollPane sp = new JScrollPane(j);
        sp.setPreferredSize(new Dimension(150, 350));
        baseProduct.setLayout(new BoxLayout(baseProduct, BoxLayout.PAGE_AXIS));
        baseProduct.add(baseLabel);
        baseProduct.add(sp);

        baseProduct.add(buttonEditBase);


        buttonEditBase.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String[][] date = new String[1000][2];
                for (int i = 0; i < j.getRowCount(); i++) {
                    if (j.getValueAt(i, 0) != null) { ///
                        date[i][0] = j.getValueAt(i, 0).toString();
                        date[i][1] = j.getValueAt(i, 1).toString();
                    } else
                        break;
                }
                JFrame frame = new JFrame();
                serie.serialize(restaurant);
                JOptionPane.showMessageDialog(frame, "S- a modificat cu succes!");
                restaurant.editBaseMenu(date);
            }
        });
    }

    public void editCompositeProduct(JPanel compositeProduct) {
        JButton buttonEditComposite = new JButton("Select the product to edit its ingredients!");
        JButton compositeSaveChanges = new JButton("Save the edit!");
        JLabel compositeLabel = new JLabel("Select and edit items:");
        compositeProduct.add(compositeLabel);
        JTable j;
        String[][] data = restaurant.getCompositeMenu();
        String[] columnNames = {"NUME ", "PRET"};

        j = new JTable(data, columnNames);
        JScrollPane sp = new JScrollPane(j);
        sp.setPreferredSize(new Dimension(150, 350));
        compositeProduct.setLayout(new BoxLayout(compositeProduct, BoxLayout.PAGE_AXIS));
        compositeProduct.add(sp);
        compositeProduct.add(buttonEditComposite);
        compositeProduct.add(compositeSaveChanges);

        buttonEditComposite.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(new JFrame(), " This functionality was not developed yet!!!!!");
            }
        });

        compositeSaveChanges.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String[][] date = new String[1000][2];
                for (int i = 0; i < j.getRowCount(); i++) {
                    if (j.getValueAt(i, 0) != null) { ///
                        date[i][0] = j.getValueAt(i, 0).toString();
                        date[i][1] = j.getValueAt(i, 1).toString();
                    } else
                        break;
                }
                JFrame frame = new JFrame();
                restaurant.editCompositeMenu(date);
                serie.serialize(restaurant);
                JOptionPane.showMessageDialog(frame, "S- a modificat cu succes!");

            }
        });
    }


    public void addAction() {

        JButton addBaseProduct = new JButton("ADD BASE PRODUCT");
        JButton addCompositeProduct = new JButton("ADD COMPOSITE PRODUCT");
        JPanel leftPanel = new JPanel();
        JPanel rightPanel = new JPanel();
        JPanel panouBaseProduct = new JPanel();
        JPanel panouCompositeProduct = new JPanel();

        panouBaseProduct.setVisible(false);
        panouCompositeProduct.setVisible(false);

        leftPanel.setLayout(new OverlayLayout(leftPanel));
        leftPanel.add(panouBaseProduct);
        leftPanel.add(panouCompositeProduct);
        addBaseProduct(panouBaseProduct, panouCompositeProduct);
        addCompositeProduct(panouBaseProduct, panouCompositeProduct);

        panouAddItems.setLayout(new BoxLayout(panouAddItems, BoxLayout.LINE_AXIS));
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.PAGE_AXIS));
        leftPanel.setBackground(Color.GRAY);
        rightPanel.add(addBaseProduct);
        rightPanel.add(addCompositeProduct);
        rightPanel.add(backAdd);
        panouAddItems.add(leftPanel);
        panouAddItems.add(rightPanel);

        addBaseProduct.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panouBaseProduct.setVisible(true);
                panouCompositeProduct.setVisible(false);
            }
        });

        addCompositeProduct.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panouBaseProduct.setVisible(false);
                panouCompositeProduct.setVisible(true);
            }
        });


    }

    public void addBaseProduct(JPanel panouBaseProduct, JPanel panouCompositeProduct) {
        JLabel base = new JLabel(" Add a new base Item!\n");
        JTextField addBaseProductName = new JTextField(10);
        JLabel addBaseProductNameLabel = new JLabel("Item's name:");
        JLabel addBaseProductPriceLabel = new JLabel(("Item's price:"));
        JTextField addBaseProductPrice = new JTextField(10);
        JButton addButtonBase = new JButton("ADD ITEM!");
        panouBaseProduct.setSize(10, 10);


        panouBaseProduct.setLayout(new BoxLayout(panouBaseProduct, BoxLayout.PAGE_AXIS));

        panouBaseProduct.add(base);
        panouBaseProduct.add(addBaseProductNameLabel);
        panouBaseProduct.add(addBaseProductName);
        panouBaseProduct.add(addBaseProductPriceLabel);
        panouBaseProduct.add(addBaseProductPrice);
        panouBaseProduct.add(addButtonBase);

        addButtonBase.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (restaurant.createNewMenuItem(new BaseProduct(addBaseProductName.getText(), Double.valueOf(addBaseProductPrice.getText())))) {
                        panouCompositeProduct.removeAll();
                        addCompositeProduct(panouBaseProduct, panouCompositeProduct);
                        JFrame frame = new JFrame();
                        ///trebuie sa fac refresh pe toate paginile unde as putea avea o lista cu produsele de baza
                        JOptionPane.showMessageDialog(frame, "S-a adaugat cu succes");
                        serie.serialize(restaurant);
                    } else {
                        JFrame frame = new JFrame();
                        JOptionPane.showMessageDialog(frame, "Nu s-a putut adauga");
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(new JFrame(), "Date introduse gresit!");
                }
            }
        });
    }

    public void addCompositeProduct(JPanel basePanel, JPanel panouCompositeProduct) {
        JLabel composite = new JLabel((" Add a composite Item!\n"));
        JLabel addCompositeNameLabel = new JLabel("Item's name:");
        JLabel addCompositePriceLabel = new JLabel(("Item's price:"));
        JButton addButtonComposite = new JButton("ADD ITEM!");
        JLabel addBaseItems = new JLabel("Pick the ingredients for the composite product!");
        JTextField addCompositePrice = new JTextField(10);
        JTextField addCompositeProductName = new JTextField(10);
        panouCompositeProduct.setLayout(new BoxLayout(panouCompositeProduct, BoxLayout.PAGE_AXIS));

        panouCompositeProduct.add(composite);
        panouCompositeProduct.add(addCompositeNameLabel);
        panouCompositeProduct.add(addCompositeProductName);
        panouCompositeProduct.add(addCompositePriceLabel);
        panouCompositeProduct.add(addCompositePrice);
        panouCompositeProduct.add(addBaseItems);
        addCompositeTable(basePanel, panouCompositeProduct, addButtonComposite, addCompositeProductName, addCompositePrice);
        panouCompositeProduct.add(addButtonComposite);
    }


    //// this is a special table from which to select the rowsto be deleted =D
    public void deleteCompositeTable(JPanel basePanel, JPanel compositePanel, String objectClass, JButton actionButton) {
        // ScrollPane for Table
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(33, 41, 494, 90);
        if (objectClass.equals("BaseProduct"))
            basePanel.add(scrollPane);
        else
            compositePanel.add(scrollPane);
        // Table
        final JTable table = new JTable();
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
        model.addColumn("Select"); model.addColumn("Name"); model.addColumn("Price");
        String[][] data;
        if (objectClass.equals("BaseProduct"))
            data = restaurant.getBaseMenu();
        else if (objectClass.equals("CompositeProduct"))
            data = restaurant.getCompositeMenu();
        else
            data = restaurant.getMenu();

        for (int i = 0; i < data.length; i++) {
            model.addRow(new Object[0]);
            model.setValueAt(false, i, 0);
            if (data[i][0] != null) {
                model.setValueAt(data[i][0], i, 1);
                model.setValueAt(data[i][1], i, 2);
            } else
                break;
        }

        ////button to delete all selected items <3
        actionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String[][] deleteInfo = new String[1000][2];
                int m = 0;

                for (int k = 0; k < model.getRowCount() - 1; k++) {
                    if (model.getValueAt(k, 0).toString().equals("true")) { ///if the box was checked
                        deleteInfo[m][0] = model.getValueAt(k, 1).toString();
                        deleteInfo[m][1] = model.getValueAt(k, 2).toString();
                        m++;
                    }
                }

                if (restaurant.deleteMenuItem(deleteInfo, objectClass)) {

                    JOptionPane.showMessageDialog(new Frame(), "S- a sters cu succes!");
                    serie.serialize(restaurant);
                    basePanel.removeAll();
                    compositePanel.removeAll();
                    deleteBaseProduct(basePanel, compositePanel);
                    deleteCompositeProduct(basePanel, compositePanel);
                }


            }
        });

    }


    public void addCompositeTable(JPanel basePanel, JPanel compositePanel, JButton actionButton, JTextField name, JTextField price) {
        // ScrollPane for Table
        JScrollPane scrollPane = new JScrollPane();

        scrollPane.setBounds(33, 41, 494, 90);
        compositePanel.add(scrollPane);

        // Table
        final JTable table = new JTable();

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

        String[][] data = restaurant.getBaseMenu();

        for (int i = 0; i < data.length; i++) {
            model.addRow(new Object[0]);
            model.setValueAt(false, i, 0);
            if (data[i][0] != null) {
                model.setValueAt(data[i][0], i, 1);
                model.setValueAt(data[i][1], i, 2);
            } else
                break;
        }

        ////button to delete all selected items <3
        actionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String[][] addIngredients = new String[1000][2];
                int m = 0;

                for (int k = 0; k < model.getRowCount() - 1; k++) {
                    if (model.getValueAt(k, 0).toString().equals("true")) { ///if the box was checked
                        addIngredients[m][0] = model.getValueAt(k, 1).toString();
                        addIngredients[m][1] = model.getValueAt(k, 2).toString();
                        m++;
                    }
                }
                if (addIngredients[0][0] == null)
                    JOptionPane.showMessageDialog(new JFrame(), "You cannot add a composite item without some ingredients selected! ");
                else if (restaurant.createNewMenuItem(name.getText(), Double.valueOf(price.getText()), addIngredients)) {
                    serie.serialize(restaurant);
                    JOptionPane.showMessageDialog(new JFrame(), "Successfully created");
                } else {
                    JOptionPane.showMessageDialog(new JFrame(), "It was not able to create");
                }


            }
        });

    }


    ///ADD PICTURE TO YOUR PANEL
    private void addPictureinPanel(JPanel panouPrincipal) {
        BufferedImage myPicture = null;
        try {
            myPicture = ImageIO.read(new File("bla.png"));

        } catch (IOException e) {
            e.printStackTrace();
        }
        JLabel picLabel = new JLabel(new ImageIcon(myPicture));
        panouPrincipal.add(picLabel);
    }
}
