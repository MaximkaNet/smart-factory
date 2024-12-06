package cz.cvut.fel.omo.smartfactory.entity.factory.factoryIterator;

import cz.cvut.fel.omo.smartfactory.entity.ProductionUnit;
import cz.cvut.fel.omo.smartfactory.entity.factory.Factory;
import cz.cvut.fel.omo.smartfactory.entity.factoryequipment.AbstractFactoryEquipment;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

public class FactoryUsageIterator implements Iterator<AbstractFactoryEquipment> {
    private List<AbstractFactoryEquipment> allProductionUnits;
    @Getter
    private AbstractFactoryEquipment current;

    public FactoryUsageIterator(Factory factory) {
        allProductionUnits = factory.getProductionLines().stream()
                .flatMap(line -> {
                    List<ProductionUnit> productionUnitList = new ArrayList<>();
                    ProductionUnit chainStart = line.getProductionUnitChain();
                    while (chainStart != null) {
                        productionUnitList.add(chainStart);
                        chainStart = chainStart.getNext();
                    }
                    return productionUnitList.stream();
                })
                .filter(productionUnit -> productionUnit instanceof AbstractFactoryEquipment)
                .map(productionUnit -> (AbstractFactoryEquipment) productionUnit)
                .sorted(Comparator.comparingDouble((AbstractFactoryEquipment unit) -> unit.getMaximumHealth() - unit.getActualHealth()).reversed())
                .collect(Collectors.toList());

        this.current = allProductionUnits.get(0);
    }

    @Override
    public boolean hasNext() {
        return allProductionUnits.indexOf(current) < allProductionUnits.size() - 1;
    }

    @Override
    public AbstractFactoryEquipment next() {
        if (!hasNext()) {
            throw new NoSuchElementException("Calling has next on last element");
        }
        current = allProductionUnits.get(allProductionUnits.indexOf(current) + 1);
        return current;
    }
}