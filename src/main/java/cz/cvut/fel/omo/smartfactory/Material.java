package cz.cvut.fel.omo.smartfactory.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Material {
    private final String id;
    private final float price;
    private int used = 0;

    public Material(String id, float price) {
        this.id = id;
        this.price = price;
    }

    /**
     * Returns number of used material (count)
     */
    public int use(int count) {
        used += count;
        return count;
    }
}
