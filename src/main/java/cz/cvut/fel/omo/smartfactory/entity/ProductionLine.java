package cz.cvut.fel.omo.smartfactory.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ProductionLine {
    private Long id;
    private String productName;
    // Sequence of robots, peoples, machines
    private int priority;
    private List<ProductionLineUnit> manufacturingUnits;

    public void addUnit(ProductionLineUnit unit) {
        manufacturingUnits.add(unit);
    }

    public ProductionLineUnit getUnit(int id) {
        return null;
    }

    /**
     * Main production step
     */
    public Product createProduct() {
        Product manufacturingProduct = new Product();
        // Process chain of components
        for (ProductionLineUnit component : manufacturingUnits) {
            component.process(manufacturingProduct);
        }
        return manufacturingProduct;
    }

    public Series createSeries(int seriesNumber, int count /*TODO: or make configuration structure*/) {
        Series series = new Series(seriesNumber, count);
        for (int i = 0; i < count; i++) {
            series.addProduct(createProduct());
        }
        return series;
    }
}
