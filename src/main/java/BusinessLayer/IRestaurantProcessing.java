package BusinessLayer;


import java.util.ArrayList;

public interface IRestaurantProcessing {
    ///admin

    /**
     * @param item
     * @return true if the item was added successfully
     * @pre item !=null
     * @post list.size== list.size@pre-1
     */
    public boolean createNewMenuItem(MenuItem item);


    /**
     * @param name
     * @param price
     * @param ingredients
     * @return true if composite item was added successfully
     * @pre name !=null
     * @pre price >0
     * @pre ingredients !=null
     * @post list.size = list.size@pre -1
     */
    public boolean createNewMenuItem(String name, Double price, String[][] ingredients);//trebuie sa tin cont daca adaug side item sau composite item


    /**
     * @param data
     * @param objectClass
     * @return true if the item was deleted successfully
     * @pre data !=null
     * @pre objectClass!= null
     * @post list.size = list.size@pre +1
     */
    public boolean deleteMenuItem(String[][] data, String objectClass);


    /**
     * @param data
     * @pre data!=null
     * @post list.size = list.size@pre+1
     */
    public void editBaseMenu(String[][] data);

    /**
     * @param data
     * @pre data!=null
     * @post list.size = list.size@pre+1
     */
    public void editCompositeMenu(String[][] data);


    //waiter

    /**
     * @param order
     * @return total price of an order and delete it
     * @pre order!=null
     * @post price>0
     */
    public double computePrice(Order order);

    /**
     * @param order
     * @param itemsInOrder
     * @return true if the order was created successfully
     * @pre order!=null
     * @pre itemsInOrder!=null
     * @post list.size = list.size@pre -1
     */
    public boolean createNewOrder(Order order, ArrayList<MenuItem> itemsInOrder);

    /**
     * @param orders
     * @pre orders!=null
     * @post bill!=null
     */
    public void generateBill(ArrayList<Order> orders); ///it s gonna be bounded with computePrice

}
