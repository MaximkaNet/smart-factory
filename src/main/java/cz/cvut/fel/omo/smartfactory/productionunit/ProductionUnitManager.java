package cz.cvut.fel.omo.smartfactory.productionunit;

import cz.cvut.fel.omo.smartfactory.equipment.Machine;
import cz.cvut.fel.omo.smartfactory.equipment.Robot;
import cz.cvut.fel.omo.smartfactory.identifier.IdentifierFactory;
import cz.cvut.fel.omo.smartfactory.worker.Worker;
import lombok.Getter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * The production unit manager
 */
@Getter
public class ProductionUnitManager {

    private final IdentifierFactory identifierFactory = new IdentifierFactory("PRODUCTION_UNIT");

    /**
     * Production units
     */
    private final Map<String, List<AbstractProductionUnit>> availableUnits = new HashMap<>();

    /**
     * Create production unit manager
     */
    public ProductionUnitManager() {
    }

    /**
     * Add production unit
     *
     * @param unit The AbstractProductionUnit
     */
    public void addProductionUnit(AbstractProductionUnit unit) {
        availableUnits.computeIfAbsent(unit.getId().getShortName(), k -> new ArrayList<>()).add(unit);
    }

    /**
     * Create worker and add to production units
     *
     * @param name         The worker name
     * @param stepDuration The step duration
     */
    public void createWorker(String name, float stepDuration) {
        addProductionUnit(
                new Worker(identifierFactory.create(name, "W"), stepDuration)
        );
    }

    /**
     * Create robot
     *
     * @param name   The robot name
     * @param health The robot health
     */
    public void createRobot(String name, float health) {
        addProductionUnit(
                new Robot(identifierFactory.create(name, "R"), health)
        );
    }

    /**
     * Create machine
     *
     * @param name   The machine name
     * @param health The machine health
     */
    public void createMachine(String name, float health) {
        addProductionUnit(
                new Machine(identifierFactory.create(name, "M"), health)
        );
    }

    /**
     * Convert string sequence to AbstractProductionUnit sequence.
     * Remove converted sequence from available units.
     *
     * @param sequence The production unit sequence
     * @return Abstract production unit list, that were removed from the available units
     * @throws RuntimeException if unit was not found
     */
    public List<AbstractProductionUnit> convertSequence(List<String> sequence) throws RuntimeException {
        List<AbstractProductionUnit> out = new ArrayList<>();

        for (String shortName : sequence) {
            List<AbstractProductionUnit> units = availableUnits.getOrDefault(shortName, new ArrayList<>());
            Optional<AbstractProductionUnit> optionalUnit = units.stream().findFirst();

            if (optionalUnit.isEmpty()) {
                // Rollback
                this.restoreSequence(out);
                throw new RuntimeException("Unit " + shortName + " was not found");
            }

            units.remove(optionalUnit.get());
            out.add(optionalUnit.get());
        }

        return out;
    }

    /**
     * Restore sequence
     *
     * @param productionUnitList The production unit list
     */
    public void restoreSequence(List<AbstractProductionUnit> productionUnitList) {
        productionUnitList.forEach(this::addProductionUnit);
    }
}
