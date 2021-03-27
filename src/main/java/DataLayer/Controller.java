package DataLayer;

import BusinessLayer.Restaurant;
import PrensentationLayer.ViewAdmin;
import PrensentationLayer.ViewChef;
import PrensentationLayer.ViewWaiter;

public class Controller {
    Restaurant restaurant;
    ViewAdmin adminInterfata;
    ViewChef chefInterfata;
    ViewWaiter waiterInterfata;
    RestaurantSerializator serie;


    public Controller(String args[]) {
        serie = new RestaurantSerializator(args);
        restaurant = serie.DEserialize();
        adminInterfata = new ViewAdmin(restaurant, serie);
        waiterInterfata = new ViewWaiter(restaurant);
        chefInterfata = new ViewChef(restaurant);

        waiterInterfata.addObserver(chefInterfata);

        adminInterfata.setVisible(true);
        chefInterfata.setVisible(true);
    }
}
