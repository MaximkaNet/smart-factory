package cz.cvut.fel.omo.smartfactory.factory.iterator;

import cz.cvut.fel.omo.smartfactory.equipment.AbstractEquipment;
import cz.cvut.fel.omo.smartfactory.productionunit.AbstractProductionUnit;

import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

/**
 * Usage iterator. Sort items by usage percentage
 */
public class UsageIterator implements FactoryIterator {
    /**
     * Equipment iterator
     */
    Iterator<AbstractEquipment> equipmentIterator;

    /**
     * Create usage iterator
     */
    public UsageIterator(List<AbstractEquipment> equipmentList) {
        this(equipmentList, UsageIterator.getComparator());
    }

    /**
     * Create usage iterator with special comparator
     */
    protected UsageIterator(List<AbstractEquipment> equipmentList, Comparator<AbstractEquipment> comparator) {
        Objects.requireNonNull(equipmentList);
        // Get cache
        List<AbstractEquipment> equipments = equipmentList.stream().sorted(comparator).toList();

        // Get iterator
        this.equipmentIterator = equipments.iterator();
    }

    /**
     * Get comparator for comparing equipment by usage percentage
     */
    protected static Comparator<AbstractEquipment> getComparator() {
        return Comparator.comparingDouble(
                (AbstractEquipment unit) -> (unit.getMaximumHealth() - unit.getActualHealth()) / unit.getMaximumHealth()
        );
    }

    @Override
    public boolean hasNext() {
        return equipmentIterator.hasNext();
    }

    @Override
    public AbstractProductionUnit next() {
        if (!equipmentIterator.hasNext()) {
            throw new NoSuchElementException("The iterator has no next element");
        }
        return equipmentIterator.next();
    }
}
