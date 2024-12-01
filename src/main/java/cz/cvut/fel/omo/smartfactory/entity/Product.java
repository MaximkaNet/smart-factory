package cz.cvut.fel.omo.smartfactory.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Product {
    /**
     * The unique product name
     */
    private String name;

    /**
     * The list of ProductionUnit ids
     */
    private List<String> sequence = new ArrayList<>();

    public Product(String name) {
        this.name = name;
    }

    /**
     * Create a template of product
     */
    public Product createTemplate() {
        return new Product(name);
    }
}
