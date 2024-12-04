package cz.cvut.fel.omo.smartfactory.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Product {
    /**
     * The unique product name
     */
    private String name;

    /**
     * The list of ProductionUnit's
     * <p>
     * For example: RMRMRRMMWRWRW where R any robot, M any machine, W any worker
     */
    private String sequence = "";

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
