package BusinessLayer;

import java.util.ArrayList;

public class CompositeProduct extends MenuItem {

    ///represents a list of
    private ArrayList<BaseProduct> compozitie = new ArrayList<>();

    public CompositeProduct(String name, Double pret) {
        super(name, pret);
    }

    @Override
    public double computePrice() {
        return super.getPrice();
    }

    public ArrayList<BaseProduct> getCompozitie() {
        return compozitie;
    }

    public void setCompozitie(ArrayList<BaseProduct> compozitie) {
        this.compozitie = compozitie;
    }


    @Override
    public String toString() {
        return super.getName() + "\n" +
                "Ingrediente: " + compozitie;
    }
}
