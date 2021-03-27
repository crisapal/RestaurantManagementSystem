package BusinessLayer;


public class BaseProduct extends MenuItem {


    public BaseProduct(String nume, double pret) {
        super(nume, pret);
    }

    @Override
    public double computePrice() {
        return super.getPrice();
    }

}
