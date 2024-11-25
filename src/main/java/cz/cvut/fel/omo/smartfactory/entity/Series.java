package cz.cvut.fel.omo.smartfactory.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Series {
    private Long id;
    private int number;
    private int amount;
    private List<Product> products;

    public Series(int seriesNumber, int amount) {
        this.number = seriesNumber;
        this.amount = amount;
        products = new ArrayList<>();
    }

    public void addProduct(Product manufacturedProduct) {
        products.add(manufacturedProduct);
    }
}
