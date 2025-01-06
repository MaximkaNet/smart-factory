package cz.cvut.fel.omo.smartfactory.factory.iterator;

import cz.cvut.fel.omo.smartfactory.equipment.AbstractEquipment;

import java.util.List;

/**
 * Usage iterator. Sort items by usage percentage
 */
public class ReversedUsageIterator extends UsageIterator {

    /**
     * Create usage iterator
     *
     * @param equipmentList Equipment list from the factory
     */
    public ReversedUsageIterator(List<AbstractEquipment> equipmentList) {
        super(equipmentList, UsageIterator.getComparator().reversed());
    }
}
