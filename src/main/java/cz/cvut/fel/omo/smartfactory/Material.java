package cz.cvut.fel.omo.smartfactory;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Material implements Stackable<Material> {
    /**
     * The identification
     */
    private final String id;

    /**
     * Count of materials in stack
     */
    private long count;

    /**
     * Price for single unit
     */
    private final float price;

    /**
     * Create single material
     *
     * @param id    The identification
     * @param price The price per unit
     */
    public Material(String id, float price) {
        this(id, price, 1);
    }

    /**
     * Create stack of material
     *
     * @param id    The identification
     * @param price The price per unit
     * @param count Number of material in stack
     */
    public Material(String id, float price, long count) {
        this.id = id;
        this.price = price;
        this.count = count;
    }

    @Override
    public void consume(Material material) {
        if (material.getId().equals(id)) {
            this.count += material.getCount();
            material.setCount(0);
        } else {
            throw new RuntimeException("The products are different");
        }
    }

    @Override
    public Material pop(long count) {
        if (this.count <= count) {
            return this;
        }
        this.count -= Math.max(0, count);
        return new Material(id, price, count);
    }
}
