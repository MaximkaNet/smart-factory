package cz.cvut.fel.omo.smartfactory.entity.productionline;

import cz.cvut.fel.omo.smartfactory.entity.ProductionUnit;
import cz.cvut.fel.omo.smartfactory.entity.event.FactoryEvent;
import cz.cvut.fel.omo.smartfactory.entity.event.FactoryEventListener;
import cz.cvut.fel.omo.smartfactory.entity.event.SeriesFinishedEvent;
import cz.cvut.fel.omo.smartfactory.entity.factory.Factory;
import cz.cvut.fel.omo.smartfactory.entity.factory.TickObserver;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

public class ProductionLinePool implements FactoryEventListener, TickObserver {

    private final Factory factory;
    private final Map<Character, List<ProductionUnit>> productionUnitMap;
    private final List<ProductionLine> productionLineList = new ArrayList<>();
    private final Queue<ProductionLine> forRemoval = new LinkedList<>();

    public ProductionLinePool(Factory factory, Map<Character, List<ProductionUnit>> productionUnitMap) {
        this.factory = factory;
        this.productionUnitMap = productionUnitMap;
    }

    /**
     * Will try to configure a ProductionLine
     *
     * @return productionLine if configured or throws RuntimeException
     */
    public ProductionLine tryConfigure(String sequence) throws RuntimeException {
        // throws RuntimeException if sequence not compatible with factory
        if (!isCompatible(sequence)) {
            throw new RuntimeException("Cannot configure for sequence: " + sequence);
        }
        List<ProductionUnit> productionUnitList = getCompatibleSequence(sequence);
        if (productionUnitList.size() != sequence.length()) {
            return null;
        }
        ProductionLine productionLine = new ProductionLine(sequence.length(), productionUnitList);
        productionLineList.add(productionLine);
        return productionLine;
    }

    /**
     * Returns true if is compatible false otherwise
     */
    public boolean isCompatible(String sequence) {
        Map<Character, Integer> charCountMap = countCharacters(sequence);

        for (Map.Entry<Character, Integer> entry : charCountMap.entrySet()) {
            if (productionUnitMap.getOrDefault(entry.getKey(), new ArrayList<>()).size() < entry.getValue()) {
                return false;
            }
        }

        return true;
    }

    /**
     * Get character "statistic"
     */
    private static Map<Character, Integer> countCharacters(String input) {
        Map<Character, Integer> charCountMap = new HashMap<>();

        for (char c : input.toCharArray()) {
            charCountMap.put(c, charCountMap.getOrDefault(c, 0) + 1);
        }

        return charCountMap;
    }

    /**
     * Returns compatible sequence of production units
     */
    private List<ProductionUnit> getCompatibleSequence(String sequence) {
        List<ProductionUnit> productionUnitList = new ArrayList<>();
        for (Character c : sequence.toCharArray()) {
            List<ProductionUnit> productionUnits = productionUnitMap.getOrDefault(c, new ArrayList<>());
            boolean found = false;
            for (ProductionUnit productionUnit : productionUnits) {
                if (productionUnit.isAvailable()) {
                    productionUnitList.add(productionUnit);
                    found = true;
                    break;
                }
            }
            if (!found) {
                break;
            }
        }
        return productionUnitList;
    }

    @Override
    public void receiveEvent(FactoryEvent event) {
        if (!(event instanceof SeriesFinishedEvent)) {
            return;
        }
        SeriesFinishedEvent seriesFinishedevent = (SeriesFinishedEvent) event;
        ProductionLine productionLine = seriesFinishedevent.getProductionLine();

        productionLineList.remove(productionLine);
    }

    @Override
    public void update(long deltaTime) {
        productionLineList.forEach(productionLine -> {
            productionLine.getState().process(deltaTime);
            if (productionLine.isFinished()) {
                factory.addCompletedSeries(productionLine.pop());
                productionLineList.remove(productionLine);
            }
        });
    }
}
