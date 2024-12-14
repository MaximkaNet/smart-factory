package cz.cvut.fel.omo.smartfactory.entity.report;

import cz.cvut.fel.omo.smartfactory.entity.factory.Factory;
import cz.cvut.fel.omo.smartfactory.entity.factoryequipment.AbstractFactoryEquipment;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConsumptionReport extends Report {

    private List<AbstractFactoryEquipment> abstractFactoryEquipments = new ArrayList<>();

    /**
     * Report fields
     */
    private Map<AbstractFactoryEquipment, String> consumptionPerEquipment = new HashMap<>();
    private String consumptionForFactory;

    // TODO: the ZonedDateTime from and to are not considered in the calculation, should calculate consumption only in predefined interval
    public ConsumptionReport(ZonedDateTime from, ZonedDateTime to, Factory factory) {
        super(factory, from, to);

        // loading all entities into a list of AbstractFactoryEquipment
        abstractFactoryEquipments.addAll(factory.getMachines());
        abstractFactoryEquipments.addAll(factory.getRobots());

        calcFactoryConsumptionStats(factory);

        calcConsumptionPerEquipment();
    }

    private void calcConsumptionPerEquipment() {
        abstractFactoryEquipments.forEach(equipment -> consumptionPerEquipment.put(equipment,
                equipment.getMaterialConsumer().getConsumed()
                        + " " +
                        equipment.getElectricityConsumer().getConsumed()
                        + " " +
                        equipment.getOilConsumer().getConsumed()
        ));
    }

    private void calcFactoryConsumptionStats(Factory factory) {
        Float electricityConsumption = abstractFactoryEquipments.stream()
                .map(equipment -> equipment.getElectricityConsumer().getConsumed())
                .reduce(0f, Float::sum);

        Integer materialConsumption = abstractFactoryEquipments.stream()
                .map(equipment -> equipment.getMaterialConsumer().getConsumed())
                .reduce(0, Integer::sum);

        Float oilConsumption = abstractFactoryEquipments.stream()
                .map(equipment -> equipment.getOilConsumer().getConsumed())
                .reduce(0f, Float::sum);

        consumptionForFactory = "Factory " + factory + " consumed{ "
                + "electricityConsumption: " + electricityConsumption
                + " materialConsumption: " + materialConsumption
                + " oilConsumption: " + oilConsumption
                + "}";
    }

    @Override
    public String exportJson() {
        return "";
    }
}
