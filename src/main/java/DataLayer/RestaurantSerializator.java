package DataLayer;


import BusinessLayer.Restaurant;

import java.io.*;

public class RestaurantSerializator {
    private String fileName = "Restaurant.ser";

    public RestaurantSerializator(String[] args) {
        this.fileName = args[0];
    }

    public void serialize(Restaurant r) {
        try {
            FileOutputStream fileOut = new FileOutputStream(fileName);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(r);
            out.close();
            fileOut.close();
            System.out.printf("Serialized data is saved in Restaurant.ser");
        } catch (IOException i) {
            i.printStackTrace();
        }
    }

    public Restaurant DEserialize() {
        Restaurant r = null;
        try {
            FileInputStream fileIn = new FileInputStream(fileName);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            r = (Restaurant) in.readObject();
            in.close();
            fileIn.close();
            System.out.println(r);
            return r;
        } catch (IOException i) {
            System.out.println(i);
            r = new Restaurant();
            serialize(r);
            return r;
        } catch (ClassNotFoundException c) {

            System.out.println("Restaurant class not found");
            c.printStackTrace();
            return r = new Restaurant();
        }
    }

}