package cz.cvut.fel.omo.smartfactory.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Product {
    /**
     * The unique product name
     */
    private String name;

    /**
     * The sorted list of ProductionUnit ids
     */
    private List<String> sequence;

    public Product(String name) {
        this.name = name;
    }
}
