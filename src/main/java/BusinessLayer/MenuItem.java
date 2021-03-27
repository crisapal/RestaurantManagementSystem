package BusinessLayer;

public abstract class MenuItem implements java.io.Serializable { ///menuItem represents the main component of Composite Design Pattern- it can be implemented either with an abstract class/interface
    private String name;
    private double price;

    public MenuItem(String nume, double price) {
        this.name = nume;
        this.price = price;

    }
    public double computePrice() {
        return price;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return name;
    }
}
