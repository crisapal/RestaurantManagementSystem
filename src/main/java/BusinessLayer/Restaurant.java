package BusinessLayer;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Restaurant implements IRestaurantProcessing, Serializable {
    Map<Order, ArrayList<MenuItem>> legaturaComanda = new HashMap<>();  /// bounding orders
    ArrayList<MenuItem> produseRestaurant = new ArrayList<>();
    ArrayList<Order> listOfOrders = new ArrayList<>();
    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

    @Override
    public boolean createNewMenuItem(MenuItem item) {  //keep attention on what are you adding: side item/ composite product
        assert item != null;
        int size = produseRestaurant.size();
        if (findMenuItem(item) != null)
            return false;
        else {
            if (item.getClass().getSimpleName().equals("BaseProduct"))
                produseRestaurant.add(item); ///add by name - simple way
        }
        assert size == produseRestaurant.size();
        return true;
    }

    ///this method is special for the composite products
    public boolean createNewMenuItem(String name, Double price, String[][] ingredients) {  // side item/ composite item
        assert name != null;
        assert price > 0;
        assert ingredients != null;
        int size = produseRestaurant.size();
        if (findMenuItem(new CompositeProduct(name, price)) != null)
            return false;
        else {
            ArrayList<BaseProduct> compoz = new ArrayList<>();
            int i = 0;
            while (ingredients[i][0] != null) {
                compoz.add(new BaseProduct(ingredients[i][0], Double.valueOf(ingredients[i][1])));
                i++;
            }
            CompositeProduct item = new CompositeProduct(name, price);
            item.setCompozitie(compoz);
            produseRestaurant.add(item);
        }
        assert size == produseRestaurant.size() - 1;
        return true;
    }

    ///I DELETE BY NAME, BUT I also need to delete those composite products that contain a base products which is deleted
    @Override
    public boolean deleteMenuItem(String[][] data, String objectClass) {
        assert data != null;
        assert objectClass != null;
        int size = produseRestaurant.size();

        int ok = 0, i = 0;
        while (data[i][0] != null) {
            if (objectClass.equals("BaseProduct")) {
                BaseProduct item = (BaseProduct) findMenuItem(new BaseProduct(data[i][0], Double.valueOf(data[i][1])));
                deleteByIngredients(item);
                produseRestaurant.remove(item);
                ok = 1;
            } else {
                CompositeProduct item = (CompositeProduct) findMenuItem(new CompositeProduct(data[i][0], Double.valueOf(data[i][1])));
                produseRestaurant.remove(item);
                ok = 1;
            }
            i++;

        }
        assert size == produseRestaurant.size() + 1;

        if (ok == 1)
            return true;
        else
            return false;
    }

    private void deleteByIngredients(BaseProduct item) {
        for (int i = 0; i < produseRestaurant.size(); i++) {
            if (produseRestaurant.get(i).getClass().getSimpleName().equals("CompositeProduct")) {
                if (findMenuItem(item) != null) {
                    produseRestaurant.remove(i); ///i will remove the product that has that base prod as ingredient
                }
            }
        }
    }

    public MenuItem findMenuItem(MenuItem item) {
        for (MenuItem find : produseRestaurant) {
            if (find.getName().equals(item.getName()))
                return find;
        }
        return null;
    }


    @Override
    public boolean createNewOrder(Order order, ArrayList<MenuItem> itemsInOrder) {
        assert order != null;
        assert itemsInOrder != null;

        int ok = 0;
        legaturaComanda.put(order, itemsInOrder);
        listOfOrders.add(order);
        ok = 1;

        if (ok == 1)
        {
            return true;
        }
        return false;
    }


    /////orders features
    @Override
    public double computePrice(Order order) {
        assert order != null;
        double price = 0;
        ArrayList<MenuItem> items = legaturaComanda.get(order);
        for (MenuItem prod : items) {
            price += prod.getPrice();
        }
        assert price > 0;
        return price;
    }

    @Override
    public void generateBill(ArrayList<Order> orders) {
        assert orders != null;
        for (Order comanda : orders) { ///for all selected orders
            Order com = findOrder(comanda);
            if (com != null) {
                double price = computePrice(com);
                String bill = " BILL\n\n Order NO. " + com.getOrderId() + "\n TABLE NO. " + com.getTableNumber() +
                        "\n " + "Date: " + formatter.format(com.getDate()) + "\n\n\n Ordered items:\n\n ";
                ArrayList<MenuItem> items = legaturaComanda.get(com);
                for (MenuItem prod : items) {
                    bill += "* " + prod.getName() + "      " + prod.computePrice() + "lei \n ";
                }
                bill += "\n\n TOTAL: " + price;
                try {
                    BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("BILL NO. " + com.getOrderId() + " .txt")));
                    bw.write(bill);
                    bw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                listOfOrders.remove(com); ///after generating its bill, I want to delete it!
            }
        }
    }

    public String[][] getMenu() {
        String[][] data = new String[1000][3];
        int i = 0;
        for (MenuItem vezi : produseRestaurant) {
            data[i][0] = vezi.getName();
            data[i][1] = String.valueOf(vezi.getPrice());
            data[i][2] = vezi.getClass().getSimpleName();
            i++;
        }

        return data;
    }

    public String[][] getBaseMenu() {
        String[][] data = new String[1000][2];
        int i = 0;
        for (MenuItem vezi : produseRestaurant) {
            if (vezi.getClass().getSimpleName().equals("BaseProduct")) {
                data[i][0] = vezi.getName();
                data[i][1] = String.valueOf(vezi.getPrice());
                i++;
            }
        }

        return data;
    }

    public void editBaseMenu(String data[][]) { //keep columns order
        int i = 0, k = 0;
        for (MenuItem vezi : produseRestaurant) {
            if (vezi.getClass().getSimpleName().equals("BaseProduct")) {
                produseRestaurant.get(i).setName(data[k][0]);
                produseRestaurant.get(i).setPrice(Double.valueOf(data[k][1]));
                k++;
            }
            i++;
        }
    }

    public void editCompositeMenu(String[][] date) {
        int i = 0, k = 0;
        for (MenuItem vezi : produseRestaurant) {
            if (vezi.getClass().getSimpleName().equals("CompositeProduct")) {
                produseRestaurant.get(i).setName(date[k][0]);
                produseRestaurant.get(i).setPrice(Double.valueOf(date[k][1]));
                k++;
            }
            i++;
        }
    }

    public String[][] getCompositeMenu() {
        String[][] data = new String[1000][2];
        int i = 0;
        for (MenuItem vezi : produseRestaurant) {
            if (vezi.getClass().getSimpleName().equals("CompositeProduct")) {
                data[i][0] = vezi.getName();
                data[i][1] = String.valueOf(vezi.getPrice());
                i++;
            }
        }
        return data;
    }


    public String[][] getOrder() {
        String data[][] = new String[1000][4];
        int i = 0;

        for (Order comanda : listOfOrders) {
            data[i][0] = String.valueOf(comanda.getOrderId());
            data[i][1] = String.valueOf(formatter.format(comanda.getDate()));
            data[i][2] = String.valueOf(comanda.getTableNumber());
            ArrayList<MenuItem> prod = legaturaComanda.get(comanda);
            String products = "Order N0. " + comanda.getOrderId() + " has next items from MENU:\n";
            for (MenuItem items : prod) {
                products += "* " + items.getName() + "\n";
            }
            data[i][3] = products;
            i++;
        }
        return data;
    }

    public Order findOrder(Order comanda) {
        for (Order comenzi : listOfOrders) {
            if (comanda.getOrderId() == comenzi.getOrderId())
                return comenzi;
        }
        return null;
    }

}
