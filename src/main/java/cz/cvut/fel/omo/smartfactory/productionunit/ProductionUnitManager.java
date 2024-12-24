package cz.cvut.fel.omo.smartfactory.productionunit;

import lombok.Getter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The production unit manager
 */
@Getter
public class ProductionUnitManager {

    /**
     * Busy production units
     */
    private final Map<String, List<AbstractProductionUnit>> busyUnits = new HashMap<>();

    /**
     * Available production units
     */
    private final Map<String, List<AbstractProductionUnit>> availableUnits = new HashMap<>();

    /**
     * Add busy unit
     *
     * @param unit The production unit
     */
    public void addBusyUnit(AbstractProductionUnit unit) {
        busyUnits.computeIfAbsent(unit.getId().getShortName(), (k) -> new ArrayList<>())
                .add(unit);
    }

    /**
     * Add available unit
     *
     * @param unit The production unit
     */
    public void addAvailableUnit(AbstractProductionUnit unit) {
        availableUnits.computeIfAbsent(unit.getId().getShortName(), (k) -> new ArrayList<>())
                .add(unit);
    }

    /**
     * Find available units and return sequence of units
     *
     * @param identifiers The production unit identifiers (such as: 'R' - robot, 'M' - machine, 'W' - worker)
     * @return Sequence of production units
     * @throws RuntimeException If available unit was not found
     */
    public List<AbstractProductionUnit> getUnits(List<String> identifiers) throws RuntimeException {
        // Output sequence
        List<AbstractProductionUnit> out = new ArrayList<>();

        for (String disc : identifiers) {
            AbstractProductionUnit unit = availableUnits
                    .getOrDefault(disc, new ArrayList<>())
                    .stream()
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("Available unit was not found"));

            // Remove unit from available units
            availableUnits.get(disc).remove(unit);
            out.add(unit);
        }

        return out;
    }

    /**
     * Remove units from busy units and add it to available units
     *
     * @param units The busy units
     */
    public void makeUnitsAvailable(List<AbstractProductionUnit> units) {
        for (AbstractProductionUnit unit : units) {
            busyUnits.computeIfAbsent(
                    unit.getId().getShortName(),
                    k -> new ArrayList<>()
            ).remove(unit);
        }

        for (AbstractProductionUnit unit : units) {
            availableUnits.computeIfAbsent(
                    unit.getId().getShortName(),
                    k -> new ArrayList<>()
            ).add(unit);
        }
    }
}
